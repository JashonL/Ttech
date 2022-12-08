package com.tianji.ttech.model

import com.ttech.lib.service.account.User
import com.ttech.lib.service.appinfo.AppSystemDto

data class LoginModel(
    val user: User,
    val appSystemDto: AppSystemDto
)