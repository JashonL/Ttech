package com.tianji.ttech.ui.array

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.model.ImpactModel
import com.tianji.ttech.model.PlantModel
import com.tianji.ttech.model.energy.ChartModel
import com.tianji.ttech.service.http.ApiPath
import com.tianji.ttech.ui.array.model.ArrayModel
import com.tianji.ttech.ui.chart.ChartListDataModel
import com.tianji.ttech.ui.chart.ChartYDataList
import com.tianji.ttech.ui.common.model.DataType
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import com.ttech.lib.util.DateUtils
import kotlinx.coroutines.launch
import java.util.*

class ArrayViewModel : BaseViewModel() {


    val arrayLiveData = MutableLiveData<Pair<Boolean, Array<ArrayModel>?>>()



    var currentStation: PlantModel? = null

    var dateType = DataType.TOTAL

    var time: String = ""


    private val arrayApi = listOf(
        ApiPath.Plant.GET_ARRAY_DATAYEAR,
        ApiPath.Plant.GET_ARRAY_DATAMONTH,
        ApiPath.Plant.GET_ARRAY_DATADAY
    )




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
    fun getArrayData() {
        viewModelScope.launch {
            val params = hashMapOf<String, String>().apply {
                put("stationId", currentStation?.id.toString())
                put("time", time)
            }
            apiService().postForm(
                arrayApi[dateType],
                params,
                object : HttpCallback<HttpResult<Array<ArrayModel>>>() {
                    override fun success(result: HttpResult<Array<ArrayModel>>) {


                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                        arrayLiveData.value = Pair(false, null)
                    }
                })
        }
    }



}