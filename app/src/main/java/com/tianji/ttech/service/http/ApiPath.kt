package com.tianji.ttech.service.http

object ApiPath {

    object Mine {
        /**
         * 登录
         */

        const val LOGIN = "v1/user/login"

        /**
         * 登出
         */
        const val LOGOUT = "v1/user/logOut"

        /**
         * 注册
         */
        const val REGISTER = "v1/user/register"


        /**
         * 设置-修改密码
         */
        const val MODIFY_PASSWORD = "/v1/user/modifyPassword"

        /**
         * 修改用户信息
         */
        const val MODIFYUSERINFO = "/v1/user/modifyUserInfo"

        /**
         * 找回密码
         */
        const val FINDPASSWORD = "/v1/user/findPassword"

        /**
         * 获取邮箱验证码
         */
        const val SENDEMAILCODE = "/v1/user/sendEmailCode"

        /**
         * 上传用户头像
         */
        const val UPLOADAVATAR = "/v1/user/uploadAvatar"

        /**
         * 忘记密码
         */
        const val RETRIEVEPASSWORD = "v1/user/retrievePassword"


    }

    object Plant {
        /**
         * 请求电站列表
         */
//        const val STATIONLIST="v1/station/stationList/1" 分页


        const val STATIONLIST = "v1/station/stationList"


        /**
         * 请求储能机状态
         */
        const val GETDATAOVERVIEW = "v1/manage/getDataOverview"


    }


    object Commom {
        /**
         * 服务-使用手册列表
         */
        const val GETCOUNTRYLIST = "v1/user/getCountryList"
    }


    object Service {
        /**
         * 服务-使用手册列表
         */
        const val GET_SERVICE_MANUAL = "ATService/getManual"

        /**
         * 服务-安装视频列表
         */
        const val GET_INSTALL_VIDEO = "ATService/getInstallVideo"
    }

}