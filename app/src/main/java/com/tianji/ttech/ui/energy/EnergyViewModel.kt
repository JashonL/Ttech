package com.tianji.ttech.ui.energy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.model.ImpactModel
import com.tianji.ttech.model.PlantModel
import com.tianji.ttech.model.energy.ChartModel
import com.tianji.ttech.service.http.ApiPath
import com.tianji.ttech.ui.chart.ChartListDataModel
import com.tianji.ttech.ui.chart.ChartYDataList
import com.tianji.ttech.ui.common.model.DataType
import com.ttech.lib.service.http.HttpCallBackString
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import com.ttech.lib.util.DateUtils
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Date

class EnergyViewModel : BaseViewModel() {


    val stationLiveData = MutableLiveData<Pair<Boolean, ChartModel?>>()

    val chartLiveData = MutableLiveData<ChartListDataModel>()

    val impactChartLiveData = MutableLiveData<ChartListDataModel>()

    val impactLiveData = MutableLiveData<ImpactModel?>()


    var currentStation: PlantModel? = null

    var dateType = DataType.TOTAL

    private val chartApi = listOf(
        ApiPath.Plant.GET_INVERTER_DATATOTAL,
        ApiPath.Plant.GET_INVERTER_DATAYEAR,
        ApiPath.Plant.GET_INVERTER_DATA_MONTH,
        ApiPath.Plant.GET_INVERTER_DATA_DAY
    )

    private val impactApi = listOf(
        ApiPath.Plant.GET_IMPACT_TOTAL,
        ApiPath.Plant.GET_IMPACT_YEAR,
        ApiPath.Plant.GET_IMPACT_MONTH,
        ApiPath.Plant.GET_IMPACT_DAY
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
                object : HttpCallBackString() {
                    override fun onFailure(errorModel: HttpErrorModel) {
                        stationLiveData.value = Pair(false, null)
                    }

                    override fun success(result: String?) {
                        val jsonObject = result?.let { JSONObject(it) }
                        val respone = jsonObject?.optJSONObject("result")?:"1"
                        val objJsonObject = jsonObject?.optJSONObject("obj")

                        objJsonObject?.let {
                            val solarTotal = it.optString("solarTotal")
                            val gridTotal = it.optString("gridTotal")
                            val batTotal = it.optString("batTotal")
                            val loadTotal = it.optString("loadTotal")
                            val energyTotal = it.optString("energyTotal")

                            //解析图表数据
                            val solarMap = it.optJSONObject("solarMap")
                            val gridMap = it.optJSONObject("gridMap")
                            val batMap = it.optJSONObject("batMap")
                            val loadMap = it.optJSONObject("loadMap")

                            val timeList: MutableList<String> = mutableListOf()
                            val solarList: MutableList<Float> = mutableListOf()
                            val gridList: MutableList<Float> = mutableListOf()
                            val batList: MutableList<Float> = mutableListOf()
                            val loadList: MutableList<Float> = mutableListOf()


                            solarMap.apply {
                                val keys = this?.keys()
                                val iterator = keys?.iterator()
                                while (iterator?.hasNext() == true) {
                                    val key = iterator.next()
                                    val value = this?.optString(key)
                                    timeList.add(key)
                                    solarList.add(value!!.toFloat())
                                }
                            }


                            gridMap.apply {
                                val keys = this?.keys()
                                val iterator = keys?.iterator()
                                while (iterator?.hasNext() == true) {
                                    val key = iterator.next()
                                    val value = this?.optString(key)
                                    gridList.add(value!!.toFloat())
                                }
                            }



                            batMap.apply {
                                val keys = this?.keys()
                                val iterator = keys?.iterator()
                                while (iterator?.hasNext() == true) {
                                    val key = iterator.next()
                                    val value = this?.optString(key)
                                    batList.add(value!!.toFloat())
                                }
                            }


                            loadMap.apply {
                                val keys = this?.keys()
                                val iterator = keys?.iterator()
                                while (iterator?.hasNext() == true) {
                                    val key = iterator.next()
                                    val value = this?.optString(key)
                                    loadList.add(value!!.toFloat())
                                }
                            }


                            val chartModel = ChartModel(
                                solarTotal,
                                gridTotal,
                                batTotal,
                                loadTotal,
                                energyTotal,
                                solarList.toTypedArray(),
                                solarList.toTypedArray(),
                                solarList.toTypedArray(),
                                solarList.toTypedArray(),
                                timeList.toTypedArray()
                            )


                            val dataList = mutableListOf(
                                ChartYDataList(chartModel.batList, "bat"),
                                ChartYDataList(chartModel.gridList, "grid"),
                                ChartYDataList(chartModel.solarList, "solar"),
                                ChartYDataList(chartModel.loadList, "load"),
                            )

                            val chartListDataModel =
                                ChartListDataModel(timeList.toTypedArray(), dataList.toTypedArray())
                            chartLiveData.value = chartListDataModel

                            stationLiveData.value = Pair(respone == "0", chartModel)

                        }

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
                impactApi[dateType],
                params,
                object : HttpCallback<HttpResult<ImpactModel>>() {
                    override fun success(result: HttpResult<ImpactModel>) {
                        val impactModel = result.obj
                        var loadList: Array<Float>? = arrayOf()
                        val timeList = mutableListOf<String>()

                        impactModel?.let {
                             loadList = it.impactList
                            if (loadList == null) loadList= arrayOf()
                            for (i in loadList!!.indices) {
                                timeList.add(i.toString())
                            }
                        }


                        val dataList = mutableListOf(ChartYDataList(loadList, "impact"))
                        val chartListDataModel = ChartListDataModel(timeList.toTypedArray(), dataList.toTypedArray())
                        impactLiveData.value = impactModel
                        impactChartLiveData.value = chartListDataModel

                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                        impactLiveData.value = null
                    }
                })
        }
    }


}