package com.tianji.ttech.ui.dataloger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivitySetUpNetBinding
import com.tianji.ttech.service.ble.BleManager
import com.tianji.ttech.ui.dataloger.viewmodel.SetUpNetViewModel
import com.ttech.lib.util.LogUtil


class SetUpNetActivity : BaseActivity() {


    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, SetUpNetActivity::class.java))
        }
    }


    private lateinit var binding: ActivitySetUpNetBinding


    private val viewModel: SetUpNetViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUpNetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getBleManager(this, object : BleManager.bindServiceListeners {
            override fun onServiceConnected() {

                //打开蓝牙
                viewModel.bleManager?.openBle()

            }

            override fun onServiceDisconnected() {
            }

        })




    }

    override fun onResume() {
        super.onResume()

    }


}