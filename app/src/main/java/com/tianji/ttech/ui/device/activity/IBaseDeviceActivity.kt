package com.tianji.ttech.ui.device.activity

import android.content.Context
import com.tianji.ttech.model.DeviceType

/**
 * 设备详情基类
 */
interface IBaseDeviceActivity {

    companion object {
        /**
         * 跳转到设备详情页面
         */
        fun jumpToDeviceInfoPage(
            context: Context?,
            @DeviceType deviceType: Int,
            deviceSN: String?
        ) {
            when (deviceType) {
                DeviceType.INVERTER -> {
                    context?.let { InverterActivity.start(it, deviceSN.toString()) }
                }
                DeviceType.HVBOX -> {

                }
            }
        }
    }

    fun getDeviceType(): Int
}