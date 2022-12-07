package com.tianji.ttech.ui

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.tianji.ttech.ui.account.register.RegisterActivity
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivityLoginRegisterBinding
import com.tianji.ttech.ui.account.login.activity.LoginActivity

class LoginAndRegisterActivity :BaseActivity(),OnClickListener{

    lateinit var binding:ActivityLoginRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setLiseners()
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