package com.tianji.ttech.ui.station.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.model.PlantModel
import com.tianji.ttech.model.PlantStatusNumModel
import com.tianji.ttech.service.http.ApiPath
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import kotlinx.coroutines.launch

/**
 * 电站列表
 */
class PlantListViewModel : BaseViewModel() {

    val getPlantListLiveData = MutableLiveData<Pair<Array<PlantModel>?, String?>>()

    val getPlantStatusNumLiveData = MutableLiveData<Pair<PlantStatusNumModel, String?>>()

    val deletePlantLiveData = MutableLiveData<String?>()

    /**
     * 获取电站列表
     */
    fun getPlantList(plantStatus: Int, orderType: Int? = null, searchWord: String = "") {


        viewModelScope.launch {
            val email = accountService().user()?.email
            val params = hashMapOf<String, String>().apply {
                if (plantStatus != PlantModel.PLANT_STATUS_ALL) {
                    put("plantStatus", plantStatus.toString())
                }
                if (orderType != null) {
                    put("order", orderType.toString())
                }
                if (!TextUtils.isEmpty(searchWord)) {
                    put("searchWord", searchWord)
                }

                put("email", email.toString())
            }
            apiService().postForm(
                ApiPath.Plant.STATIONLIST,
                params,
                object : HttpCallback<HttpResult<Array<PlantModel>>>() {
                    override fun success(result: HttpResult<Array<PlantModel>>) {
                        if (result.isBusinessSuccess()) {
                            getPlantListLiveData.value =
                                Pair(result.obj ?: emptyArray(), null)
                        } else {
                            getPlantListLiveData.value = Pair(null, result.msg ?: "")
                        }
                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                        getPlantListLiveData.value = Pair(null, errorModel.errorMsg ?: "")
                    }
                })
        }
    }






    /**
     * 删除电站
     */
    fun deletePlant(plantId: String) {
        viewModelScope.launch {
            val params = hashMapOf<String, String>().apply {
                put("plantId", plantId)
            }
            apiService().postForm(
                ApiPath.Plant.DELETE_PLANT,
                params,
                object : HttpCallback<HttpResult<String?>>() {
                    override fun success(result: HttpResult<String?>) {
                        if (result.isBusinessSuccess()) {
                            deletePlantLiveData.value = null
                        } else {
                            deletePlantLiveData.value = result.msg ?: ""
                        }
                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                        deletePlantLiveData.value = errorModel.errorMsg ?: ""
                    }
                })
        }
    }


}