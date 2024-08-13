//package com.yiyi.testrlsdk
//
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import androidx.activity.result.ActivityResult
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AppCompatActivity
//import com.yiyi.testrlsdk.databinding.ActivityMainBinding
//import com.yiyi.testrlsdk.util.StorageUtil
//import com.yiyikeji.oauth.bean.UserInfoBean
//import com.yiyikeji.oauth.data.UserInfoData
//import com.yiyikeji.oauth.interfaces.AuthCallback
//import com.yiyikeji.oauth.utils.AuthUtils
//
//
//class MainActivity : AppCompatActivity(), AuthCallback {
//    private lateinit var binding: ActivityMainBinding
//
//    private lateinit var someActivityResultLauncher: ActivityResultLauncher<Intent>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//        binding.title.ivBack.visibility = View.INVISIBLE
//        binding.title.tvTitle.text = "首页"
//        binding.title.tvRightTitle.text = "积分管理"
//        binding.title.tvRightTitle.setOnClickListener {
//            val intent = Intent()
//            intent.setClass(this, PointManagementActivity::class.java)
//            startActivity(intent)
//        }
//
//        if (StorageUtil(this).get("access_token", "").isNotEmpty()) {
//            binding.tvAuthorizationResult.text = "授权结果：授权成功"
//        } else {
//            binding.tvAuthorizationResult.text = "授权结果：用户未授权"
//        }
//        // 注册 ActivityResultLauncher
//        someActivityResultLauncher = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult(),
//            ::handleActivityResult
//        )
//
//
//        binding.accredit.setOnClickListener {
//            AuthUtils.launchAuthorization(
//                activity = this,
//                launcher = someActivityResultLauncher,
//                appId = "309711722824829",
//                appSecret = "66b0387d6da35"
//            )
//
//
//        }
//
//        binding.results.setOnClickListener {
//            val userInfo = StorageUtil(this).get("user_info", "")
//            Log.d("ywh", "onCreate: $userInfo")
//            if (userInfo.isNotEmpty()) {
//                binding.tvResults.visibility = View.VISIBLE
//                binding.tvResults.text = userInfo
//            }
//
//        }
//
//    }
//
//    // 处理 Activity 结果的回调
//    private fun handleActivityResult(result: ActivityResult) {
//        if (result.resultCode == Activity.RESULT_OK) {
//            val resultData = result.data?.getStringExtra("resultKey")
//            binding.tvAuthorizationResult.text = "授权结果：授权成功"
//            Log.d("ywh", "handleActivityResult: ${resultData.toString()}")
//            StorageUtil(this).save("access_token", resultData.toString())
//            UserInfoData.rlUserInfo(accessToken = resultData.toString(), callback = this)
//            // 使用返回的数据
//
//
//        }
//    }
//
//    override fun onAuthResult(code: Int, message: String?, userInfo: UserInfoBean?) {
//        StorageUtil(this).save("user_info", userInfo.toString())
//        Log.d("ywh", "onAuthResult: $code")
//        if (code == 0) {
//            val intent = Intent()
//            intent.setClass(this, PointManagementActivity::class.java)
//            startActivity(intent)
//        }
//
//
//    }
//
//
//}
//
//
