package com.ttech.bluetooth.util.`interface`

interface IBleConnect {




    /**
     * 打开蓝牙
     */

    fun openBle()

    /**
     *扫描蓝牙
     */
    fun scan()


    /**
     * 连接蓝牙
     */
    fun connect(mac:String)


    /**
     * 设置MTU
     */

    fun setMtu(mtu:Int)


    /**
     * 发送数据
     */
    fun sendData(value:ByteArray)


    /**
     * 接收数据
     */

    fun receiveData(data: ByteArray?)

    /**
     * .断开蓝牙
     */
    fun disConnect()





}