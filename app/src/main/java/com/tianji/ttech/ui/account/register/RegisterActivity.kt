package com.tianji.ttech.ui.account.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivityRegisterBinding
import com.tianji.ttech.ui.account.login.activity.LoginActivity

class RegisterActivity : BaseActivity() {

    companion object {

        fun start(context: Context?) {
            context?.startActivity(Intent(context, RegisterActivity::class.java))
        }


    }

    private lateinit var bind:ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        bind= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bind.root)


    }

}