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
import com.tianji.ttech.ui.common.fragment.RequestPermissionHub
import com.tianji.ttech.app.TtechApplication
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivitySetUpNetBinding
import com.tianji.ttech.monitor.WifiMonitor
import com.tianji.ttech.service.ble.BleCommand.BLUETOOTH_KEY
import com.tianji.ttech.service.ble.BleCommand.WIFI_PASSWORD
import com.tianji.ttech.service.ble.BleManager
import com.tianji.ttech.ui.MainActivity
import com.tianji.ttech.ui.dataloger.viewmodel.SetUpNetViewModel
import com.tianji.ttech.utils.ByteDataUtil
import com.tianji.ttech.utils.ByteDataUtil.BlueToothData.DATALOG_GETDATA_0X18
import com.tianji.ttech.view.dialog.AlertDialog
import com.tianji.ttech.view.pop.ListPopuwindow
import com.tianji.ttech.view.popuwindow.ListPopModel
import com.ttech.bluetooth.util.receiver.BlueToothReceiver
import com.ttech.bluetooth.util.util.LocalUtils
import com.ttech.lib.util.LogUtil
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

        //android 12 ????????????  ??????wifi??????
        wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {//android 12  31
            connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            requestNetwork()
        }

        //??????????????????GPS
        initGps()

        initListeners()

        //??????????????????wifi??????
        WifiMonitor.watch(lifecycle) {
            it?.let {
                if (it.action == WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) {
                    scanResults = wifiManager!!.scanResults
                }
            }
        }

        initData()

    }

    private fun initData() {
        viewModel.responLiveData.observe(this) {
            //1.???????????????bean
            if (it?.funcode == DATALOG_GETDATA_0X18) {
                val statusCode = it.statusCode
                val paramNum = it.paramNum
                if (paramNum == BLUETOOTH_KEY) { //????????????
                    if (statusCode == 0) {
                        LogUtil.i(TtechApplication.APP_NAME, "??????????????????")
                    } else { //????????????

                    }
                } else if (paramNum == WIFI_PASSWORD) {//??????WiFi??????????????????
                    if (statusCode == 0) {
                        LogUtil.i(TtechApplication.APP_NAME, "??????wifi?????????????????????......................")
                        DatalogerConActivity.start(this)
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        //??????wifi
        wifiManager?.startScan()


        //????????????????????????
        BlueToothReceiver.watch(lifecycle, TtechApplication.instance()) {
            val removePro = ByteDataUtil.BlueToothData.removePro(it)
            dismissDialog()
            viewModel.parserData(it!![7], removePro)
        }



        viewModel.getBleManager(this, object : BleManager.bindServiceListeners {
            override fun onServiceConnected() {
                //????????????
                viewModel.sendCmdConnect()
            }

            override fun onServiceDisconnected() {}

        })

    }


    /**
     * ??????GPS?????????????????????wifi??????  ?????????wifi??????
     */
    private fun initGps() {
        val b: Boolean = LocalUtils.checkGPSIsOpen(this)
        if (b) {
            try {//??????????????????wifi????????? ??????wifi??????
                initPermission()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {//?????????GPS
            //????????????
            showGpsDialog()

        }

    }


    /**
     * ??????GPS??????
     */
    private fun showGpsDialog() {
        AlertDialog.showDialog(
            supportFragmentManager,
            getString(com.tianji.ttech.R.string.m104_gps_to_getwifi),
            getString(com.tianji.ttech.R.string.m101_cancel),
            getString(com.tianji.ttech.R.string.m102_comfir),
            getString(com.tianji.ttech.R.string.m103_turn_on_gps)
        ) {
            val intent = Intent()
            intent.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
            startActivityForResult(intent, LocalUtils.OPEN_GPS_CODE)
        }
    }


    //???????????? ??????wifi??????
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
        binding.ivWifiList.setOnClickListener(this)
        binding.btFinish.setOnClickListener(this)
        binding.btCancel.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when {
            p0 === binding.ivWifiList -> {
                showWifiList()
            }

            p0 === binding.btFinish -> {

                val ssid = binding.etSsid.text.toString()
                val pwd = binding.etPassword.text.toString()
                showDialog()
                viewModel.requestSetting(ssid, pwd)

            }

            p0===binding.btCancel->{
                MainActivity.start(this)
                finish()
            }

        }

    }


    private fun showWifiList() {
        val options = mutableListOf<ListPopModel>()
        scanResults?.let {
            it.forEach {
                val ssid = it.SSID
                options.add(ListPopModel(ssid, false))
            }
        }
        val current = binding.etSsid.text.toString()
        ListPopuwindow.showPop(
            this,
            options,
            binding.llSsid,
            current
        ) {
            binding.etSsid.setText(options[it].title)
        }
    }


    private val request: NetworkRequest =
        NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build()


    private val mNetworkCallback =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            object : ConnectivityManager.NetworkCallback(FLAG_INCLUDE_LOCATION_INFO) {
                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        val wifiInfo: WifiInfo = networkCapabilities.transportInfo as WifiInfo
                        val ssid =
                            wifiInfo.ssid.replace("\"", "").replace("<", "").replace(">", "")
                        binding.etSsid.setText(ssid)

                    }
                }
            }
        } else {
            null
        }


    private fun requestNetwork() {
        mNetworkCallback?.let { connectivityManager?.registerNetworkCallback(request, it) };
    }

    private fun unrequestNetwork() {
        mNetworkCallback?.let { connectivityManager?.unregisterNetworkCallback(it) };
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