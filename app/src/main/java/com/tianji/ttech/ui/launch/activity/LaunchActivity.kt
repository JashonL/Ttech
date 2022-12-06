package com.tianji.ttech.ui.launch.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.growatt.atess.ui.launch.fragment.UserAgreementDialog
import com.growatt.atess.ui.launch.monitor.UserAgreementMonitor
import com.tianji.ttech.app.TtechApplication
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivityLaunchBinding
import com.tianji.ttech.ui.MainActivity
import com.tianji.ttech.ui.account.login.activity.LoginActivity
import com.ttech.lib.view.statusbar.StatusBarCompat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * 启动页
 */
class LaunchActivity :BaseActivity() {

    private lateinit var binding: ActivityLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        StatusBarCompat.translucentStatusBar(this, true)
        StatusBarCompat.setWindowLightStatusBar(this, true)

        lifecycleScope.launch {
            if (fetchIsAgreeResult()) {
                enterApp()
            } else {
                finish()
            }
        }

    }



    /**
     * 获取是否同意隐私政策结果
     */
    private suspend fun fetchIsAgreeResult(): Boolean = suspendCancellableCoroutine {
        if (TtechApplication.instance().isAgree()) {
            it.resumeWith(Result.success(true))
        } else {
            UserAgreementDialog.showDialog(supportFragmentManager)
            UserAgreementMonitor.watch(lifecycle) { isAgree, _ ->
                it.resumeWith(Result.success(isAgree))
            }
        }
    }

    private suspend fun enterApp() {
        delay(2000)
        if (accountService().isLogin()) {
            MainActivity.start(this)
        } else {
            LoginActivity.start(this)
        }
        finish()
    }

}