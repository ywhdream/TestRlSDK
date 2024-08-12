package com.yiyikeji.oauth.bean

/**
 * @Author ywh

 * @Date 2024/6/28

 * @类的作用:接收接口返回的数据
 * @param code 0表示请求成功，其他参考文档说明
 */
data class ApiResponse<T>(
    val code: Int,
    val message: String,
    val data: T?
)
