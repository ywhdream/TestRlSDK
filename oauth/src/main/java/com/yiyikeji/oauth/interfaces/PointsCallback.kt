package com.yiyikeji.oauth.interfaces


import com.yiyikeji.oauth.bean.OffsetPaymentBean
import com.yiyikeji.oauth.bean.PointsBean
import com.yiyikeji.oauth.bean.RewardItem

/**
 * @Author yiyikeji

 * @Date 2024/6/28

 * @类的作用:积分相关的接口
 */

interface PointsCallback {
    fun onPointsBalance(code: Int, message: String?, pointsBean: PointsBean?)//总积分
    fun onConsumptionPoints(
        code: Int,
        message: String?,
        offsetPaymentBean: OffsetPaymentBean?
    )//消费积分

    fun onPointsHistoryList(
        code: Int,
        message: String?,
        rewardItem: MutableList<RewardItem>?
    )//消费积分厉害记录
}