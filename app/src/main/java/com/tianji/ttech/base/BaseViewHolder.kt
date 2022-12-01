package com.tianji.ttech.base

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.shuoxd.lib.service.ServiceManager
import com.shuoxd.lib.service.account.IAccountService
import com.shuoxd.lib.service.device.IDeviceService
import com.shuoxd.lib.service.http.IHttpService
import com.shuoxd.lib.service.location.ILocationService
import com.shuoxd.lib.service.storage.IStorageService
import com.tianji.ttech.app.TtechApplication

open class BaseViewHolder(
    itemView: View,
    private val onItemClickListener: OnItemClickListener? = null
) : RecyclerView.ViewHolder(itemView),
    ServiceManager.ServiceInterface, View.OnClickListener, View.OnLongClickListener {

    fun showDialog() {
        (itemView.context as? BaseActivity)?.showDialog()
    }

    fun dismissDialog() {
        (itemView.context as? BaseActivity)?.dismissDialog()
    }

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
        return TtechApplication.instance().locationService()
    }

    override fun onClick(v: View?) {
        onItemClickListener?.onItemClick(v, bindingAdapterPosition)
    }

    fun getColor(@ColorRes colorId: Int): Int {
        return TtechApplication.instance().resources.getColor(colorId)
    }

    fun getString(@StringRes stringId: Int): String {
        return TtechApplication.instance().getString(stringId)
    }

    override fun onLongClick(v: View?): Boolean {
        onItemClickListener?.onItemLongClick(v, bindingAdapterPosition)
        return true
    }



}

interface OnItemClickListener {

    fun onItemClick(v: View?, position: Int) {}

    fun onItemLongClick(v: View?, position: Int) {}
}