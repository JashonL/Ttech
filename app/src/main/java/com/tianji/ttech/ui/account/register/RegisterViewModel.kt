package com.tianji.ttech.ui.account.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.service.http.ApiPath
import com.ttech.lib.service.account.User
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import com.ttech.lib.util.MD5Util
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegisterViewModel : BaseViewModel() {

    val registerLiveData = MutableLiveData<String?>()

    /**
     * 是否同意隐私政策
     */
    var isAgree = false


    var selectArea: String = ""




    /**
     * 注册
     */
    fun register(
        country: String, timeZone: String,
        email: String, password: String,
        verificationCode: String, installerCode: String
    ) {
        viewModelScope.launch {
            val params = hashMapOf<String, String>().apply {
                put("country", country)
                put("timeZone", timeZone)
                put("email", email)
                put("password", password)
                put("verificationCode", verificationCode)
                put("installerCode", installerCode)

            }
            apiService().postForm(ApiPath.Mine.REGISTER, params, object :
                HttpCallback<HttpResult<User>>() {
                override fun success(result: HttpResult<User>) {
                    if (result.isBusinessSuccess()) {
                        registerLiveData.value = null
                    } else {
                        registerLiveData.value = result.msg ?: ""
                    }
                }

                override fun onFailure(errorModel: HttpErrorModel) {
                    registerLiveData.value = errorModel.errorMsg ?: ""
                }

            })
        }
    }


}