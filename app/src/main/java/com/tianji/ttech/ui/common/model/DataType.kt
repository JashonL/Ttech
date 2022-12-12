package com.tianji.ttech.ui.common.model

import androidx.annotation.IntDef

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
    }
}