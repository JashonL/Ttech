package com.tianji.ttech.ui.chart


/**
 * 图表数据模型
 * 时间列表-timeList
 */
data class ChartListDataModel(val timeList: Array<String>?, val dataList: Array<ChartYDataList>?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChartListDataModel

        if (!timeList.contentEquals(other.timeList)) return false
        if (!dataList.contentEquals(other.dataList)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = timeList.contentHashCode()
        result = 31 * result + dataList.contentHashCode()
        return result
    }

    fun getXTimeList(): Array<String> {
        return timeList ?: arrayOf()
    }

    fun getYDataList(): Array<ChartYDataList> {
        return dataList ?: arrayOf()
    }
}

/**
 * Y轴数据模型
 */
data class ChartYDataList(val chartDataList: Array<Float>?, val label: String?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChartYDataList

        if (!chartDataList.contentEquals(other.chartDataList)) return false
        if (label != other.label) return false

        return true
    }

    override fun hashCode(): Int {
        var result = chartDataList.contentHashCode()
        result = 31 * result + label.hashCode()
        return result
    }

    fun getYDataList(): Array<Float> {
        return chartDataList ?: arrayOf()
    }

}
