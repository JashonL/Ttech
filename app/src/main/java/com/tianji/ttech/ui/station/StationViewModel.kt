package com.tianji.ttech.ui.station

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class StationViewModel {

    private val _text = MutableLiveData<String>().apply {
        value = "This is station Fragment"
    }
    val text: LiveData<String> = _text
}