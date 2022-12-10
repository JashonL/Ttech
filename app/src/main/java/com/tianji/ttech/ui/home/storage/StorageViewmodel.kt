package com.tianji.ttech.ui.home.storage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.service.http.ApiPath
import com.tianji.ttech.ui.home.model.StorageModel
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import com.ttech.lib.util.MD5Util
import kotlinx.coroutines.launch

/**
 * 请求电站状态
 */
class StorageViewmodel : BaseViewModel() {


    val statusLiveData = MutableLiveData<StorageModel?>()
    var stationId: String? = null

    /**
     * 获取系统状态
     */
    fun getDataOverview() {
        val params = hashMapOf<String, String>().apply {
            put("stationId", stationId?:"")
        }

        viewModelScope.launch {
            apiService().postForm(
                ApiPath.Plant.GETDATAOVERVIEW, params,
                object : HttpCallback<HttpResult<StorageModel>>() {
                    override fun success(result: HttpResult<StorageModel>) {
                        if (result.isBusinessSuccess()) {
                            statusLiveData.value = result.obj
                        } else {
                            statusLiveData.value = null
                        }
                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                        statusLiveData.value = null
                    }

                })
        }
    }


}