package com.yiyikeji.oauth.http

import java.net.URLEncoder

/**
 * @Author yiyikeji

 * @Date 2024/6/28

 * @类的作用:网络请求API
 */
object ApiServer {
    const val baseUrl = "http://1.12.181.89:5125/openapi/"
    const val oauthUserInfoUrl = "${baseUrl}sns/userinfo"//获取用户信息
    const val pointsBalanceUrl = "${baseUrl}points/pointsBalance"//获取积分余额
    const val offsetPaymentUrl = "${baseUrl}points/offsetPayment"//积分支付
    const val pointsHistoryUrl = "${baseUrl}points/pointsHistory"//消费积分列表

    var rlApk = "rl-oauth://rewardslink/customer-oauth" //跳转到奖励您
    var webUrl = "http://oauthdemo.yiarttech.com/login.html"


    /**
     * 构建OAuth URL
     * @param appId 应用ID
     * @param appSecret 应用密钥
     * @param redirectUri 重定向URI
     * @param state 状态值
     * @param scope 授权范围
     * @param responseType 响应类型
     * @param lang 语言
     * @param appType 应用类型
     * @return 完整的OAuth URL
     */
    fun buildOAuthUrl(
        appId: String,
        appSecret: String,
        redirectUri: String = "",
        state: String = "123",
        scope: String = "snsapi_userinfo",
        responseType: String = "code",
        lang: String = "zh",
        appType: String = "android"
    ): String {
        val encodedAppId = URLEncoder.encode(appId, "UTF-8")
        val encodedAppSecret = URLEncoder.encode(appSecret, "UTF-8")
        val encodedRedirectUri = URLEncoder.encode(redirectUri, "UTF-8")

        return "$webUrl?" +
                "app_id=$encodedAppId&" +
                "app_secret=$encodedAppSecret&" +
                "redirect_uri=$encodedRedirectUri&" +
                "state=$state&" +
                "scope=$scope&" +
                "response_type=$responseType&" +
                "lang=$lang&" +
                "app_type=$appType"
    }
}