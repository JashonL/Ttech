package com.tianji.ttech.ui.device.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tianji.ttech.ui.device.activity.IBaseDeviceActivity
import com.tianji.ttech.ui.device.viewholder.BaseDeviceViewHolder
import com.tianji.ttech.base.BaseViewHolder
import com.tianji.ttech.base.OnItemClickListener
import com.tianji.ttech.base.viewholder.EmptyViewHolder
import com.tianji.ttech.model.DeviceModel

/**
 * 我的设备列表
 */
class DeviceAdapter : RecyclerView.Adapter<BaseViewHolder>(), OnItemClickListener {


    companion object {
        const val ADAPTER_DATA_EMPTY = -2
    }


    private val deviceList = mutableListOf<DeviceModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == ADAPTER_DATA_EMPTY) {
            EmptyViewHolder.create(parent)
        } else {
            BaseDeviceViewHolder.createDeviceViewHolder(viewType, parent, this)

        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is BaseDeviceViewHolder) {
            holder.bindData(deviceList[position])
        }
    }

    override fun getItemCount(): Int {
        if (deviceList.isEmpty()) {
            return 1
        }
        return deviceList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (deviceList.isEmpty()) {
            return ADAPTER_DATA_EMPTY
        }
        return deviceList[position].getRealDeviceType()
    }

    override fun onItemClick(v: View?, position: Int) {
        IBaseDeviceActivity.jumpToDeviceInfoPage(
            v?.context,
            getItemViewType(position),
            deviceList[position].deviceSn
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(deviceList: List<DeviceModel>?) {
        this.deviceList.clear()
        if (!deviceList.isNullOrEmpty()) {
            this.deviceList.addAll(deviceList)
        }
        notifyDataSetChanged()
    }
}