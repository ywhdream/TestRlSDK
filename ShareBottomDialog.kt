package com.togl.rewardslink.se.modules.dynamic.detail.pop

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.togl.baseproject.share.Share
import com.togl.rewardslink.R
import com.togl.rewardslink.se.base.RewardsLinkApplication
import com.togl.rewardslink.se.data.beans.DynamicDetailBeanV2
import com.togl.rewardslink.se.utils.TeenModeUtil

/**
 * @Author ywh

 * @Date 2023/11/1

 * @类的作用:
 */
class ShareBottomDialog(private val dynamicDetailBean: DynamicDetailBeanV2) :
    BottomSheetDialogFragment() {
    private val mShareAdapter: ShareAdapter by lazy { ShareAdapter(mutableListOf()) }
    private var listenerShare: OnShareListener? = null

    val root: View by lazy {
        LayoutInflater.from(activity).inflate(R.layout.dialog_sharebottom, null)
    }

    interface OnShareListener {
        fun onShareClick(shareOrForwardBean: ShareOrForwardBean)
    }

    fun setShareListener(listener: OnShareListener) {
        this.listenerShare = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (activity == null) return super.onCreateDialog(savedInstanceState)
        val dialog = Dialog(
            requireActivity(), R.style.BottomSheetStyle
        ).apply {
            setCancelable(false)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(root)
            setCanceledOnTouchOutside(true)
            //设置dialog在底部显示
            window?.setGravity(Gravity.BOTTOM)
            //设置dialog宽度占满屏幕，高度包含内容
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            //因为我的dialog背景图片是圆弧型，不设置背景透明的话圆弧处显示黑色
            window?.setBackgroundDrawableResource(android.R.color.transparent)

        }
        initView()
        return dialog
    }

    private fun initView() {

        val recyclerView = root.findViewById<RecyclerView>(R.id.share_Recyclerview)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = mShareAdapter
        mShareAdapter.setList(setShareBean())
        mShareAdapter.addItemClickListener { _, shareOrForwardBean ->
            listenerShare?.onShareClick(shareOrForwardBean)
            dismiss()
        }


    }

    private fun setShareBean(): MutableList<ShareOrForwardBean> {
        val shareBean: MutableList<ShareOrForwardBean> = mutableListOf()

        if (RewardsLinkApplication.getmCurrentLoginAuth() != null && dynamicDetailBean.user_id == RewardsLinkApplication.getMyUserIdWithdefault()) {
            if (!TeenModeUtil.isInTeenMode()) {
                shareBean.add(
                    ShareOrForwardBean(
                        Share.EDIT,
                        getString(R.string.edit), R.drawable.icon_feed_edit
                    )
                )
            }

            shareBean.add(
                ShareOrForwardBean(
                    Share.PIN,
                    getString(if (!dynamicDetailBean.isIs_pinned)
                        R.string.rw_detail_share_pin else R.string.rw_detail_share_unpin),
                    if (!dynamicDetailBean.isIs_pinned) R.drawable.icon_feed_pin else R.drawable.icon_feed_up_pin

                )
            )

            shareBean.add(
                ShareOrForwardBean(
                    Share.COLLECT,
                    getString(if (dynamicDetailBean.has_collect) R.string.unsave else R.string.save),
                    R.drawable.icon_feed_save
                )
            )
            shareBean.add(
                ShareOrForwardBean(
                    Share.COMMENT_PERMISSION,
                    getString(if (dynamicDetailBean.isBooleanCommentDisabled) R.string.rw_enable_comment else R.string.rw_disable_comment),
                    if (dynamicDetailBean.isBooleanCommentDisabled) R.drawable.icon_feed_on_comment else R.drawable.icon_feed_off_comment

                )
            )
            shareBean.add(
                ShareOrForwardBean(
                    Share.DELETE,
                    getString(R.string.delete), R.drawable.icon_feed_delete
                )
            )
        } else {
            shareBean.add(
                ShareOrForwardBean(
                    Share.COLLECT,
                    getString(if (dynamicDetailBean.has_collect) R.string.unsave else R.string.save),

                    R.drawable.icon_feed_save
                )
            )
            shareBean.add(
                ShareOrForwardBean(
                    Share.REPORT,
                    getString(R.string.report), R.drawable.icon_feed_report
                )
            )
        }



        return shareBean
    }
}