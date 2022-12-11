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



        /**
         * 货币列表
         */
        const val CURRENCY_LIST = "ATSregister/moneyUnitList"

        /**
         * 根据国家来获取时区列表
         */
        const val GET_TIMEZONE_BY_COUNTRY = "ATSregister/getTimezoneByCountry"

        /**
         * 城市列表
         */
        const val GET_CITY_LIST = "ATSregister/getProvAndCityList"

        /**
         * 添加采集器
         */
        const val ADD_COLLECTOR = "ATSplant/addDatalog"

        /**
         * 添加电站
         */
        const val ADD_PLANT = "ATSplant/addPlant"

        /**
         * 修改电站
         */
        const val UPDATE_PLANT = "ATSplant/updatePlant"

        /**
         * 通过采集器序列号获取校验码
         */
        const val GET_CHECK_CODE = "ATSplant/getValidCodeBySN"

        /**
         * 获取电站列表
         */
        const val GET_PLANT_LIST = "ATSplant/getAllPlantList"

        /**
         * 删除电站
         */
        const val DELETE_PLANT = "ATSplant/delplant"

        /**
         * 获取电站详情
         */
        const val GET_PLANT_INFO = "ATSplant/getUserCenterEnertyDataByPlantid"

        /**
         * 我的设备列表
         */
        const val GET_DEVICE_LIST = "ATSDevice/getDevices"

        /**
         * 电站详情-PCS和HPS设备序列号
         */
        const val GET_PCS_AND_HPS_LIST = "ATSplant/getPCSAndHPSList"

        /**
         * HPS设备详情
         */
        const val GET_DEVICE_HPS_INFO = "ATSDevice/getHPSBySn"

        /**
         * HPS图表详情
         */
        const val GET_DEVICE_HPS_CHART_INFO = "ATSDevice/getHPSDataList"

        /**
         * PCS设备详情
         */
        const val GET_DEVICE_PCS_INFO = "ATSDevice/getPCSBySn"

        /**
         * PCS图表详情
         */
        const val GET_DEVICE_PCS_CHART_INFO = "ATSDevice/getPCSDataList"

        /**
         * PBD设备详情
         */
        const val GET_DEVICE_PBD_INFO = "ATSDevice/getPBDBySn"

        /**
         * PBD图表详情
         */
        const val GET_DEVICE_PBD_CHART_INFO = "ATSDevice/getPBDDataList"

        /**
         * 获取BMS、MBMS、BCU_BMS设备详情
         */
        const val GET_DEVICE_BMS_INFO = "ATSDevice/getBMSBySn"

        /**
         * 获取BMS、MBMS、BCU_BMS图表详情
         */
        const val GET_DEVICE_BMS_CHART_INFO = "ATSDevice/getBMSDataList"

        /**
         * HPS或PCS能源概况
         */
        const val GET_ENERGY_INFO = "ATSDevice/getEnergyOverview"

        /**
         * 电站详情获取HPS与PCS设备图表详情
         */
        const val GET_HPS_OR_PCS_CHART_INFO = "ATSDevice/getHpsOrPcsChartData"

        /**
         * 获取HPS系统运行图
         */
        const val GET_HPS_SYSTEM_OPERATION = "ATSDevice/getHPSRunChart"

        /**
         * 获取PCS系统运行图
         */
        const val GET_PCS_SYSTEM_OPERATION = "ATSDevice/getPCSRunChart"

        /**
         * 获取总览头部信息
         */
        const val GET_SYNOPSIS_TOTAL = "ATSOverview/getOverview"

        /**
         * 获取总览头部信息
         */
        const val GET_POWER_TRENDS_INFO = "ATSOverview/batTrend"

        /**
         * 首页总览-光伏产出与负载用电
         */
        const val GET_PV_AND_LOAD = "ATSOverview/getPVAndLoad"


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