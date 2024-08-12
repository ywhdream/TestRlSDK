package com.yiyikeji.oauth.interfaces

import com.yiyikeji.oauth.bean.UserInfoBean

interface AuthCallback {
    fun onAuthResult(code: Int, message: String?, userInfo: UserInfoBean?)
}