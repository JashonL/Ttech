package com.tianji.ttech.ui.common.model

import androidx.annotation.IntDef
import com.tianji.ttech.R
import com.tianji.ttech.app.TtechApplication

/**
 * 搜索Type
 */
@IntDef(
    DataType.TOTAL,
    DataType.YEAR,
    DataType.MONTH,
    DataType.DAY
)
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
annotation class DataType {
    companion object {
        const val TOTAL = 0
        const val YEAR = 1
        const val MONTH = 2
        const val DAY = 3


        fun getDateNameByType(@DataType type: Int): String {
            return when (type) {
                DataType.TOTAL -> TtechApplication.instance().getString(R.string.m89_total)
                DataType.YEAR ->  TtechApplication.instance().getString(R.string.m72_year)
                DataType.MONTH ->  TtechApplication.instance().getString(R.string.m71_month)
                DataType.DAY ->  TtechApplication.instance().getString(R.string.m70_day)
                else -> {
                    ""
                }
            }
        }

    }


}