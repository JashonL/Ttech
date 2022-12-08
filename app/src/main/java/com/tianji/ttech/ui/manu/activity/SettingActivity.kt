package com.tianji.ttech.ui.manu.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.tianji.ttech.R
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivitySettingBinding
import com.tianji.ttech.ui.common.activity.CountryActivity
import com.tianji.ttech.ui.manu.viewmodel.SettingViewModel
import com.tianji.ttech.view.dialog.InputDialog
import com.ttech.lib.service.account.IAccountService
import com.ttech.lib.util.ActivityBridge
import com.ttech.lib.util.ToastUtil


/**
 * 设置页面
 */
class SettingActivity : BaseActivity(), View.OnClickListener,
    IAccountService.OnUserProfileChangeListener {

    companion object {

        fun start(context: Context?) {
            context?.startActivity(Intent(context, SettingActivity::class.java))
        }

    }

    private lateinit var binding: ActivitySettingBinding
    private val viewModel: SettingViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initView()
        setListener()
    }

    private fun setListener() {
        binding.ivAvatar.setOnClickListener(this)
        binding.itemUserName.setOnClickListener(this)
        binding.itemPassword.setOnClickListener(this)
        binding.itemCountry.setOnClickListener(this)
        binding.itemInstallationCode.setOnClickListener(this)


        accountService().addUserProfileChangeListener(this)
    }

    private fun initView() {
        refreshUserProfile()
    }

    private fun refreshUserProfile() {
        Glide.with(this).load(accountService().userAvatar())
            .placeholder(R.drawable.ic_default_avatar)
            .into(binding.ivAvatar)

        binding.itemUserName.setSubName(accountService().user()?.email)
        binding.itemCountry.setSubName(accountService().user()?.country)
        binding.itemInstallationCode.setSubName(accountService().user()?.userType)
    }

    private fun initData() {
        viewModel.logoutLiveData.observe(this) {
            dismissDialog()
            if (it == null) {
                accountService().logout()
                accountService().login(this)
                finish()
            } else {
                ToastUtil.show(it)
            }
        }
        viewModel.uploadUserAvatarLiveData.observe(this) {
            dismissDialog()
            if (it.second == null) {
                accountService().saveUserAvatar(it.first)
                refreshUserProfile()
            } else {
                ToastUtil.show(it.second)
            }
        }
    }

    override fun onClick(v: View?) {
        when {
            v === binding.itemUserName -> {

            }
            v === binding.itemPassword -> ModifyPasswordActivity.start(this)
//            v === binding.itemCountry -> ModifyInstallerNoActivity.start(this)
//            v === binding.itemInstallationCode -> ModifyInstallerNoActivity.start(this)

            v===binding.itemCountry->selectCountry()

            v===binding.itemInstallationCode->{
                val installerCode = accountService().user()?.installerCode
                val title = getString(R.string.m186_installer_code)
                showInstallationDialog(title,installerCode.toString())
            }

        }
    }

    private fun showInstallationDialog(title:String,content:String) {
            InputDialog.showDialog(
                supportFragmentManager,
                content,
                getString(R.string.m18_confirm),
                getString(R.string.m16_cancel),
                title
            ) {

            }

    }


    private fun selectCountry() {
        ActivityBridge.startActivity(
            this,
            CountryActivity.getIntent(this),
            object : ActivityBridge.OnActivityForResult {
                override fun onActivityForResult(
                    context: Context?,
                    resultCode: Int,
                    data: Intent?
                ) {
                    if (resultCode == RESULT_OK && data?.hasExtra(CountryActivity.KEY_AREA) == true) {
                        val country = data.getStringExtra(CountryActivity.KEY_AREA) ?: ""
                        binding.itemCountry.setSubName(country)
                    }
                }
            })
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onUserProfileChange(account: IAccountService) {
        refreshUserProfile()
    }

    override fun onDestroy() {
        accountService().removeUserProfileChangeListener(this)
        super.onDestroy()
    }
}