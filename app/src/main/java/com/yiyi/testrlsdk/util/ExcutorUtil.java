package com.yiyi.testrlsdk.util;

import java.util.concurrent.Executors;

public class ExcutorUtil {


    public static void startRunInNewThread(Runnable doSthRunnable) {
        Executors.newSingleThreadExecutor().execute(doSthRunnable);
    }

}
