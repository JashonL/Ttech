package com.tianji.ttech.ui.dataloger.viewmodel

import android.content.Context
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.model.ble.DatalogResponBean
import com.tianji.ttech.service.ble.BleCommand.CHECKCONNET_STATUS
import com.tianji.ttech.service.ble.BleCommand.LINK_STATUS
import com.tianji.ttech.service.ble.BleManager
import com.tianji.ttech.utils.ByteDataUtil
import com.tianji.ttech.utils.ByteDataUtil.BlueToothData.DATALOG_GETDATA_0X19
import com.tianji.ttech.utils.ByteDataUtil.BlueToothData.numberServerPro19
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConnetViewModel : BaseViewModel() {

    private val bleLiveData = MutableLiveData<BleManager?>()

    var bleManager: BleManager? = null

    val responLiveData = MutableLiveData<DatalogResponBean?>()

    var devId: String = "0000000000"

    fun getBleManager(context: Context, bindServiceListeners: BleManager.bindServiceListeners) {
        bleLiveData.value = BleManager(context, bindServiceListeners)
        bleManager = bleLiveData.value
    }


    fun checkStatus() {
        //开启一个协程   隔两秒去获取状态
        viewModelScope.launch {
            delay(2000)
            var bytes = ByteArray(0)
            try {
                bytes = ByteDataUtil.BlueToothData.numberServerPro19(
                    DATALOG_GETDATA_0X19,
                    devId,
                    intArrayOf(CHECKCONNET_STATUS)
                )!!
            } catch (e: Exception) {
                e.printStackTrace()
            }
            bleManager?.sendData(bytes)

        }

    }


    /*发送命令查询状态*/
     fun checkServerStatus() {
        //开启一个协程   隔两秒去获取状态
        viewModelScope.launch {
            delay(2000)
            var bytes = ByteArray(0)
            try {
                bytes = numberServerPro19(
                    DATALOG_GETDATA_0X19,
                    devId,
                    intArrayOf(LINK_STATUS)
                )!!
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            bleManager?.sendData(bytes)

        }


    }


    /**
     * 解析返回数据
     */
    fun parserData(byte: Byte, dataByte: ByteArray?) {
        dataByte?.let {
            responLiveData.value = ByteDataUtil.BlueToothData.paserData(byte, it)

        }
    }

}