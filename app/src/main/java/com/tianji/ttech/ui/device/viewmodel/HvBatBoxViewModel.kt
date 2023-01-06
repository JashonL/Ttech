package com.tianji.ttech.ui.device.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.ui.device.model.HvBox
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.model.DeviceType
import com.tianji.ttech.service.http.ApiPath
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import kotlinx.coroutines.launch

class HvBatBoxViewModel : BaseViewModel() {


    val hvBatBoxLiveData = MutableLiveData<Pair<Boolean, HvBox?>>()


    var deviceType: Int = DeviceType.HVBOX

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
                object : HttpCallback<HttpResult<HvBox>>() {
                    override fun success(result: HttpResult<HvBox>) {
                        hvBatBoxLiveData.value = Pair(result.isBusinessSuccess(), result.obj)
                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                        hvBatBoxLiveData.value = Pair(false, null)

                    }
                })
        }
    }


}