package com.yiyikeji.oauth.bean

/**
 * @Author ywh

 * @Date 2024/6/26

 * @类的作用:
 */
data class OffsetPaymentBean(
    val transaction_id: String,
    val user_id: String,
    val merchant_id: String,
    val price: String,
    val pay_amount: String,
    val offset_amount: String,
    val offset_points: String,
    val points: Double,
)
