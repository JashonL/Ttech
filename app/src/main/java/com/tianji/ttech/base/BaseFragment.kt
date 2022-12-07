package com.tianji.ttech.base

import android.view.View
import androidx.fragment.app.Fragment
import com.ttech.lib.LibApplication
import com.ttech.lib.service.ServiceManager
import com.ttech.lib.service.account.IAccountService
import com.ttech.lib.service.device.IDeviceService
import com.ttech.lib.service.http.IHttpService
import com.ttech.lib.service.location.ILocationService
import com.ttech.lib.service.storage.IStorageService
import com.tianji.ttech.app.TtechApplication

abstract class BaseFragment : Fragment(), ServiceManager.ServiceInterface, IDisplay {


    override fun apiService(): IHttpService {
        return TtechApplication.instance().apiService()
    }

    override fun storageService(): IStorageService {
        return TtechApplication.instance().storageService()
    }

    override fun deviceService(): IDeviceService {
        return TtechApplication.instance().deviceService()
    }

    override fun accountService(): IAccountService {
        return TtechApplication.instance().accountService()
    }

    override fun locationService(): ILocationService {
        return LibApplication.instance().locationService()
    }

    override fun showDialog() {
        (activity as? BaseActivity)?.showDialog()
    }

    override fun dismissDialog() {
        (activity as? BaseActivity)?.dismissDialog()
    }

    override fun showPageErrorView(onRetry: ((view: View) -> Unit)) {
        (activity as? BaseActivity)?.showPageErrorView(onRetry)
    }

    override fun hidePageErrorView() {
        (activity as? BaseActivity)?.hidePageErrorView()
    }

    override fun showPageLoadingView() {
        (activity as? BaseActivity)?.showPageLoadingView()
    }

    override fun hidePageLoadingView() {
        (activity as? BaseActivity)?.hidePageLoadingView()
    }

    override fun showResultDialog(
        result: String?,
        onCancelClick: (() -> Unit)?,
        onComfirClick: (() -> Unit)?
    ) {
        (activity as? BaseActivity)?.showResultDialog(result, onCancelClick, onComfirClick)
    }

}