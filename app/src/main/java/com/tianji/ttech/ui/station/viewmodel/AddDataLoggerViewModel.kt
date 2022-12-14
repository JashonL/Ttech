package com.tianji.ttech.ui.station.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.service.http.ApiPath
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import kotlinx.coroutines.launch

/**
 * 添加采集器
 */
class AddDataLoggerViewModel : BaseViewModel() {

    var plantId: String? = null

    val addDataLoggerLiveData = MutableLiveData<String?>()

    val getCheckCodeLiveData = MutableLiveData<Pair<String?, String?>>()

    /**
     * 添加采集器
     */
    fun addDataLogger(dataLoggerSN: String, checkCode: String) {
        viewModelScope.launch {
            val params = hashMapOf<String, String>().apply {
                put("datalogSn", dataLoggerSN)
                put("verificationCode", checkCode)
                put("stationId", plantId ?: "")
            }
            apiService().postForm(ApiPath.Dataloger.ADDDATALOG, params, object :
                HttpCallback<HttpResult<String>>() {
                override fun success(result: HttpResult<String>) {
                    if (result.isBusinessSuccess()) {
                        addDataLoggerLiveData.value = null
                    } else {
                        addDataLoggerLiveData.value = result.msg ?: ""
                    }
                }

                override fun onFailure(errorModel: HttpErrorModel) {
                    addDataLoggerLiveData.value = errorModel.errorMsg ?: ""
                }

            })
        }
    }

  /*  *//**
     * 获取校验码
     *//*
    fun getCheckCode(dataLoggerSN: String) {
        viewModelScope.launch {
            val params = hashMapOf<String, String>().apply {
                put("datalogSN", dataLoggerSN)
            }
            apiService().postForm(ApiPath.Plant.GET_CHECK_CODE, params, object :
                HttpCallback<HttpResult<String>>() {
                override fun success(result: HttpResult<String>) {
                    if (result.isBusinessSuccess()) {
                        getCheckCodeLiveData.value = Pair(result.data, null)
                    } else {
                        getCheckCodeLiveData.value = Pair(null, result.msg ?: "")
                    }
                }

                override fun onFailure(errorModel: HttpErrorModel) {
                    getCheckCodeLiveData.value = Pair(null, errorModel.errorMsg ?: "")
                }

            })
        }
    }*/


}