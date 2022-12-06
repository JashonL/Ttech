package com.growatt.atess.ui.common.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import kotlinx.coroutines.launch

/**
 * 静态资源
 */
class StaticResourceViewModel : BaseViewModel() {

    val staticResourceLiveData = MutableLiveData<Pair<String?, String?>>()

    /**
     * 静态资源获取
     */
    fun fetchStaticResource(staticResourceUrl: String) {
        viewModelScope.launch {
            apiService().httpGet(staticResourceUrl, object :
                HttpCallback<HttpResult<String>>() {
                override fun success(result: HttpResult<String>) {
                    if (result.isBusinessSuccess()) {
                        staticResourceLiveData.value = Pair(result.obj, null)
                    } else {
                        staticResourceLiveData.value = Pair(null, result.msg ?: "")
                    }
                }

                override fun onFailure(errorModel: HttpErrorModel) {
                    staticResourceLiveData.value = Pair(null, errorModel.errorMsg ?: "")
                }

            })
        }
    }

}