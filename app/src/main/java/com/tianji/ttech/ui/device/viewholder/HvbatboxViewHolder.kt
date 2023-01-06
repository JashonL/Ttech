package com.olp.weco.ui.device.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tianji.ttech.R
import com.tianji.ttech.base.OnItemClickListener
import com.tianji.ttech.databinding.HvBatBoxHolderBinding
import com.tianji.ttech.model.DeviceModel
import com.tianji.ttech.ui.device.viewholder.BaseDeviceViewHolder

class HvbatboxViewHolder(
    itemView: View,
    onItemClickListener: OnItemClickListener
) : BaseDeviceViewHolder(itemView, onItemClickListener) {


    companion object {
        fun create(
            parent: ViewGroup,
            onItemClickListener: OnItemClickListener
        ): HvbatboxViewHolder {
            val binding = HvBatBoxHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            val holder = HvbatboxViewHolder(binding.root, onItemClickListener)
            holder.binding = binding
            binding.root.setOnClickListener(holder)
            return holder
        }
    }

    private lateinit var binding: HvBatBoxHolderBinding

    override fun bindData(deviceModel: DeviceModel) {
        binding.tvDeviceName.text = deviceModel.deviceSn
//        binding.tvStatus.text = deviceModel.deviceName
//        binding.tvBatPercent.text = deviceModel.deviceName

        val s = getString(R.string.m188_device_type) + ":" + deviceModel.getRealDeviceType()
        binding.tvDeviceType.text =s

    }
}