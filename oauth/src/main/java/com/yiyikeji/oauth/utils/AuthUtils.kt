// AuthUtils.kt
package com.yiyikeji.oauth.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.yiyikeji.oauth.http.ApiServer
import com.yiyikeji.oauth.webview.WebViewActivity

object AuthUtils {


    // 检查应用是否安装
    private fun isAppInstall(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getApplicationInfo(packageName, 0)
            true
        } catch (e: Exception) {
            false
        }
    }

    // 启动授权流程的方法
    fun launchAuthorization(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>,
        appId: String,
        appSecret: String,
    ) {
        val intent = createAuthorizationIntent(
            activity,
            ApiServer.rlApk,
            ApiServer.buildOAuthUrl(appId = appId, appSecret = appSecret),
            appId
        )
        launcher.launch(intent)
    }

    // 创建授权 Intent
    private fun createAuthorizationIntent(
        activity: Activity,
        rlApk: String,
        webUrl: String,
        appId: String
    ): Intent {
        val intent: Intent
        val appInstall = isAppInstall(activity, "com.togl.rewardslink.stg")
        if (appInstall) {
            Log.d("ywh", "createAuthorizationIntent: ${Uri.parse(rlApk)}")

            intent = Intent(Intent.ACTION_VIEW, Uri.parse(rlApk))
            intent.putExtra("app_id", appId)
        } else {
            intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra(WebViewActivity.WEB_URL, webUrl)
        }
        return intent
    }


}