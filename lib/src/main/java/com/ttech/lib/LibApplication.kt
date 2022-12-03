package com.ttech.lib

import android.app.Application
import com.ttech.lib.service.ServiceManager

abstract class LibApplication : Application(), ServiceManager.ServiceInterface {

    companion object {
        private lateinit var instance: LibApplication
        fun instance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}