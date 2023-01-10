package com.ttech.lib.service.http

import com.ttech.lib.LibApplication
import com.ttech.lib.R
import com.ttech.lib.util.GsonManager
import com.ttech.lib.util.ToastUtil
import org.json.JSONException
import org.json.JSONObject

abstract class HttpCallBackString : IHttpCallback {
    override fun onSuccess(response: String?) {
        //统一处理登录失效的问题
        try {
            val result = JSONObject(response).optString("result").toString()
            if (result == "10000") {
                //做自动登录处理
                LibApplication.instance().accountService().tokenExpired()
                val errorMsg = JSONObject(response).optString("msg").toString()
                ToastUtil.show(errorMsg)
                return
            }
        } catch (e: JSONException) {
        }

        success(response)

    }

    override fun onFailure(errorCode: String, error: String?) {
        onFailure(HttpErrorModel(errorCode, error))
    }


    abstract fun success(result: String?)

    open fun onFailure(errorModel: HttpErrorModel) {}


}