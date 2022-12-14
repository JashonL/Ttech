package com.tianji.ttech.ui.dataloger

import android.os.Bundle
import android.os.PersistableBundle
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivityDatalogerConnectingBinding

class DatalogerConnectingActivity : BaseActivity() {

    private lateinit var binding: ActivityDatalogerConnectingBinding


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityDatalogerConnectingBinding.inflate(layoutInflater)
    }
}