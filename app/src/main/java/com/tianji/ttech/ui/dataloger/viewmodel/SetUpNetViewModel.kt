package com.tianji.ttech.ui.dataloger.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.service.ble.BleManager

class SetUpNetViewModel : BaseViewModel() {

    private val bleLiveData = MutableLiveData<BleManager?>()

    var bleManager: BleManager? = null


    fun getBleManager(context: Context, bindServiceListeners: BleManager.bindServiceListeners) {
        bleLiveData.value = BleManager(context, bindServiceListeners)
        bleManager = bleLiveData.value
    }


}