package com.tianji.ttech.ui.energy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.model.StationModel
import com.tianji.ttech.service.http.ApiPath
import com.tianji.ttech.ui.common.model.DataType
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import kotlinx.coroutines.launch

class EnergyViewModel : BaseViewModel() {

    val getPlantListLiveData = MutableLiveData<Pair<Boolean, Array<StationModel>?>>()
    var currentStation: StationModel? = null
    var dateType = DataType.TOTAL

    private val chartApi = listOf(
        ApiPath.Plant.GET_INVERTER_DATATOTAL,
        ApiPath.Plant.GET_INVERTER_DATAYEAR,
        ApiPath.Plant.GET_INVERTER_DATA_MONTH,
        ApiPath.Plant.GET_INVERTER_DATA_DAY
    )


    var time: String = ""


    /**
     * 获取电站数据
     */
    fun getPlantChartData() {
        viewModelScope.launch {
            val params = hashMapOf<String, String>().apply {
                put("stationId", currentStation?.id.toString())
                put("time", time)
            }
            apiService().postForm(
                chartApi[dateType],
                params,
                object : HttpCallback<HttpResult<Array<StationModel>>>() {
                    override fun success(result: HttpResult<Array<StationModel>>) {
                        getPlantListLiveData.value = Pair(result.isBusinessSuccess(), result.obj)
                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                        getPlantListLiveData.value = Pair(false, null)
                    }
                })
        }
    }

}