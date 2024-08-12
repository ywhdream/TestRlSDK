package com.yiyi.testrlsdk.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;



public class DeviceUtils {
    private static final String TAG = "DeviceUtils";


    public static void showSoftKeyboard(Context context, View view) {
        ((InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE)).showSoftInput(view,
                InputMethodManager.SHOW_IMPLICIT);
    }


    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    public static void hideSoftKeyboard(Context context, View view) {
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(
                    view.getWindowToken(), 0);
        }
    }


}
