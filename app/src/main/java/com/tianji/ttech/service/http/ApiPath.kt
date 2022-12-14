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

        const val STATIONLIST = "v1/station/stationList"


        /**
         * 请求储能机状态
         */
        const val GETDATAOVERVIEW = "v1/manage/getDataOverview"


        /**
         * 货币列表
         */
        const val CURRENCY_LIST = "v1/station/getIncomeUnit"

        /**
         * 根据国家来获取时区列表
         */
        const val GET_TIMEZONE_BY_COUNTRY = "ATSregister/getTimezoneByCountry"

        /**
         * 城市列表
         */
        const val GET_CITY_LIST = "v1/user/getCountryCityList"


        /**
         * 添加电站
         */
        const val ADD_PLANT = "v1/station/addStation"

        /**
         * 修改电站
         */
        const val UPDATE_PLANT = "ATSplant/updatePlant"


        /**
         * 删除电站
         */
        const val DELETE_PLANT = "v1/station/deleteStation"

        /**
         * 获取电站详情
         */
        const val GET_PLANT_INFO = "ATSplant/getUserCenterEnertyDataByPlantid"

        /**
         * 根据日期类型请求图表
         */
        const val GET_INVERTER_DATA_DAY = "v1/manage/getInverterDataDay"
        const val GET_INVERTER_DATA_MONTH = "v1/manage/getInverterDataMonth"
        const val GET_INVERTER_DATAYEAR = "v1/manage/getInverterDataYear"
        const val GET_INVERTER_DATATOTAL = "v1/manage/getInverterDataTotal"


        /**
         * 根据日期类型请求收益
         */
        const val GET_IMPACT_DAY = "v1/manage/getImpactDay"
        const val GET_IMPACT_MONTH = "v1/manage/getImpactMonth"
        const val GET_IMPACT_YEAR = "v1/manage/getImpactYear"
        const val GET_IMPACT_TOTAL = "v1/manage/getImpactTotal"


        /**
         * 根据日期获取
         */
        const val GET_ARRAY_DATADAY = "v1/manage/getArrayDataDay"
        const val GET_ARRAY_DATAMONTH = "v1/manage/getArrayDataMonth"
        const val GET_ARRAY_DATAYEAR = "v1/manage/getArrayDataYear"
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


    object Device {

        /**
         * 设备列表
         */
        const val GETDEVICELIST = "v1/station/deviceList"

        const val SETHMIINFO = "v1/manager/setHmiInfo"

        const val SETMGRNINFO = "v1/manager/setMgrnInfo"

        const val ELECSETTING = "v1/manager/elecSetting"

        const val GETPCSSETINFO = "v1/manager/getPcsSetInfo"

        const val GETMGRNSETINFO = "v1/manager/getMgrnSetInfo"

        const val GETDEVICEDETAILS = "/v1/plant/getDeviceDetails"

        const val GETINVERTERDATA = "/v1/station/getInverterData"

    }



    object OperatorLog{
        const val OPERATIONLIST = "v1/station/operationList"
    }


    object Dataloger {

        /**
         * 添加采集器
         */
        const val ADDDATALOG = "v1/station/addDatalog"

    }

}