package com.tianji.ttech.base

import androidx.lifecycle.ViewModel
import com.ttech.lib.LibApplication
import com.ttech.lib.service.ServiceManager
import com.ttech.lib.service.account.IAccountService
import com.ttech.lib.service.device.IDeviceService
import com.ttech.lib.service.http.IHttpService
import com.ttech.lib.service.location.ILocationService
import com.ttech.lib.service.storage.IStorageService

open class BaseViewModel : ViewModel(), ServiceManager.ServiceInterface {

    override fun apiService(): IHttpService {
        return LibApplication.instance().apiService()
    }

    override fun storageService(): IStorageService {
        return LibApplication.instance().storageService()
    }

    override fun deviceService(): IDeviceService {
        return LibApplication.instance().deviceService()
    }

    override fun accountService(): IAccountService {
        return LibApplication.instance().accountService()
    }

    override fun locationService(): ILocationService {
        return LibApplication.instance().locationService()
    }

}