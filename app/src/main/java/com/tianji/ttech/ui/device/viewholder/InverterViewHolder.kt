package com.tianji.ttech.ui.device.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tianji.ttech.R
import com.tianji.ttech.base.OnItemClickListener
import com.tianji.ttech.databinding.Xte8kHolderBinding
import com.tianji.ttech.model.DeviceModel

class InverterViewHolder(
    itemView: View,
    onItemClickListener: OnItemClickListener
) : BaseDeviceViewHolder(itemView, onItemClickListener) {


    companion object {
        fun create(
            parent: ViewGroup,
            onItemClickListener: OnItemClickListener
        ): InverterViewHolder {
            val binding = Xte8kHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            val holder = InverterViewHolder(binding.root, onItemClickListener)
            holder.binding = binding
            binding.root.setOnClickListener(holder)
            return holder
        }
    }

    private lateinit var binding: Xte8kHolderBinding

    override fun bindData(deviceModel: DeviceModel) {
        binding.tvDeviceName.text = deviceModel.inverterSn
//        binding.tvStatus.text = deviceModel.deviceName
//        binding.tvBatPercent.text = deviceModel.deviceName

        val s = getString(R.string.m188_device_type) + ":" + deviceModel.deviceType
        binding.tvDeviceType.text =s
    }
}