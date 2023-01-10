package com.tianji.ttech.ui.device.viewholder

import android.view.View
import android.view.ViewGroup
import com.olp.weco.ui.device.viewholder.HvbatboxViewHolder
import com.tianji.ttech.base.BaseViewHolder
import com.tianji.ttech.base.OnItemClickListener
import com.tianji.ttech.model.DeviceModel
import com.tianji.ttech.model.DeviceType

/**
 * 设备ViewHolder基类
 */
abstract class BaseDeviceViewHolder(
    itemView: View,
    onItemClickListener: OnItemClickListener
) : BaseViewHolder(itemView, onItemClickListener) {

    companion object {
        fun createDeviceViewHolder(
            @DeviceType deviceType: Int, parent: ViewGroup,
            onItemClickListener: OnItemClickListener,
        ): BaseDeviceViewHolder {
            return when (deviceType) {
                DeviceType.INVERTER -> InverterViewHolder.create(parent, onItemClickListener)
                else -> HvbatboxViewHolder.create(parent, onItemClickListener)
            }
        }
    }

    abstract fun bindData(deviceModel: DeviceModel)
}