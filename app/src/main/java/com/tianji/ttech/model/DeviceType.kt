package com.tianji.ttech.model

import androidx.annotation.IntDef
import com.tianji.ttech.R
import com.tianji.ttech.app.TtechApplication
import com.tianji.ttech.ui.device.model.PCS

/**
 * 设备状态
 */
@IntDef(
    DeviceType.INVERTER,
    DeviceType.XP,
    DeviceType.HVBOX,
    DeviceType.MGRN_INVERTER,
)
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DeviceType {
    companion object {
        const val INVERTER = 1
        const val XP = 2
        const val HVBOX = 3
        const val MGRN_INVERTER = 4


        fun getDeviceTypeName(@DeviceType deviceType: Int): String {
            return TtechApplication.instance().getString(
                when (deviceType) {
                    INVERTER -> R.string.m187_inverter
                    XP -> R.string.m128_xp
                    MGRN_INVERTER->R.string.m130_mgrn_inverter
                    else -> R.string.m129_hvbox
                }
            )
        }
    }
}