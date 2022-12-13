package com.tianji.ttech.utils

import com.tianji.ttech.R
import com.tianji.ttech.app.TtechApplication
import com.ttech.lib.util.Util
import java.math.BigDecimal

/**
 * 数值换算工具，根据规则换算出单位
 */
object ValueUtil {
    /**
     * 电量单位：数值转换，基础单位是kWh
     */
    fun valueFromKWh(kwhValue: Double?): Pair<String, String> {
        return when {
            kwhValue == null -> {
                Pair("0", TtechApplication.instance().getString(R.string.m48_kwh))
            }
            kwhValue < 100000 -> {
                Pair(
                    Util.getDoubleText(kwhValue),
                    TtechApplication.instance().getString(R.string.m48_kwh)
                )
            }
            else -> {
                Pair(
                    Util.getDoubleText(kwhValue / 1000),
                    TtechApplication.instance().getString(R.string.m49_mwh)
                )
            }
        }
    }

    /**
     * 功率单位(平均)：数值转换，基础单位是w
     */
    fun valueFromW(wValue: Double?): Pair<String, String> {
        return when {
            wValue == null -> {
                Pair("0", TtechApplication.instance().getString(R.string.m52_kw))
            }
            wValue < 1000000000 -> {
                Pair(
                    Util.getDoubleText(wValue / 1000),
                    TtechApplication.instance().getString(R.string.m52_kw)
                )
            }
            else -> {
                Pair(
                    Util.getDoubleText(wValue / 1000000),
                    TtechApplication.instance().getString(R.string.m53_mw)
                )
            }
        }
    }

    /**
     * 功率单位（峰值）：数值转换，基础单位是w
     * 目前只有组件总功率使用到
     */
    fun valueFromWp(wValue: Double?): Pair<String, String> {
        return when {
            wValue == null -> {
                Pair("0", TtechApplication.instance().getString(R.string.m50_kwp))
            }
            wValue < 100000000 -> {
                Pair(
                    Util.getDoubleText(wValue / 1000),
                    TtechApplication.instance().getString(R.string.m50_kwp)
                )
            }
            else -> {
                Pair(
                    Util.getDoubleText(wValue / 1000000),
                    TtechApplication.instance().getString(R.string.m51_mwp)
                )
            }
        }
    }

    /**
     * 重量单位：数值转换，基础单位是kg
     */
    fun valueFromKG(kgValue: Double?): Pair<String, String> {
        return when {
            kgValue == null -> {
                Pair("0", TtechApplication.instance().getString(R.string.m59_kg))
            }
            kgValue < 100000 -> {
                Pair(
                    Util.getDoubleText(kgValue),
                    TtechApplication.instance().getString(R.string.m59_kg)
                )
            }
            else -> {
                Pair(
                    Util.getDoubleText(kgValue / 1000),
                    TtechApplication.instance().getString(R.string.m60_t)
                )
            }
        }
    }


    /**
     * @param f   源数据
     * @param num 保留的位数
     * @return
     */
    fun roundDouble2String(f: Double, num: Int): String? {
        val bg = BigDecimal(f)
        val f1: Double = bg.setScale(num, BigDecimal.ROUND_HALF_UP).toDouble()
        return f1.toString()
    }



}