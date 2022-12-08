package com.tianji.ttech.ui.account.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tianji.ttech.base.BaseViewModel
import com.tianji.ttech.model.LoginModel
import com.tianji.ttech.service.http.ApiPath
import com.ttech.lib.service.account.User
import com.ttech.lib.service.http.HttpCallback
import com.ttech.lib.service.http.HttpErrorModel
import com.ttech.lib.service.http.HttpResult
import com.ttech.lib.util.MD5Util
import kotlinx.coroutines.launch

/**
 * 登录
 */
class LoginViewModel : BaseViewModel() {

    val loginLiveData = MutableLiveData<Pair<User?, String?>>()

    /**
     * 登录
     */
    fun login(
        email: String,
        password: String,
        phoneOs: Int,
        phoneModel: String,
        appVersion: String
    ) {

        val params = hashMapOf<String, String>().apply {
            put("email", email)
            put("password", password)
            put("phoneOs", phoneOs.toString())
            put("phoneModel", phoneModel)
            put("appVersion", appVersion)

        }
        viewModelScope.launch {
            apiService().postForm(
                ApiPath.Mine.LOGIN,
                params,
                object : HttpCallback<HttpResult<LoginModel>>() {
                    override fun success(result: HttpResult<LoginModel>) {
                        val loginModel = result.obj
                        if (result.isBusinessSuccess()&&loginModel!=null){
                            loginLiveData.value = Pair(loginModel.user, null)
                        }else{
                            loginLiveData.value = Pair(null, result.msg ?: "")
                        }

                    }


                    override fun onFailure(errorModel: HttpErrorModel) {
                        loginLiveData.value = Pair(null, errorModel.errorMsg ?: "")

                    }

                })





        }


    }


}