package com.yiyikeji.oauth.webview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.yiyikeji.oauth.databinding.ActivityWebviewBinding


/**
 * @Author ywh

 * @Date 2024/7/20

 * @类的作用:
 */
class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebviewBinding
    private lateinit var webUrl: String
    companion object {
        const val WEB_URL = "web_url"   //H5Url

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webUrl = intent.getStringExtra(WEB_URL).toString()
        WebView.setWebContentsDebuggingEnabled(true)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.webView.settings.apply {
            domStorageEnabled = true
            userAgentString = "$userAgentString OAuth"
            javaScriptEnabled = true //允许使用js
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            defaultTextEncodingName = "UTF-8"
            binding.webView.addJavascriptInterface(this@WebViewActivity, "userContentControllerInterface")
            cacheMode = WebSettings.LOAD_NO_CACHE //不使用缓存，只从网络获取数据.
            textZoom = 100 //文字大小
            //支持屏幕缩放
            setSupportZoom(true)
            builtInZoomControls = true
            useWideViewPort = true
            loadWithOverviewMode = true
        }
        binding.webView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        binding.webView.loadUrl(webUrl) //加载url
    }


    @JavascriptInterface
    fun getAccessToken(accessToken: String) {
        setResultData(accessToken = accessToken)

    }

    @JavascriptInterface
    fun goBack() {
        finish()
    }


    private fun setResultData(accessToken: String = "") {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra("resultKey", accessToken) // 可以添加返回给APP1的数据
        })
        finish()
    }

}