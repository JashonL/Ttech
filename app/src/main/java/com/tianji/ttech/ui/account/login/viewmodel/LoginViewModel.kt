package com.tianji.ttech.ui.account.login.viewmodel

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

/**
 * 登录
 */
class LoginViewModel : BaseViewModel() {

    val loginLiveData = MutableLiveData<Pair<User?, String?>>()

    /**
     * 登录
     */
    fun login(userName: String, password: String) {
        viewModelScope.launch {
            val params = hashMapOf<String, String>().apply {
                put("email", userName)
                put("password", MD5Util.md5(password) ?: "")
            }
            apiService().postForm(ApiPath.Mine.LOGIN, params, object :
                HttpCallback<HttpResult<User>>() {
                override fun success(result: HttpResult<User>) {
                    val user = result.obj
                    if (result.isBusinessSuccess() && user != null) {
                        loginLiveData.value = Pair(user, null)
                    } else {
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