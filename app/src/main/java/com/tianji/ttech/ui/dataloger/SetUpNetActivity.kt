package com.tianji.ttech.ui.dataloger

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivitySetUpNetBinding
import com.ttech.bluetooth.util.service.BleConnectService


class SetUpNetActivity : BaseActivity() {


    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, SetUpNetActivity::class.java))
        }
    }


    private lateinit var binding: ActivitySetUpNetBinding

    var mBleService: BleConnectService? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUpNetBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val intent = Intent(this, BleConnectService::class.java)
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
        startService(intent)




    }

    /**
     * 服务
     */
    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, rawBinder: IBinder) {
            mBleService = (rawBinder as BleConnectService.LocalBinder).service
            mBleService?.openBle()

        }

        override fun onServiceDisconnected(classname: ComponentName) {
        }
    }


}