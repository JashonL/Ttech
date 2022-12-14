package com.tianji.ttech.service.ble

import com.ttech.bluetooth.util.`interface`.IBleConnect

/**
 * ble 蓝牙单例类
 */
class BleUtils : IBleConnect {



    //静态内部类实现单例模式
    companion object {
        fun instance(): BleUtils = Holder.instance
    }





    private object Holder {
        val instance = BleUtils()
    }


    /**
     * 服务
     */
    override fun openBle() {
    }

    override fun scan() {
    }

    override fun connect(mac: String) {
    }

    override fun setMtu(mtu: Int) {
    }

    override fun sendData(value: ByteArray) {
    }

    override fun receiveData(data: ByteArray?) {
    }

    override fun disConnect() {
    }


}