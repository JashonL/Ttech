package com.ttech.bluetooth.util.util

import java.util.*
import kotlin.experimental.and

object ByteDataUtils {


    /**
     * 将byte[2]转成byte[2]
     *
     * @return
     */
    fun byte2Int(b: ByteArray): Int {
        var value = 0
        if (b.size > 1) {
            value = 0x000000ff and b[0].toInt() shl 8 and 0x0000ff00 or (0x000000ff and b[1].toInt())
            return value
        }
        return value
    }


    /**
     * byte数组转为String
     */
    fun bytesToHexString(bytes: ByteArray): String {
        val result = StringBuilder()
        for (aByte in bytes) {
            var hexString = Integer.toHexString((aByte and 0xFF.toByte()).toInt())
            if (hexString.length == 1) {
                hexString = "0$hexString"
            }
            result.append(hexString.uppercase(Locale.getDefault()))
        }
        return result.toString()
    }


    /**
     * 将int转成byte[2]
     *
     * @param a
     * @return
     */
    fun int2Byte(a: Int): ByteArray {
        val b = ByteArray(2)
        b[0] = (a shr 8).toByte()
        b[1] = a.toByte()
        return b
    }



}