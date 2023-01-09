package com.tianji.ttech.model.energy

data class ChartModel(
    val solarTotal: String,
    val gridTotal: String,
    val batTotal: String,
    val loadTotal: String,
    val energyTotal: String,
    val solarList: Array<Float>?,
    val gridList: Array<Float>?,
    val batList: Array<Float>?,
    val loadList: Array<Float>?,
    val timeList:Array<String>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChartModel

        if (solarTotal != other.solarTotal) return false
        if (gridTotal != other.gridTotal) return false
        if (batTotal != other.batTotal) return false
        if (loadTotal != other.loadTotal) return false
        if (energyTotal != other.energyTotal) return false
        if (!solarList.contentEquals(other.solarList)) return false
        if (!gridList.contentEquals(other.gridList)) return false
        if (!batList.contentEquals(other.batList)) return false
        if (!loadList.contentEquals(other.loadList)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = solarTotal.hashCode()
        result = 31 * result + gridTotal.hashCode()
        result = 31 * result + batTotal.hashCode()
        result = 31 * result + loadTotal.hashCode()
        result = 31 * result + energyTotal.hashCode()
        result = 31 * result + solarList.contentHashCode()
        result = 31 * result + gridList.contentHashCode()
        result = 31 * result + batList.contentHashCode()
        result = 31 * result + loadList.contentHashCode()
        return result
    }
}

