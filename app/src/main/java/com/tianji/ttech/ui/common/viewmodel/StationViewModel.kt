package com.tianji.ttech.ui.common.viewmodel

import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.service.http.ApiPath
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import kotlinx.coroutines.launch

class StationViewModel:BaseViewModel() {


    /**
     * 获取国家/地区列表
     */
    fun stationlist() {
        viewModelScope.launch {
            apiService().post(
                ApiPath.Plant.STATIONLIST,
                object : HttpCallback<HttpResult<Array<String>>>() {
                    override fun success(result: HttpResult<Array<String>>) {
                        if (result.isBusinessSuccess()) {

                        } else {

                        }
                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                    }

                })
        }
    }

}