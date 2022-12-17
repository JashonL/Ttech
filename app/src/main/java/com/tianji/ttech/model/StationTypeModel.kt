package com.tianji.ttech.model

import com.tianji.ttech.view.dialog.ItemName

/**
 * 通用选择器器Model
 */
data class StationTypeModel(val name: String) : ItemName {

    companion object {
        fun convert(names: Array<String>): Array<GeneralItemModel> {
            return Array(names.size) { index -> GeneralItemModel(names[index]) }
        }
    }

    override fun itemName(): String {
        return name
    }
}