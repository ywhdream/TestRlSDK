package com.yiyikeji.oauth.data

import com.yiyikeji.oauth.bean.ApiResponse
import com.yiyikeji.oauth.bean.UserInfoBean
import com.yiyikeji.oauth.http.ApiServer
import com.yiyikeji.oauth.http.NetworkClient
import com.yiyikeji.oauth.http.NetworkClient.parseJson
import com.yiyikeji.oauth.interfaces.AuthCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Author ywh

 * @Date 2024/7/11

 * @类的作用:
 */
object UserInfoData {
    /**
     * 获取RL用户信息api
     *
     * @param accessToken 授权成功后获取到的accessToken
     * @param callback 获取RL用户信息的回调
     */
    fun rlUserInfo(accessToken: String, callback: AuthCallback) {
        MainScope().launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                NetworkClient.executeHttpRequest(
                    accessToken,
                    url = ApiServer.oauthUserInfoUrl,
                    method = "GET",
                ) { responseData ->
                    val oauthCodeData = parseJson<ApiResponse<UserInfoBean>>(responseData)
                    callback.onAuthResult(
                        oauthCodeData.code,
                        oauthCodeData.message,
                        oauthCodeData.data
                    )

                }
            }

        }
    }

}