package com.fly.myview.prompt;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.shangyi.android.msgpromptlibrary.R;
import com.shangyi.android.msgpromptlibrary.interf.OnActionClickListener;

/**
 * 包    名 : com.shangyi.android.msgpromptlibrary.prompt
 * 作    者 : FLY
 * 创建时间 : 2019/6/20
 * 描述: 提示对话帮助
 */
public class PromptDialog {

    private ViewGroup decorView;

    private PromptView promptView;

    private Builder builder;

    private AnimationSet inSheetAnim;
    private AlphaAnimation outSheetAnim;

    private AnimationSet inDefaultAnim;
    private AnimationSet outDefaultAnim;
    private ValueAnimator dissmissAnim;

    private InputMethodManager inputmanger;

    /**
     * 动画是否取消
     */
    private boolean dissmissAnimCancle;

    /**
     * 动画播放时间
     */
    public long viewAnimDuration = 300;

    /**
     * 退出动画
     */
    private Animation outAnim;

    /**
     * 进入动画
     */
    private Animation inAnim;

    /***
     * 是否正在执行动画
     */
    private boolean outAnimRunning;

    /**
     * 是否已经展示
     */
    private boolean isShowing;

    /**
     * 广告监听
     */
    private OnActionClickListener adListener;

    /**
     * 关闭监听
     */
    private OnActionClickListener dissmissListener;

    public PromptDialog(Activity context, Builder builder) {
        this.builder = builder;
        this.promptView = new PromptView(context, builder, this);
        this.inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        this.decorView = (ViewGroup) context.getWindow().getDecorView().findViewById(android.R.id.content);
        initAnim(context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels);
    }

