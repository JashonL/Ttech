package com.tianji.ttech.model.energy

data class ChartModel(
    val solarTotal: String,
    val gridTotal: String,
    val batTotal: String,
    val loadTotal: String,
    val energyTotal: String,
    val solarList: List<Float>,
    val gridList: List<Float>,
    val batList: List<Float>,
    val loadList: List<Float>
)

