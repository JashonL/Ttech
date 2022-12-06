package com.tianji.ttech.ui

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.databinding.DataBindingUtil.setContentView
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivityLoginRegisterBinding
import com.tianji.ttech.ui.account.login.activity.LoginActivity
import com.tianji.ttech.ui.account.register.RegisterActivity

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