package com.tianji.ttech.ui.account.forgot.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.service.http.ApiPath
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import com.ttech.lib.service.http.IHttpCallback
import kotlinx.coroutines.launch

class FotgotPasswordViewModel : BaseViewModel() {


    val resetPasswordListdata = MutableLiveData<Pair<Boolean?, String?>>()


    fun retrievePassword(
        email: String,
        verificationCode: String,
        password: String,
        confirmPassword: String
    ) {
        val params = hashMapOf<String, String>().apply {
            put("email", email)
            put("verificationCode", verificationCode)
            put("password", password)
            put("confirmPassword", confirmPassword)
        }

        viewModelScope.launch {
            //去请求
            apiService().postForm(
                ApiPath.Mine.RETRIEVEPASSWORD,
                params,
                object : HttpCallback<HttpResult<String>>() {
                    override fun success(result: HttpResult<String>) {
                        resetPasswordListdata.value =
                            Pair(result.isBusinessSuccess(), result.msg ?: "")
                    }

                    override fun onFailure(errorModel: HttpErrorModel) {
                        super.onFailure(errorModel)
                        resetPasswordListdata.value = Pair(false, errorModel.errorMsg ?: "")
                    }

                })


        }

    }


}