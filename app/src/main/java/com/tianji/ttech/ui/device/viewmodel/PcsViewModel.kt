package com.olp.weco.ui.device.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.model.DeviceType
import com.tianji.ttech.service.http.ApiPath
import com.tianji.ttech.ui.device.model.PCS
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import kotlinx.coroutines.launch

class PcsViewModel : BaseViewModel() {


    val pcsLiveData = MutableLiveData<Pair<Boolean, PCS?>>()


    var deviceType: Int = DeviceType.INVERTER

    var deviceSn: String = ""


    /**
     * 获取电站数据
     */
    fun getdeviceDetails() {
        viewModelScope.launch {
            val params = hashMapOf<String, String>().apply {
                put("deviceType", deviceType.toString())
                put("deviceSn", deviceSn)

            }
            apiService().postForm(
                ApiPath.Device.GETDEVICEDETAILS,
                params,
                object : HttpCallback<HttpResult<PCS>>() {
                    override fun success(result: HttpResult<PCS>) {
                        pcsLiveData.value = Pair(result.isBusinessSuccess(), result.obj)
                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                        pcsLiveData.value = Pair(false, null)

                    }
                })
        }
    }


}