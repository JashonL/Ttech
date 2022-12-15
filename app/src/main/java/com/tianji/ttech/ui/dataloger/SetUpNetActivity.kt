package com.tianji.ttech.ui.dataloger

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.growatt.atess.ui.common.fragment.RequestPermissionHub
import com.tianji.ttech.R
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivitySetUpNetBinding
import com.tianji.ttech.monitor.WifiMonitor
import com.tianji.ttech.ui.dataloger.viewmodel.SetUpNetViewModel
import com.tianji.ttech.view.dialog.AlertDialog
import com.ttech.bluetooth.util.util.LocalUtils
import java.util.*


class SetUpNetActivity : BaseActivity(), OnClickListener {


    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, SetUpNetActivity::class.java))
        }
    }


    private lateinit var binding: ActivitySetUpNetBinding


    private val viewModel: SetUpNetViewModel by viewModels()

    private var connectivityManager: ConnectivityManager? = null

    private var wifiManager: WifiManager? = null

    private var scanResults: List<ScanResult>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUpNetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {//android 12  31
            connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            requestNetwork()
        }
        initGps()
        initListeners()


        WifiMonitor.watch (lifecycle){
            it?.let {
                if (it.action==WifiManager.SCAN_RESULTS_AVAILABLE_ACTION){
                    scanResults = wifiManager!!.scanResults
                }
            }


        }

/*
        viewModel.getBleManager(this, object : BleManager.bindServiceListeners {
            override fun onServiceConnected() {



            }

            override fun onServiceDisconnected() {
            }

        })*/


    }







    /**
     * 打开GPS获取当前连接的wifi名称  和获取wifi列表
     */
    private fun initGps() {
        val b: Boolean = LocalUtils.checkGPSIsOpen(this)
        if (b) {
            try {//获取当前连接wifi的名称 还有wifi列表
                initPermission()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {//去打开GPS
            //弹框提示
            showGpsDialog()

        }

    }


    /**
     * 开启GPS弹框
     */
    private fun showGpsDialog() {
        AlertDialog.showDialog(
            supportFragmentManager,
            getString(R.string.m104_gps_to_getwifi),
            getString(R.string.m101_cancel),
            getString(R.string.m102_comfir),
            getString(R.string.m103_turn_on_gps)
        ) {
            val intent = Intent()
            intent.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
            startActivityForResult(intent, LocalUtils.OPEN_GPS_CODE)
        }
    }


    //检查权限 扫描wifi列表
    @SuppressLint("MissingPermission")
    private fun initPermission() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
        RequestPermissionHub.requestPermission(
            supportFragmentManager,
            permissions
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//android 8 26
                val mWifiManager =
                    (applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager)
                val info = mWifiManager.connectionInfo
                val ssid = info.ssid.replace("\"", "")
                binding.etSsid.setText(ssid)
            } else {
                val mWifiManager =
                    applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val connectionInfo = mWifiManager.connectionInfo
                val networkId = connectionInfo.networkId

                var ssid = connectionInfo.ssid
                val configuredNetworks = mWifiManager.configuredNetworks
                for (wificonf in configuredNetworks) {
                    if (wificonf.networkId == networkId) {
                        ssid = wificonf.SSID
                        break
                    }
                }
                if (TextUtils.isEmpty(ssid)) ssid = ""
                if (ssid.contains("\"")) {
                    ssid = ssid.replace("\"", "")
                }
                if (ssid.lowercase(Locale.getDefault()).contains("unknown ssid")) {
                    ssid = ""
                }

                binding.etSsid.setText(ssid)

            }


            scanResults = wifiManager?.scanResults


        }


    }

    private fun initListeners() {
        binding.ivScan.setOnClickListener(this)

    }


    override fun onClick(p0: View?) {
        when {
            p0 === binding.ivScan -> {


            }
        }

    }


    private val request: NetworkRequest =
        NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build()


    private val mNetworkCallback =
        @RequiresApi(Build.VERSION_CODES.S)
        object : ConnectivityManager.NetworkCallback(FLAG_INCLUDE_LOCATION_INFO) {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val wifiInfo: WifiInfo = networkCapabilities.transportInfo as WifiInfo
                    val ssid =
                        wifiInfo.getSSID().replace("\"", "").replace("<", "").replace(">", "")
                    binding.etSsid.setText(ssid)

                }


            }

        }


    private fun requestNetwork() {
        connectivityManager?.registerNetworkCallback(request, mNetworkCallback);
    }

    private fun unrequestNetwork() {
        connectivityManager?.unregisterNetworkCallback(mNetworkCallback);
    }


    override fun onDestroy() {
        super.onDestroy()
        unrequestNetwork()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LocalUtils.OPEN_GPS_CODE) {
            initGps()
        }
    }


}