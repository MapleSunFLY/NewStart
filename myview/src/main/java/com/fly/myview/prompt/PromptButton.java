package com.fly.myview.prompt;

import android.graphics.Color;
import android.graphics.RectF;

/**
 * 包    名 : com.shangyi.android.msgpromptlibrary.prompt
 * 作    者 : FLY
 * 创建时间 : 2019/6/20
 * 描述:
 */
public class PromptButton {

    private String text = "confirm";

    private int textColor = Color.BLACK;

    private float textSize = 18;

    private RectF rect;

    private PromptButtonListener listener;

    //是否设置背景
    private boolean focus;

    private int focusBacColor = Color.parseColor("#DCDCDC");

    /**
     * 点击是否关闭对话
     */
    private boolean dismissAfterClick = true;

    /**
     * 是否延时点击
     */
    private  boolean isDelyClick;

    private  int delyTime = 100;

    public PromptButton(String text, PromptButtonListener listener) {
        this.text = text;
        this.listener = listener;
    }
    public PromptButton(String text, PromptButtonListener listener,boolean delayClick) {
        this.text = text;
        this.listener = listener;
        this.isDelyClick=delayClick;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }

    public PromptButtonListener getListener() {
        return listener;
    }

    public void setListener(PromptButtonListener listener) {
        this.listener = listener;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public int getFocusBacColor() {
        return focusBacColor;
    }

    public void setFocusBacColor(int focusBacColor) {
        this.focusBacColor = focusBacColor;
    }

    public boolean isDismissAfterClick() {
        return dismissAfterClick;
    }

    public void setDismissAfterClick(boolean dismissAfterClick) {
        this.dismissAfterClick = dismissAfterClick;
    }

    public boolean isDelyClick() {
        return isDelyClick;
    }

    public void setDelyClick(boolean delyClick) {
        isDelyClick = delyClick;
    }

    public int getDelyTime() {
        return delyTime;
    }

    public void setDelyTime(int delyTime) {
        this.delyTime = delyTime;
    }
}
