//package com.yiyi.testrlsdk
//
//import android.annotation.SuppressLint
//import android.app.AlertDialog
//import android.content.Context
//import android.os.Bundle
//import android.view.WindowManager
//import android.view.inputmethod.InputMethodManager
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.alimuzaffar.lib.pin.PinEntryEditText
//import com.scwang.smart.refresh.footer.ClassicsFooter
//import com.scwang.smart.refresh.header.ClassicsHeader
//import com.yiyi.testrlsdk.adapter.RewardsAdapter
//import com.yiyi.testrlsdk.databinding.ActivityPointManagementBinding
//import com.yiyi.testrlsdk.util.APPUtils
//import com.yiyi.testrlsdk.util.LoadingDialog
//import com.yiyi.testrlsdk.util.StorageUtil
//import com.yiyikeji.oauth.bean.OffsetPaymentBean
//import com.yiyikeji.oauth.bean.PointsBean
//import com.yiyikeji.oauth.bean.RewardItem
//import com.yiyikeji.oauth.data.PointsData
//import com.yiyikeji.oauth.interfaces.PointsCallback
//import kotlinx.coroutines.MainScope
//import kotlinx.coroutines.launch
//
//
///**
// * @Author ywh
//
// * @Date 2024/6/25
//
// * @类的作用:
// */
//class PointManagementActivity : AppCompatActivity(), PointsCallback {
//    private lateinit var binding: ActivityPointManagementBinding
//
//
//    private lateinit var rewardsAdapter: RewardsAdapter
//    private var loadingDialog: LoadingDialog? = null
//    var accessToken = String()
//    var mMutableListData: MutableList<RewardItem> = mutableListOf()
//
//
//    @SuppressLint("NotifyDataSetChanged")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityPointManagementBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.title.tvTitle.text = "积分管理"
//        binding.title.ivBack.setOnClickListener {
//            finish()
//        }
//        rewardsAdapter = RewardsAdapter(mMutableListData)
//        binding.recyclerView.adapter = rewardsAdapter
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//
//
//        binding.tvPoints.setOnClickListener {
//            if (binding.etSearchPoints.text.toString().isNotEmpty()) {
//                showPaymentPasswordDialog()
//            } else {
//                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show()
//            }
//
//
//        }
//        accessToken = StorageUtil(this).get("access_token", "")
//
//        PointsData.pointsBalance(accessToken = accessToken, callback = this)
//
//        initSmartRefresh()
//
//        binding.refreshLayout.autoRefresh()
//
//    }
//
//    private fun initSmartRefresh() {
//        binding.refreshLayout.setRefreshHeader(ClassicsHeader(this))
//        binding.refreshLayout.setRefreshFooter(ClassicsFooter(this))
//        binding.refreshLayout.setEnableLoadMore(false)
//        binding.refreshLayout.setOnRefreshListener { refreshlayout ->
//            refreshlayout.finishRefresh(1000) //传入false表示刷新失败
//            PointsData.pointsHistoryList(
//                accessToken = accessToken,
//                callback = this
//            )
//
//        }
//        binding.refreshLayout.setOnLoadMoreListener { refreshlayout ->
//            refreshlayout.finishLoadMore(1000) //传入false表示加载失败
//            PointsData.pointsHistoryList(
//                accessToken = accessToken,
//                callback = this
//            )
//
//        }
//    }
//
//    private fun showPaymentPasswordDialog() {
//        val builder = AlertDialog.Builder(this)
//        val inflater = this@PointManagementActivity.layoutInflater
//        val dialogView = inflater.inflate(R.layout.dialog_payment, null)
//        val passwordEditText = dialogView.findViewById<PinEntryEditText>(R.id.txtPinEnter)
//
//        // 确保EditText可以获取焦点
//        passwordEditText.isFocusable = true
//        passwordEditText.isFocusableInTouchMode = true
//
//        builder.setView(dialogView)
//        val dialog = builder.create()
//        dialog.show()
//
//        passwordEditText.setOnPinEnteredListener {
//            binding.etSearchPoints.clearFocus()
//            PointsData.consumptionPoints(
//                accessToken = accessToken,
//                amount = binding.etSearchPoints.text.toString().toDouble(),
//                callback = this
//            )
//            APPUtils.hideSoftKeyboard(this, binding.etSearchPoints)
//            showLoadingDialog()
//            dialog.dismiss()
//
//        }
//
//        // 强制窗口获得当前的输入焦点
//        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
//
//        // 请求密码EditText的焦点
//        passwordEditText.requestFocus()
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.showSoftInput(passwordEditText, InputMethodManager.SHOW_IMPLICIT)
//
//
//    }
//
//    private fun showLoadingDialog() {
//        loadingDialog = LoadingDialog(this).apply {
//            showStateIng("支付中...")
//        }
//    }
//
//    private fun dismissLoadingDialog() {
//        loadingDialog?.onDestroy()
//
//
//    }
//
//    @SuppressLint("SetTextI18n")
//    override fun onPointsBalance(code: Int, message: String?, pointsBean: PointsBean?) {
//        MainScope().launch {
//            binding.tvPointsBalance.text = "${pointsBean?.points} pt"
//        }
//    }
//
//    @SuppressLint("SetTextI18n")
//    override fun onConsumptionPoints(
//        code: Int,
//        message: String?,
//        offsetPaymentBean: OffsetPaymentBean?
//    ) {
//        MainScope().launch {
//            binding.tvPointsBalance.text = "${offsetPaymentBean?.points} pt"
//            dismissLoadingDialog()
//            binding.refreshLayout.autoRefresh()
//        }
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    override fun onPointsHistoryList(
//        code: Int,
//        message: String?,
//        rewardItem: MutableList<RewardItem>?
//    ) {
//
//        MainScope().launch {
//
//            if (rewardItem != null) {
//                mMutableListData.addAll(rewardItem)
//            }
//            rewardsAdapter.notifyDataSetChanged()
//
//
//        }
//
//    }
//
//}