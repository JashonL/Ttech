package com.tianji.ttech.app

import android.app.Application
import android.os.Process
import com.tianji.ttech.service.http.OkhttpService
import com.shuoxd.lib.service.ServiceManager
import com.shuoxd.lib.service.ServiceType
import com.shuoxd.lib.service.account.IAccountService
import com.shuoxd.lib.service.device.IDeviceService
import com.shuoxd.lib.service.http.IHttpService
import com.shuoxd.lib.service.location.ILocationService
import com.shuoxd.lib.service.storage.DefaultStorageService
import com.shuoxd.lib.service.storage.IStorageService
import com.shuoxd.lib.util.Util

class TtechApplication : Application() ,ServiceManager.ServiceInterface{



    companion object {
        private lateinit var instance:TtechApplication
        fun instance()= instance

    }


    override fun onCreate() {
        super.onCreate()
        instance=this
        registerService()
        init()

    }




    private fun init() {
        //过滤掉其它进程
        if (!packageName.equals(Util.getProcessNameByPID(this, Process.myPid()))) {
            return
        }
        registerService()
        Foreground.init(this)


    }



    private fun registerService(){
        ServiceManager.instance().registerService(ServiceType.HTTP, OkhttpService())
    }




    override fun apiService(): IHttpService {
        return ServiceManager.instance().getService(ServiceType.HTTP) as IHttpService
    }

    override fun storageService(): IStorageService {
        return ServiceManager.instance().getService(ServiceType.STORAGE) as DefaultStorageService
    }

    override fun deviceService(): IDeviceService {
        return ServiceManager.instance().getService(ServiceType.DEVICE) as IDeviceService
    }

    override fun accountService(): IAccountService {
        return ServiceManager.instance().getService(ServiceType.ACCOUNT) as IAccountService
    }

    override fun locationService(): ILocationService {
        return ServiceManager.instance().getService(ServiceType.LOCATION) as ILocationService
    }






}