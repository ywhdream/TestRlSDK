package com.yiyikeji.oauth.data


import com.yiyikeji.oauth.bean.AmountReq
import com.yiyikeji.oauth.bean.ApiResponse
import com.yiyikeji.oauth.bean.OffsetPaymentBean
import com.yiyikeji.oauth.bean.PointsBean
import com.yiyikeji.oauth.bean.RewardItem
import com.yiyikeji.oauth.http.ApiServer
import com.yiyikeji.oauth.http.NetworkClient
import com.yiyikeji.oauth.http.NetworkClient.parseJson
import com.yiyikeji.oauth.interfaces.PointsCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Author ywh

 * @Date 2024/7/11

 * @类的作用:
 */
object PointsData {
    /**
     * 获取消费积分api
     *
     * @param accessToken 授权成功后获取到的accessToken
     * @param amount 消费的积分
     * @param callback 获取RL用户信息的回调
     */
    fun consumptionPoints(accessToken: String, amount: Double, callback: PointsCallback) {
        MainScope().launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                NetworkClient.executeHttpRequest(
                    accessToken,
                    url = ApiServer.offsetPaymentUrl,
                    method = "POST",
                    body = AmountReq(amount)
                ) { responseData ->
                    val mPaymentDataBean = parseJson<ApiResponse<OffsetPaymentBean>>(responseData)
                    callback.onConsumptionPoints(
                        mPaymentDataBean.code,
                        mPaymentDataBean.message,
                        mPaymentDataBean.data
                    )

                }
            }

        }

    }

    /**
     * 积分历史账单api
     *
     * @param accessToken 授权成功后获取到的accessToken
     * @param limit 返回的数量，默认10条
     * @param offset 返回的页码，默认0
     * @param type 0表示全部类型，1表示收入类型，-1表示消费类型
     * @param callback 积分历史账单的回调
     */
    fun pointsHistoryList(
        accessToken: String,
        limit: Int = 15,
        offset: Int = 0,
        type: Int = 0,
        callback: PointsCallback
    ) {

        val queryParams = with(StringBuilder()) {
            append("?limit=$limit")
            append("&offset=$offset")
            append("&type=$type")
            toString()
        }
        val requestUrl = "${ApiServer.pointsHistoryUrl}$queryParams"
        MainScope().launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                NetworkClient.executeHttpRequest(
                    accessToken,
                    url = requestUrl,
                    method = "GET",
                ) { responseData ->
                    val mRewardItem = parseJson<ApiResponse<MutableList<RewardItem>>>(responseData)
                    callback.onPointsHistoryList(
                        mRewardItem.code,
                        mRewardItem.message,
                        mRewardItem.data
                    )

                }
            }

        }


    }

    /**
     * 积分余额api
     *
     * @param accessToken 授权成功后获取到的accessToken
     * @param callback 积分余额的回调
     */
    fun pointsBalance(accessToken: String, callback: PointsCallback) {
        MainScope().launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                NetworkClient.executeHttpRequest(
                    accessToken,
                    url = ApiServer.pointsBalanceUrl,
                    method = "GET",
                ) { responseData ->
                    val pointsBean = parseJson<ApiResponse<PointsBean>>(responseData)
                    callback.onPointsBalance(pointsBean.code, pointsBean.message, pointsBean.data)

                }
            }

        }
    }


}