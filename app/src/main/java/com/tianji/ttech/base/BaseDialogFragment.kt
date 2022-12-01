package com.tianji.ttech.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.shuoxd.lib.LibApplication
import com.shuoxd.lib.service.ServiceManager
import com.shuoxd.lib.service.account.IAccountService
import com.shuoxd.lib.service.device.IDeviceService
import com.shuoxd.lib.service.http.IHttpService
import com.shuoxd.lib.service.location.ILocationService
import com.shuoxd.lib.service.storage.IStorageService
import com.shuoxd.lib.util.ViewUtil

/**
 * 基础DialogFragment
 */
open class BaseDialogFragment : DialogFragment(), ServiceManager.ServiceInterface {

    override fun onStart() {
        super.onStart()
        //设置左右边距
        requireDialog().window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = LibApplication.instance().deviceService().getDeviceWidth() - ViewUtil.dp2px(
            requireContext(),
            35f * 2
        )
        requireDialog().window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
    }

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

    fun showDialog() {
        (activity as? BaseActivity)?.showDialog()
    }

    fun dismissDialog() {
        (activity as? BaseActivity)?.dismissDialog()
    }
}