package com.tianji.ttech.ui.device.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.service.http.ApiPath
import com.tianji.ttech.ui.device.model.OperatorLogModel
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import kotlinx.coroutines.launch

class OperationViewModel : BaseViewModel() {


    val operatorLiveData = MutableLiveData<Pair<Boolean, Array<OperatorLogModel>?>>()

    /**
     * 获取电站数据
     */
    fun operationlist() {
        viewModelScope.launch {
            val email = accountService().user()?.email
            val params = hashMapOf<String, String>().apply {
                put("email", email.toString())

            }
            apiService().postForm(
                ApiPath.OperatorLog.OPERATIONLIST,
                params,
                object : HttpCallback<HttpResult<Array<OperatorLogModel>>>() {
                    override fun success(result: HttpResult<Array<OperatorLogModel>>) {
                        operatorLiveData.value = Pair(result.isBusinessSuccess(), result.obj)
                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                        operatorLiveData.value = Pair(false, null)

                    }
                })
        }
    }
}