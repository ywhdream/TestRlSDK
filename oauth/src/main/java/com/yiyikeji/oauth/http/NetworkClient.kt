package com.yiyikeji.oauth.http

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import kotlin.coroutines.suspendCoroutine

/**
 * @Author yiyikeji

 * @Date 2024/6/28

 * @类的作用:网络请求基础类
 */

object NetworkClient {

    // 异步网络请求函数，支持动态请求类型和参数
    suspend fun <T> executeHttpRequest(
        accessToken: String? = "",
        url: String,
        method: String = "GET", // 默认请求方法
        body: Any? = null, // 请求体，可以是null
        parser: (String) -> T // 响应解析函数
    ): HttpURLConnection? = withContext(Dispatchers.IO) {
        try {
            val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
            connection.apply {
                requestMethod = method
                // 设置请求头
                setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                setRequestProperty("Open-Oauth-Token", accessToken)
                setRequestProperty("Lang", "zh-cn")

                body?.let {
                    if (method == "POST" || method == "PUT") {
                        doOutput = true
                        val gson = Gson() // 实例化 Gson
                        val jsonBody = gson.toJson(it) // 将对象转换为 JSON 字符串
                        val outputStream =
                            OutputStreamWriter(outputStream, Charset.forName("UTF-8"))
                        outputStream.write(jsonBody)
                        outputStream.flush()
                        outputStream.close()
                    }
                }

                // 发送请求并获取响应
                connect()

                // 检查响应码
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val responseReader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()
                    var line: String? = responseReader.readLine()
                    while (line != null) {
                        response.append(line).append('\n')
                        line = responseReader.readLine()
                    }
                    responseReader.close()

                    // 使用传入的 parser 函数处理响应字符串，并返回泛型类型 T
                    parser(response.toString())
                } else {
                    Log.d("ywh", "Response Code: $responseCode")
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("ywh", "executeHttpRequest: ${e.message}")
            null
        }
    }

//    suspend fun <T> executeHttpRequest(
//        token: String? = "",
//        url: String,
//        method: String = "GET",
//        body: Any? = null,
//        parser: (String) -> T
//    ): T? {
//        return try {
//            // 使用suspendCoroutine来桥接常规函数和协程
//            suspendCoroutine { continuation ->
////                CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
//                    connection.apply {
//                        requestMethod = method
//                        setRequestProperty("Content-Type", "application/json; charset=UTF-8")
//                        setRequestProperty("Open-Oauth-Token", token)
//                        setRequestProperty("Lang", "zh-cn")
//
//                        body?.let { it ->
//                            if (method == "POST" || method == "PUT") {
//                                doOutput = true
//                                val gson = Gson()
//                                val jsonBody = gson.toJson(it)
//                                OutputStreamWriter(outputStream, "UTF-8").use {
//                                    it.write(jsonBody)
//                                    it.flush()
//                                }
//                            }
//                        }
//
//                        connect()
//
//                        if (responseCode == HttpURLConnection.HTTP_OK) {
//                            val responseReader = BufferedReader(InputStreamReader(inputStream))
//                            val response = StringBuilder()
//                            var line: String? = responseReader.readLine()
//                            while (line != null) {
//                                response.append(line).append('\n')
//                                line = responseReader.readLine()
//                            }
//                            responseReader.close()
//                            // 通过continuation恢复协程并传递结果
//                            continuation.resumeWith(Result.success(parser(response.toString())))
//                        } else {
//                            Log.d("ywh", "Response Code: $responseCode")
//                            continuation.resumeWith(Result.failure(IllegalStateException("Response code: $responseCode")))
//                        }
//                    }
//                } catch (e: Exception) {
//                    Log.d("ywh", "executeHttpRequest: ${e.message}")
//                    continuation.resumeWith(Result.failure(e))
//                }
////                }
//            }
//        } catch (e: Exception) {
//            Log.d("ywh", "executeHttpRequest: ${e.message}")
//            null
//        }
//    }


    inline fun <reified T> parseJson(json: String): T {
        val gson = Gson()
        val type = object : TypeToken<T>() {}.type
        return gson.fromJson(json, type)
    }

}