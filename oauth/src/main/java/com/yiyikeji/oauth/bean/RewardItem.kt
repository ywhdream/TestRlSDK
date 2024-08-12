package com.yiyikeji.oauth.bean


data class RewardItem(
    val transaction_id: String,
    val user_id: Int,
    val points: String,
    val status: Int,
    val type: Int,
    val created_at: String,
    val status_label: String,
    val type_label: String
)
