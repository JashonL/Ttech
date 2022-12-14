package com.tianji.ttech.service.ble

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.ttech.bluetooth.util.`interface`.IBleConnect
import com.ttech.bluetooth.util.service.BleConnectService

class BleManager(context: Context, bindServiceListeners: bindServiceListeners) : IBleConnect {

    //保持所启动的Service的IBinder对象,同时定义一个ServiceConnection对象
    var binder: BleConnectService.LocalBinder? = null
    private val conn: ServiceConnection = object : ServiceConnection {
        //Activity与Service断开连接时回调该方法
        override fun onServiceDisconnected(name: ComponentName) {
        }

        //Activity与Service连接成功时回调该方法
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            binder = service as BleConnectService.LocalBinder
            bindServiceListeners.onServiceConnected()

        }
    }

    init {
        val intent = Intent(context, BleConnectService::class.java)
        context.bindService(intent, conn, AppCompatActivity.BIND_AUTO_CREATE)
        context.startService(intent)
    }


    override fun isBleEnable(): Boolean? {
        return binder?.service?.isBleEnable()
    }


    override fun openBle() {
        binder?.service?.openBle()
    }

    override fun scan() {
        binder?.service?.scan()

    }

    override fun stopScan() {
        binder?.service?.stopScan()
    }

    override fun connect(mac: String) {
        binder?.service?.connect(mac)
    }

    override fun setMtu(mtu: Int) {
        binder?.service?.setMtu(mtu)
    }

    override fun sendData(value: ByteArray) {
        binder?.service?.sendData(value)
    }

    override fun receiveData(data: ByteArray?) {
        binder?.service?.receiveData(data)
    }

    override fun disConnect() {
        binder?.service?.disConnect()
    }


    interface bindServiceListeners {
        fun onServiceConnected()
        fun onServiceDisconnected()
    }

}