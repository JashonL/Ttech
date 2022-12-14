package com.ttech.lib.util

import android.util.Log
import com.ttech.lib.BuildConfig

object LogUtil {

    fun i(tag: String, log: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, log)
        }
    }
}