package com.tianji.ttech.service.account

import android.content.Context
import com.ttech.lib.service.account.BaseAccountService
import com.tianji.ttech.app.Foreground
import com.tianji.ttech.ui.account.login.activity.LoginActivity

class DefaultAccountService : BaseAccountService() {

    override fun login(context: Context) {
        LoginActivity.startClearTask(context)
    }

    override fun tokenExpired() {
        logout()
        Foreground.instance().getTopActivity()?.also {
            login(it)
        }
    }
}