package com.yiyikeji.oauth.interfaces

import com.yiyikeji.oauth.bean.UserInfoBean

/**
 * @Author yiyikeji

 * @Date 2024/6/28

 * @类的作用:用户信息的接口
 */

interface AuthCallback {
    fun onAuthResult(code: Int, message: String?, userInfo: UserInfoBean?)//用户信息
}