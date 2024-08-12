package com.yiyikeji.oauth.interfaces


import com.yiyikeji.oauth.bean.OffsetPaymentBean
import com.yiyikeji.oauth.bean.PointsBean
import com.yiyikeji.oauth.bean.RewardItem

interface PointsCallback {
    fun onPointsBalance(code: Int, message: String?, pointsBean: PointsBean?)
    fun onConsumptionPoints(code: Int, message: String?, offsetPaymentBean: OffsetPaymentBean?)
    fun onPointsHistoryList(code: Int, message: String?, rewardItem: MutableList<RewardItem>?)
}