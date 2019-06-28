package com.fly.myview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.fly.myview.R;


public class CommonDialog extends Dialog {

    private OnCancelListener cancelListener;

    public CommonDialog(Context context) {
        this(context, 0);

    }


    public CommonDialog(Context context, int themeResId) {
        super(context, themeResId);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除对话框的标题
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(0x00000000);
        getWindow().setBackgroundDrawable(gradientDrawable);//设置对话框边框背景,必须在代码中设置对话框背景，不然对话框背景是黑色的

        dimAmount(0.2f);
    }

    public CommonDialog contentView(@LayoutRes int layoutResID) {
        getWindow().setContentView(layoutResID);
        return this;
    }


    public CommonDialog contentView(@NonNull View view) {
        getWindow().setContentView(view);
        return this;
    }

    public CommonDialog contentView(@NonNull View view, @Nullable ViewGroup.LayoutParams params) {
        getWindow().setContentView(view, params);
        return this;
    }

    public CommonDialog layoutParams(@Nullable ViewGroup.LayoutParams params) {
        getWindow().setLayout(params.width, params.height);
        return this;
    }


    /**
     * 点击外面是否能dissmiss
     *
     * @param canceledOnTouchOutside
     * @return
     */
    public CommonDialog canceledOnTouchOutside(boolean canceledOnTouchOutside) {
        setCanceledOnTouchOutside(canceledOnTouchOutside);
        return this;
    }

    /**
     * 位置
     *
     * @param gravity
     * @return
     */
    public CommonDialog gravity(int gravity) {

        getWindow().setGravity(gravity);

        return this;

    }

    /**
     * 偏移
     *
     * @param x
     * @param y
     * @return
     */
    public CommonDialog offset(int x, int y) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.x = x;
        layoutParams.y = y;
        return this;
    }

    /**
     * 设置背景阴影,必须setContentView之后调用才生效
     *
     * @param dimAmount
     * @return
     */
    public CommonDialog dimAmount(float dimAmount) {

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = dimAmount;
        return this;
    }


    /**
     * 动画类型
     *
     * @param animInType 上中下
     * @return
     */
    public CommonDialog animType(CommonDialog.AnimInType animInType) {


        switch (animInType.getIntType()) {
            case 0:
                getWindow().setWindowAnimations(R.style.dialog_zoom);

                break;
            case 1:
                getWindow().setWindowAnimations(R.style.dialog_anim_left);

                break;
            case 2:
                getWindow().setWindowAnimations(R.style.dialog_anim_top);

                break;
            case 3:
                getWindow().setWindowAnimations(R.style.dialog_anim_right);

                break;
            case 4:
                getWindow().setWindowAnimations(R.style.dialog_anim_bottom);

                break;
        }
        return this;
    }

    public CommonDialog setCancelListener(OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        return this;
    }

    @Override
    public void cancel() {
        super.cancel();
        if (cancelListener != null) cancelListener.onCancel(this);
    }

    /*
        动画类型
         */
    public enum AnimInType {
        CENTER(0),
        LEFT(1),
        TOP(2),
        RIGHT(3),
        BOTTOM(4);

        AnimInType(int n) {
            intType = n;
        }

        final int intType;

        public int getIntType() {
            return intType;
        }
    }
}