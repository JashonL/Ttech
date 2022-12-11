package com.tianji.ttech.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.viewModels
import com.tianji.ttech.app.TtechApplication
import com.tianji.ttech.ui.account.register.RegisterActivity
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivityLoginRegisterBinding
import com.tianji.ttech.ui.account.login.activity.LoginActivity
import com.tianji.ttech.ui.account.login.viewmodel.LoginViewModel
import com.ttech.lib.util.MD5Util
import com.ttech.lib.util.Util

class LoginAndRegisterActivity :BaseActivity(),OnClickListener{

    private val viewModel: LoginViewModel by viewModels()

    companion object{
        fun start(context: Context) {
            val intent = Intent(context, LoginAndRegisterActivity::class.java)
            context.startActivity(intent)
        }
    }


    lateinit var binding:ActivityLoginRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setLiseners()
        initViews()
    }




    private fun initViews() {
        val user = accountService().user()
        val logout = accountService().isLogout()
        user?.let {
            val email = it.email
            val password = it.password
            if (!logout) {
                login(password, email)
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
                viewModel.login(userName, pwd_md5, TtechApplication.APP_OS, phoneModel, version)
            }
        }

    }


    private fun setLiseners() {
        binding.btLogin.setOnClickListener(this)
        binding.btRegister.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when{
            p0===binding.btLogin-> LoginActivity.start(this)
            p0===binding.btRegister-> RegisterActivity.start(this)
        }
    }


}