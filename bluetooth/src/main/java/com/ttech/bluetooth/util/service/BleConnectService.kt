package com.ttech.bluetooth.util.service

import android.app.Service
import android.bluetooth.BluetoothGatt
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.clj.fastble.BleManager
import com.clj.fastble.callback.*
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.clj.fastble.scan.BleScanRuleConfig
import com.ttech.bluetooth.util.CRC16
import com.ttech.bluetooth.util.`interface`.IBleConnect
import com.ttech.bluetooth.util.util.AESCBCUtils
import com.ttech.bluetooth.util.util.ByteDataUtils.byte2Int
import com.ttech.bluetooth.util.util.ByteDataUtils.bytesToHexString
import com.ttech.bluetooth.util.util.ByteDataUtils.int2Byte
import java.util.*


class BleConnectService : Service(), IBleConnect {

    companion object {
        private val TAG = BleConnectService::class.java.simpleName
        val SERVICE_UUID: UUID = UUID.fromString("000000FF-0000-1000-8000-00805f9b34fb")

        //服务UUID
        private const val SERVICE_ID = "000000FF-0000-1000-8000-00805f9b34fb"

        //写入UUID
        private const val WRITE_ID = "0000ff01-0000-1000-8000-00805f9b34fb"

        lateinit var instance: BleConnectService

    }


    private var mBleDevice: BleDevice? = null
    private var receviceData: ByteArray? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        //初始化蓝牙sdk
        BleManager.getInstance().init(application);
        BleManager.getInstance().enableLog(true)
            .setReConnectCount(1, 5000)
            .setSplitWriteNum(600)
            .setConnectOverTime(10000).operateTimeout = 5000

