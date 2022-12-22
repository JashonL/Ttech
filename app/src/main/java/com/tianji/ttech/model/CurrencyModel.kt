package com.tianji.ttech.model

import com.tianji.ttech.view.dialog.ItemName

data class CurrencyModel(
    val id: Int,
    val incomeUnit: String
) : ItemName {
    override fun itemName(): String {
        return incomeUnit
    }
}