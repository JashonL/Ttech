package com.tianji.ttech.ui

import android.os.Bundle
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivityLoginRegisterBinding

class LoginAndRegisterActivity :BaseActivity(){

    lateinit var binding:ActivityLoginRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}