package com.tianji.ttech.ui.energy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.model.StationModel
import com.tianji.ttech.model.energy.ChartModel
import com.tianji.ttech.service.http.ApiPath
import com.tianji.ttech.ui.chart.ChartListDataModel
import com.tianji.ttech.ui.chart.ChartYDataList
import com.tianji.ttech.ui.common.model.DataType
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import com.ttech.lib.util.DateUtils
import kotlinx.coroutines.launch
import java.util.Date

class EnergyViewModel : BaseViewModel() {

    val impactLiveData = MutableLiveData<Pair<Boolean, Array<StationModel>?>>()

    val stationLiveData = MutableLiveData<Pair<Boolean, ChartModel?>>()


    val chartLiveData = MutableLiveData<ChartListDataModel>()


    var currentStation: StationModel? = null

    var dateType = DataType.TOTAL

    private val chartApi = listOf(
        ApiPath.Plant.GET_INVERTER_DATATOTAL,
        ApiPath.Plant.GET_INVERTER_DATAYEAR,
        ApiPath.Plant.GET_INVERTER_DATA_MONTH,
        ApiPath.Plant.GET_INVERTER_DATA_DAY
    )


    var time: String = ""


    fun setTime(date: Date) {
        time = when (dateType) {
            DataType.DAY -> DateUtils.yyyy_MM_dd_format(date)
            DataType.MONTH -> DateUtils.yyyy_MM_format(date)
            DataType.YEAR -> DateUtils.yyyy_format(date)
            else -> {
                DateUtils.yyyy_format(date)
            }
        }
    }


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
                object : HttpCallback<HttpResult<ChartModel>>() {
                    override fun success(result: HttpResult<ChartModel>) {
                        val chartModel = result.obj

                        chartModel?.let {
                            val loadList = it.loadList
                            val timeList = mutableListOf<String>()
                            for (i in loadList.indices) {
                                timeList.add(i.toString())
                            }

                            val dataList = mutableListOf(
                                ChartYDataList(it.batList, "bat"),
                                ChartYDataList(it.gridList, "grid"),
                                ChartYDataList(it.solarList, "solar"),
                                ChartYDataList(it.loadList, "load"),
                            )

                            val chartListDataModel =
                                ChartListDataModel(timeList.toTypedArray(), dataList.toTypedArray())
                            chartLiveData.value = chartListDataModel

                        }
                        stationLiveData.value = Pair(result.isBusinessSuccess(), result.obj)
                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                        stationLiveData.value = Pair(false, null)
                    }
                })
        }
    }


    /**
     * 获取电站数据
     */
    fun getPlantImpactData() {
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
                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                    }
                })
        }
    }


}