    private void initAnim(int widthPixels, int heightPixels) {
        inDefaultAnim = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(2, 1f, 2,
                1f, widthPixels * 0.5f, heightPixels * 0.45f);
        inDefaultAnim.addAnimation(scaleAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        inDefaultAnim.addAnimation(alphaAnimation);
        inDefaultAnim.setDuration(viewAnimDuration);
        inDefaultAnim.setFillAfter(false);
        inDefaultAnim.setInterpolator(new DecelerateInterpolator());

        outDefaultAnim = new AnimationSet(true);
        scaleAnimation = new ScaleAnimation(1, 2, 1,
                2, widthPixels * 0.5f, heightPixels * 0.45f);
        alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(200);
        outDefaultAnim.addAnimation(scaleAnimation);
        outDefaultAnim.addAnimation(alphaAnimation);
        outDefaultAnim.setDuration(viewAnimDuration);
        outDefaultAnim.setFillAfter(false);
        outDefaultAnim.setInterpolator(new AccelerateInterpolator());

        alphaAnimation = new AlphaAnimation(0, 1);
        scaleAnimation = new ScaleAnimation(1, 1, 1,
                1, widthPixels * 0.5f, heightPixels * 0.5f);
        inSheetAnim = new AnimationSet(true);
        inSheetAnim.addAnimation(alphaAnimation);
        inSheetAnim.addAnimation(scaleAnimation);
        inSheetAnim.setDuration(viewAnimDuration);
        inSheetAnim.setFillAfter(false);


        outSheetAnim = new AlphaAnimation(1, 0);
        outSheetAnim.setDuration(viewAnimDuration);
        outSheetAnim.setFillAfter(false);
    }

    public void showSomthing(boolean withAnim) {
        if (inAnim != null) inAnim = inDefaultAnim;
        if (outAnim != null) outAnim = outDefaultAnim;
        closeInput();
        checkLoadView(withAnim);
        promptView.showLoading();
        dissmissAni(false);
    }

    public void showAlert(boolean withAnim, PromptButton... button) {
        if (button.length > 2) {
            if (inAnim != null) inAnim = inSheetAnim;
            if (outAnim != null) outAnim = outSheetAnim;
        } else {
            if (inAnim != null) inAnim = inDefaultAnim;
            if (outAnim != null) outAnim = outDefaultAnim;
        }
        closeInput();
        checkLoadView(withAnim);
        promptView.showSomthingAlert(button);
        dissmissAni(true);
    }

    public ImageView showAd(boolean withAnim, OnActionClickListener listener) {
        this.adListener = listener;
        if (inAnim != null) inAnim = inDefaultAnim;
        if (outAnim != null) outAnim = outDefaultAnim;
        closeInput();
        checkLoadView(withAnim);
        promptView.showAd();
        dissmissAni(true);
        return promptView;
    }

    private void dissmissAni(boolean isCancle) {
        if (dissmissAnim == null) {
            dissmissAnim = ValueAnimator.ofInt(0, 1);
            dissmissAnim.setDuration(builder.stayDuration);
            dissmissAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!dissmissAnimCancle) {
                        dismiss();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else if (dissmissAnim.isRunning()) {
            dissmissAnimCancle = true;
            dissmissAnim.end();

        }
        if (!isCancle) {
            dissmissAnim.start();
            dissmissAnimCancle = false;
        }
    }

    /**
     * 关闭输入
     */
    protected void closeInput() {
        if (decorView != null) {
            inputmanger.hideSoftInputFromWindow(decorView.getWindowToken(), 0);
        }
    }

    private void checkLoadView(boolean withAnim) {
        if (!isShowing) {
            decorView.addView(promptView);
            isShowing = true;
            if (builder.withAnim && inAnim != null && withAnim)
                promptView.startAnimation(inAnim);
        }
    }

    /**
     * 设置进入 进出动画持续的事件默认300毫秒
     *
     * @param viewAnimDuration 毫秒
     */
    public PromptDialog setViewAnimDuration(long viewAnimDuration) {
        this.viewAnimDuration = viewAnimDuration;
        return this;
    }

    public long getViewAnimDuration() {
        return viewAnimDuration;
    }

    public PromptDialog setOutAnim(Animation outAnim) {
        this.outAnim = outAnim;
        return this;
    }

    public PromptDialog setInAnim(Animation inAnim) {
        this.inAnim = inAnim;
        return this;
    }

    public PromptDialog setDissmissListener(OnActionClickListener dissmissListener) {
        this.dissmissListener = dissmissListener;
        return this;
    }

    /**
     * 关闭对话
     */
    public void dismiss() {
        if (isShowing && !outAnimRunning) {
            if (builder.withAnim && outAnim != null) {
                if (promptView.getCurrentType() == PromptView.PROMPT_LOADING) {
                    outAnim.setStartOffset(builder.loadingDuration);
                } else {
                    outAnim.setStartOffset(0);
                }
                promptView.dismissSheet();
                promptView.startAnimation(outAnim);
                outAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        outAnimRunning = true;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        decorView.removeView(promptView);
                        outAnimRunning = false;
                        isShowing = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                promptView.dismissSheet();
                dismissImmediately();
            }
        }
    }

    /**
     * 立刻关闭窗口
     */
    public void dismissImmediately() {
        if (isShowing && !outAnimRunning) {
            decorView.removeView(promptView);
            isShowing = false;
        }
    }

    /**
     * 处理返回键，需要用户自行调用
     *
     * @return
     */
    public boolean onBackPressed() {
        if (isShowing && promptView.getCurrentType() == PromptView.PROMPT_LOADING) {
            return false;
        }
        if (isShowing && (promptView.getCurrentType() == PromptView.PROMPT_SHEET
                || promptView.getCurrentType() == PromptView.PROMPT_DIALOG
                || promptView.getCurrentType() == PromptView.PROMPT_AD)) {
            dismiss();
            return false;
        } else {
            return true;
        }
    }

    /**
     * 广告监听
     */
    public void onAdClick() {
        if (adListener != null) {
            adListener.onClick();
        }
    }

    /**
     * 界面关闭
     */
    public void onDetach() {
        isShowing = false;
        if (dissmissListener != null) {
            dissmissListener.onClick();
        }
    }

    public static class Builder {
        public Activity context;

        /**
         * 全屏背景颜色
         */
        int backColor = Color.BLACK;

        /**
         * 全屏背景透明度
         */
        int backAlpha = 90;

        int textColor = Color.WHITE;

        float textSize = 14;

        /**
         * 内边距
         */
        float padding = 15;

        /**
         * 圆角
         */
        float round = 8;

        /**
         * 展示框背景
         */
        int roundColor = Color.BLACK;

        /**
         * 展示框背景透明度
         */
        int roundAlpha = 120;

        /**
         * 是否传递操作，默认：不传递，可点击
         */
        boolean touchAble = false;

        /**
         * 是否显示动画
         */
        boolean withAnim = true;

        /**
         * 开始动画时间
         */
        long stayDuration = 1000;

        /**
         * 空白是否可关闭
         */
        boolean cancleAble;

        int icon;

        String text;

        /**
         * 动画开始时间，及展示时间
         */
        long loadingDuration;

        /**
         * 表单点击后透明度
         */
        int sheetPressAlph = 15;

        /**
         * 表单行高
         */
        int sheetCellHeight = 48;

        /**
         * 表单外边距
         */
        int sheetCellPad = 13;

        /**
         * 广告关闭图标
         */
        int closeIcon = R.drawable.prompt_ad_close_ic;

        /**
         * 广告圆角
         */
        int iconRadius = 50;


        /**
         * 对话宽度
         */
        int dialogidth = 120;


        /**
         * 对话按钮高度
         */
        int btnHeight = 45;

        /**
         * 提示是否自动关闭
         */
        boolean isClose = true;

        public Builder setBackColor(int backColor) {
            this.backColor = backColor;
            return this;
        }

        public Builder setBackAlpha(int backAlpha) {
            this.backAlpha = backAlpha;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setTextSize(float textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder setPadding(float padding) {
            this.padding = padding;
            return this;
        }

        public Builder setRound(float round) {
            this.round = round;
            return this;
        }

        public Builder setRoundColor(int roundColor) {
            this.roundColor = roundColor;
            return this;
        }

        public Builder setRoundAlpha(int roundAlpha) {
            this.roundAlpha = roundAlpha;
            return this;
        }

        public Builder setTouchAble(boolean touchAble) {
            this.touchAble = touchAble;
            return this;
        }

        public Builder setWithAnim(boolean withAnim) {
            this.withAnim = withAnim;
            return this;
        }

        public Builder setStayDuration(long stayDuration) {
            this.stayDuration = stayDuration;
            return this;
        }

        public Builder setCancleAble(boolean cancleAble) {
            this.cancleAble = cancleAble;
            return this;
        }

        public Builder setIcon(int icon) {
            this.icon = icon;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setLoadingDuration(long loadingDuration) {
            this.loadingDuration = loadingDuration;
            return this;
        }

        public Builder setSheetPressAlph(int sheetPressAlph) {
            this.sheetPressAlph = sheetPressAlph;
            return this;
        }

        public Builder setSheetCellHeight(int sheetCellHeight) {
            this.sheetCellHeight = sheetCellHeight;
            return this;
        }

        public Builder setSheetCellPad(int sheetCellPad) {
            this.sheetCellPad = sheetCellPad;
            return this;
        }

        public Builder setCloseIcon(int closeIcon) {
            this.closeIcon = closeIcon;
            return this;
        }

        public Builder setIconRadius(int iconRadius) {
            this.iconRadius = iconRadius;
            return this;
        }

        public Builder setDialogidth(int dialogidth) {
            this.dialogidth = dialogidth;
            return this;
        }

        public Builder setBtnHeight(int btnHeight) {
            this.btnHeight = btnHeight;
            return this;
        }

        public Builder setClose(boolean close) {
            this.isClose = close;
            return this;
        }

        public Builder(Activity activity) {
            context = activity;
        }

        public PromptDialog create() {
            PromptDialog cookie = new PromptDialog(context, this);
            return cookie;
        }

        public PromptDialog createAlert() {
            this.setRoundColor(Color.WHITE)
                    .setRoundAlpha(255)
                    .setTextColor(Color.GRAY)
                    .setTextColor(15)
                    .setCancleAble(true);
            PromptDialog cookie = new PromptDialog(context, this);
            return cookie;
        }
    }

}
