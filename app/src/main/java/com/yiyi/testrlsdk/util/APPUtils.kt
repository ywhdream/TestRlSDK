package com.yiyi.testrlsdk.util

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.view.View
import android.view.inputmethod.InputMethodManager

object APPUtils {
    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    fun hideSoftKeyboard(context: Context, view: View?) {
        if (view == null) {
            return
        }
        val inputMethodManager = context.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isActive) {
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken, 0
            )
        }
    }


    /**
     * 判断手机是否安装某个应用
     * @param context
     * @param packageName  应用包名
     * @return   true：安装，false：未安装
     */
    fun isAppInstall(context: Context, packageName: String?): Boolean {
        val installed: Boolean = try {
            context.packageManager.getPackageInfo(packageName!!, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
            false
        }
        return installed
    }

}