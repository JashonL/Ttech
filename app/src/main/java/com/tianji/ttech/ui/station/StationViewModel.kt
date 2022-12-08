package com.tianji.ttech.ui.station

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.model.StationModel
import com.tianji.ttech.service.http.ApiPath
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import kotlinx.coroutines.launch

class StationViewModel :BaseViewModel(){

    val getPlantListLiveData = MutableLiveData<Pair<Array<StationModel>?, String?>>()

    /**
     * 获取电站列表
     */
    fun getPlantList() {
        val email = accountService().user()?.email
        viewModelScope.launch {
            val params = hashMapOf<String, String>().apply {
                put("email",email.toString())
            }
            apiService().postForm(
                ApiPath.Plant.STATIONLIST,
                params,
                object : HttpCallback<HttpResult<Array<StationModel>>>() {
                    override fun success(result: HttpResult<Array<StationModel>>) {

                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                    }
                })
        }
    }
}