        //初始化搜索
        val scanRuleConfig = BleScanRuleConfig.Builder()
            .setAutoConnect(false)      // 连接时的autoConnect参数，可选，默认false
            .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒
            .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);

    }


    override fun onBind(p0: Intent?): IBinder {
        return mBinder
    }




    inner class LocalBinder : Binder() {
        val service: BleConnectService
            get() = this@BleConnectService
    }


    private val mBinder: IBinder = LocalBinder()

    override fun openBle() {
        val blueEnable = BleManager.getInstance().isBlueEnable
        //如果已经打开了，初始化对应控件并开始搜索
        if (!blueEnable){
            BleManager.getInstance().enableBluetooth()
        }

    }


    override fun scan() {
        BleManager.getInstance().scan(object : BleScanCallback() {
            override fun onScanStarted(success: Boolean) {
            }

            override fun onScanning(bleDevice: BleDevice?) {

            }

            override fun onScanFinished(scanResultList: MutableList<BleDevice>?) {
            }
        })

    }

    override fun connect(mac: String) {

        BleManager.getInstance().connect(mac, object : BleGattCallback() {
            override fun onStartConnect() {
            }

            override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
            }

            override fun onConnectSuccess(
                bleDevice: BleDevice?,
                gatt: BluetoothGatt?,
                status: Int
            ) {
                //连接成功
                Log.d(TAG, "on start connect success")
                //校验设备是否拥有我们所需的服务与特征
                val service = gatt?.getService(SERVICE_UUID)
                //是否需要更新
                if (null == service) {
                    Log.e(TAG, "service==null ")
                    return
                }
                mBleDevice = bleDevice

                //连接成功之后 设置MTU
                setMtu(100)

            }

            override fun onDisConnected(
                isActiveDisConnected: Boolean,
                device: BleDevice?,
                gatt: BluetoothGatt?,
                status: Int
            ) {
            }

        })
    }

    override fun setMtu(mtu: Int) {
        BleManager.getInstance().setMtu(mBleDevice, mtu, object : BleMtuChangedCallback() {
            override fun onSetMTUFailure(exception: BleException?) {

            }

            override fun onMtuChanged(mtu: Int) {
                //设置完mtu 监听蓝牙返回数据
                BleManager.getInstance()
                    .notify(mBleDevice, SERVICE_ID, WRITE_ID, object : BleNotifyCallback() {
                        override fun onNotifySuccess() {

                        }

                        override fun onNotifyFailure(exception: BleException?) {

                        }

                        override fun onCharacteristicChanged(data: ByteArray?) {
                            //蓝牙回应数据
                            receiveData(data)

                        }

                    })

            }

        })

    }

    override fun sendData(value: ByteArray) {
        try {
            receviceData = ByteArray(0)
            BleManager.getInstance()
                .write(mBleDevice, SERVICE_ID, WRITE_ID, value, false, object : BleWriteCallback() {
                    override fun onWriteSuccess(current: Int, total: Int, justWrite: ByteArray?) {
                    }

                    override fun onWriteFailure(exception: BleException?) {
                    }
                })
        } catch (e: Exception) {

        }

    }


    override fun receiveData(data: ByteArray?) {
        if (data?.size!! < 8) return
        Log.d(TAG, "蓝牙回应数据......" + bytesToHexString(data))
        //数据校验
        val receiveLen = byte2Int(byteArrayOf(data[0], data[1]))
        if (receiveLen != data.size - 2) {
            dataCompase(data)
        } else {
            receviceData = data
            if (checkAndshow()) {
                //AES解密
                aesPase()

            } else {
                receviceData = ByteArray(0)
            }

        }
    }

    override fun disConnect() {
        BleManager.getInstance().disconnectAllDevice();
    }


    private fun dataCompase(data: ByteArray) {
        if (receviceData == null) return
        val compose = ByteArray(receviceData!!.size + data.size) { 0 }
        receviceData?.let {
            System.arraycopy(it, 0, compose, 0, receviceData!!.size)
            System.arraycopy(data, 0, compose, receviceData!!.size, data.size)
        }
        receviceData = compose
        if (checkAndshow()) {
            aesPase();
        }


    }


    private fun checkAndshow(): Boolean {
        //数据校验
        val receiveLen = byte2Int(byteArrayOf(receviceData!![0], receviceData!![1]))
        val b_len = receiveLen == receviceData!!.size - 2

        //crc校验
        //获取crc效验
        val crcL: Byte = receviceData!![receviceData!!.size - 1]
        val crcH: Byte = receviceData!![receviceData!!.size - 2]
        //获取CRC之外的数据
        val originalByte: ByteArray = Arrays.copyOfRange(receviceData!!, 0, receviceData!!.size - 2)
        val crc: Int = CRC16.calcCrc16(originalByte)
        val crcBytes: ByteArray = int2Byte(crc)
        val b_crc = crcBytes[0] == crcH && crcBytes[1] == crcL
        return b_crc && b_len;
    }


    private fun aesPase() {
        //减10是因为：有效数据=总数据-总长度(2)-协议标识（2）-实际数据长度（2）-设备地址（1）-功能码（1）-功能码-crc（2）
        var datalen = receviceData?.size?.minus(10)
        val databytes = ByteArray(datalen ?: 0) { 0 }

        System.arraycopy(receviceData, 8, databytes, 0, datalen!!)
        val bytes: ByteArray = AESCBCUtils.AESDecryption(databytes)
        //还要减去补0的数据
        //获取真实的数据长度
        val realByte: ByteArray = byteArrayOf(receviceData!![4], receviceData!![5])
        val crc: ByteArray = byteArrayOf(
            receviceData!![receviceData!!.size - 2],
            receviceData!![receviceData!!.size - 1]
        )
        val realLen: Int = byte2Int(realByte) - 2//内容长度包含了设备地址和功能码所以要减去
        val dataBytes = ByteArray(realLen) { 0 }
        if (realLen >= 0) System.arraycopy(bytes, 0, dataBytes, 0, realLen)
        datalen = dataBytes.size + 10
        val allData = ByteArray(datalen) { 0 }
        System.arraycopy(receviceData, 0, allData, 0, 8)
        System.arraycopy(dataBytes, 0, allData, 8, dataBytes.size)
        System.arraycopy(crc, 0, allData, 8 + dataBytes.size, crc.size)
        receviceData = allData

    }


}