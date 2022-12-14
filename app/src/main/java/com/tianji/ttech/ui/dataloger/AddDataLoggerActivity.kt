package com.tianji.ttech.ui.dataloger

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.tianji.ttech.ui.common.activity.ScanActivity
import com.growatt.atess.ui.common.fragment.RequestPermissionHub
import com.tianji.ttech.R
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivityAddDataLoggerBinding
import com.tianji.ttech.ui.station.viewmodel.AddDataLoggerViewModel
import com.ttech.lib.util.ActivityBridge
import com.ttech.lib.util.ToastUtil
import com.ttech.lib.util.Util

/**
 * 添加采集器页面
 */
class AddDataLoggerActivity : BaseActivity(), View.OnClickListener {

    companion object {

        private const val KEY_PLANT_ID = "key_plant_id"
        private const val KEY_DATALOGER_SN = "key_dataloger_sn"

        fun start(context: Context?, plantId: String?, datalogSn: String?) {
            context?.startActivity(getIntent(context, plantId, datalogSn))
        }

        fun getIntent(context: Context?, plantId: String?, datalogSn: String?): Intent {
            return Intent(context, AddDataLoggerActivity::class.java).also {
                it.putExtra(KEY_PLANT_ID, plantId)
                it.putExtra(KEY_DATALOGER_SN, datalogSn)
            }
        }

    }

    private lateinit var binding: ActivityAddDataLoggerBinding
    private val viewModel: AddDataLoggerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDataLoggerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        setListener()
    }

    private fun initData() {
        viewModel.plantId = intent.getStringExtra(KEY_PLANT_ID)
        val datalogSn = intent.getStringExtra(KEY_DATALOGER_SN)
        val validate = Util.validateWebbox(datalogSn)
        binding.etDataLoggerSn.setText(datalogSn)
        binding.etCheckCode.setText(validate)

        viewModel.addDataLoggerLiveData.observe(this) {
            dismissDialog()
            if (it == null) {
                //去配网
                SetUpNetActivity.start(this)
                finish()
            } else {
                ToastUtil.show(it)
            }
        }
        viewModel.getCheckCodeLiveData.observe(this) {
            dismissDialog()
            if (it.second == null) {
                binding.etCheckCode.setText(it.first)
            } else {
                ToastUtil.show(it.second)
            }
        }
    }

    private fun setListener() {
        binding.ivScan.setOnClickListener(this)
        binding.btFinish.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when {
            v === binding.ivScan -> {
                RequestPermissionHub.requestPermission(
                    supportFragmentManager,
                    arrayOf(Manifest.permission.CAMERA)
                ) {
                    if (it) {
                        scan()
                    }
                }
            }
            v === binding.btFinish -> {
                val dataLoggerSN = binding.etDataLoggerSn.text.toString().trim()
                val checkCode = binding.etCheckCode.text.toString().trim()
                when {
                    dataLoggerSN.isEmpty() -> {
                        ToastUtil.show(getString(R.string.m90_serial_number_not_null))
                    }
                    checkCode.isEmpty() -> {
                        ToastUtil.show(getString(R.string.m91_check_code_not_null))
                    }
                    else -> {
                        showDialog()
                        viewModel.addDataLogger(dataLoggerSN, checkCode)
                    }
                }
            }
        }
    }

    private fun scan() {
        ActivityBridge.startActivity(
            this,
            ScanActivity.getIntent(this),
            object : ActivityBridge.OnActivityForResult {
                override fun onActivityForResult(
                    context: Context?,
                    resultCode: Int,
                    data: Intent?
                ) {
                    if (resultCode == RESULT_OK && data?.hasExtra(ScanActivity.KEY_CODE_TEXT) == true) {
                        val dataLoggerSN = data.getStringExtra(ScanActivity.KEY_CODE_TEXT)
                        binding.etDataLoggerSn.setText(dataLoggerSN)
                        dataLoggerSN?.also {
                            showDialog()
                            binding.etCheckCode.setText(Util.validateWebbox(it))
                        }
                    }
                }
            })
    }


    private fun toConfig() {

    }


}