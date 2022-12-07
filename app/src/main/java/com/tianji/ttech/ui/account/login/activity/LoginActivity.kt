package com.tianji.ttech.ui.account.login.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.viewModels
import com.tianji.ttech.ui.common.activity.WebActivity
import com.growatt.atess.ui.common.viewmodel.StaticResourceViewModel
import com.tianji.ttech.R
import com.tianji.ttech.app.TtechApplication.Companion.APP_OS
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivityLoginBinding
import com.tianji.ttech.ui.MainActivity
import com.tianji.ttech.ui.account.forgot.ForgotPasswordActivity
import com.tianji.ttech.ui.account.login.viewmodel.LoginViewModel
import com.ttech.lib.service.account.User
import com.ttech.lib.util.MD5Util
import com.ttech.lib.util.ToastUtil
import com.ttech.lib.util.Util

class LoginActivity : BaseActivity(), View.OnClickListener {

    companion object {

        fun start(context: Context?) {
            context?.startActivity(Intent(context, LoginActivity::class.java))
        }

        fun startClearTask(context: Context?) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context?.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityLoginBinding

    //是否显示密码


    private val viewModel: LoginViewModel by viewModels()
    private val staticResourceViewModel: StaticResourceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        setListener()
    }

    private fun initData() {
        viewModel.loginLiveData.observe(this) {
            dismissDialog()
            if (it.second == null) {
                val user = it.first
                loginSuccess(user)
            } else {
                ToastUtil.show(it.second)
            }
        }
        staticResourceViewModel.staticResourceLiveData.observe(this) {
            dismissDialog()
            if (it.second == null) {
                WebActivity.start(this, it.first)
            } else {
                ToastUtil.show(it.second)
            }
        }
    }



    private fun loginSuccess(user: User?) {
        accountService().saveUserInfo(user)
        MainActivity.start(this)
        finish()
    }

    private fun setListener() {
        binding.btnLogin.setOnClickListener(this)
        binding.tvForgetPassword.setOnClickListener(this)

    }









    override fun onClick(v: View?) {
        when {
            v === binding.btnLogin -> {
                checkInfo()
            }

            v===binding.tvForgetPassword->{
                ForgotPasswordActivity.start(this)
            }

        }
    }

    private fun checkInfo() {
        val userName = binding.etUsername.getValue()
        val password = binding.etPassword.getValue()
        when {
            TextUtils.isEmpty(userName) -> {
                ToastUtil.show(getString(R.string.m7_username_cant_empty))
            }
            TextUtils.isEmpty(password) -> {
                ToastUtil.show(getString(R.string.m8_password_cant_empty))
            }

            else -> {
                //校验成功
                showDialog()
                login(password!!,userName!!)
            }
        }
    }


    private fun login(password: String, userName: String) {
        if (!TextUtils.isEmpty(password)) {
            showDialog()
            val pwd_md5 = MD5Util.md5(password)
            var version = Util.getVersion(this)
            val phoneModel = Util.getPhoneModel()
            if (version == null) version = "";
            if (pwd_md5 != null) {
                viewModel.login(userName, pwd_md5, APP_OS, phoneModel, version)
            }
        }

    }



}