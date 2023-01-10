package com.olp.weco.ui.device.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.model.DeviceModel
import com.tianji.ttech.service.http.ApiPath
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import kotlinx.coroutines.launch

class DeviceListViewModel : BaseViewModel() {

    val deviceListLiveData = MutableLiveData<Pair<Boolean, Array<DeviceModel>?>>()


    var currentPlantId:String? = ""

    /**
     * 获取电站数据
     */
    fun getDevicelist() {
        viewModelScope.launch {
            val params = hashMapOf<String, String>().apply {
                put("stationId", currentPlantId.toString())
            }
            apiService().postForm(
                ApiPath.Device.GETDEVICELIST,
                params,
                object : HttpCallback<HttpResult<Array<DeviceModel>>>() {
                    override fun success(result: HttpResult<Array<DeviceModel>>) {
                        deviceListLiveData.value = Pair(result.isBusinessSuccess(), result.obj)
                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                        deviceListLiveData.value = Pair(false, null)

                    }
                })
        }
    }


}