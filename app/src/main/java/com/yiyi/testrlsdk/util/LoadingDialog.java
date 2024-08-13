package com.yiyi.testrlsdk.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.yiyi.testrlsdk.R;


public class LoadingDialog {

    private final float DEFAULT_ALPHA = 0.8f;
    private AlertDialog sLoadingDialog;
    private AnimationDrawable mAnimationDrawable;
    private View layoutView;
    private ImageView iv_hint_img;
    private TextView tv_hint_text;

    private Activity mActivity;


    public LoadingDialog(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 显示进行中的状态
     *
     * @param text 进行中的提示消息
     */
    public void showStateIng(String text) {
        initDialog(R.drawable.frame_loading_grey, text, false);
        handleAnimation(true);
    }



    public void onDestroy() {
        if (sLoadingDialog != null) {
            sLoadingDialog.dismiss();
        }
    }

    /**
     * 处理动画
     *
     * @param status true 开启动画，false 关闭动画
     */
    private void handleAnimation(boolean status) {
        mAnimationDrawable = (AnimationDrawable) iv_hint_img.getDrawable();
        if (mAnimationDrawable == null)
            throw new IllegalArgumentException("load animation not be null");
        if (status) {
            if (!mAnimationDrawable.isRunning()) {
                mAnimationDrawable.start();
            }
        } else {
            if (mAnimationDrawable.isRunning()) {
                mAnimationDrawable.stop();
            }
        }
    }


    private void initDialog(Integer imgRsId, String hintContent, boolean outsideCancel) {
        if (sLoadingDialog == null) {
            layoutView = LayoutInflater.from(mActivity).inflate(R.layout.view_hint_info1, null);
            iv_hint_img = (AppCompatImageView) layoutView.findViewById(R.id.iv_hint_img);
            tv_hint_text = (AppCompatTextView) layoutView.findViewById(R.id.tv_hint_text);
            sLoadingDialog = new AlertDialog.Builder(mActivity, R.style.loadingDialogStyle)
                    .setCancelable(outsideCancel)
                    .create();
            sLoadingDialog.setCanceledOnTouchOutside(outsideCancel);
            sLoadingDialog.setOnDismissListener(dialog -> setWindowAlpha(1.0f));
        }
        tv_hint_text.setText(hintContent);
        iv_hint_img.setImageResource(imgRsId);
        showDialog();
        sLoadingDialog.setContentView(layoutView);// 必须放在show方法后面
    }

    private void showDialog() {
        setWindowAlpha(DEFAULT_ALPHA);
        if (isValidActivity(mActivity)) {
            sLoadingDialog.show();
        }
    }

    /**
     * 判断一个界面是否还存在
     * 使用场景：比如  一个activity被销毁后，它的dialog还要执行某些操作，比如dismiss和show这样是不可以的
     * 因为 dialog是属于activity的
     *
     * @param c
     * @return
     */
    @TargetApi(17)
    private boolean isValidActivity(Activity c) {
        if (c == null) {
            return false;
        }

        if (c.isDestroyed() || c.isFinishing()) {
            return false;
        } else {
            return true;
        }
    }

    private void setWindowAlpha(float alpha) {
        WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
        params.alpha = alpha;
        params.verticalMargin = 100;
        mActivity.getWindow().setAttributes(params);
    }
}
