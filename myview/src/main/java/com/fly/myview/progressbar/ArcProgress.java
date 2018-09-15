package com.fly.myview.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.res.TypedArrayUtils;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.fly.myview.R;

import java.text.DecimalFormat;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.myview.progressbar
 * 作    者 : FLY
 * 创建时间 : 2018/8/16
 * <p>
 * 描述:
 */

public class ArcProgress extends View {

    /**
     * 默认进度条所占用的角度
     */
    private final int ARC_FULL_DEGREE = 300;

    /**
     * 默认已完成颜色
     */
    private final int DEFAULT_FINISHED_COLOR = getResources().getColor(R.color.arc_progress_finished_color);

    /**
     * 默认未完成颜色
     */
    private final int DEFAULT_UNFINISHED_COLOR = getResources().getColor(R.color.arc_progress_unfinished_color);

    /**
     * 弧线的宽度
     */
    private int STROKE_WIDTH;

    /**
     * 组件的宽，高
     */
    private int width, height;

    /**
     * 进度条最大值和当前进度值
     */
    private float max, progress;

    /**
     * 进度条所占用的角度
     */
    private int arcFullDegree;

    /**
     * 颜色
     */
    private int finishedStrokeColor, unfinishedStrokeColor;

    /**
     * 是否允许拖动进度条
     */
    private boolean draggingEnabled;

    /**
     * 绘制弧线的矩形区域
     */
    private RectF circleRectF;

    /**
     * 绘制弧线的画笔
     */
    private Paint progressPaint;

    /**
     * 绘制文字的画笔
     */
    private Paint textPaint;

    /**
     * 文字 颜色
     */
    private int textColor;

    /**
     * 描述内容
     */
    private String describeText;

    /**
     * 圆弧的半径
     */
    private int circleRadius;

    /**
     * 圆弧圆心位置
     */
    private int centerX, centerY;

    private int animaTime;

    private Rect textBounds = new Rect();


    public ArcProgress(Context context) {
        this(context, null);
    }

    public ArcProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPainters();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcProgress);
        finishedStrokeColor = typedArray.getColor(R.styleable.ArcProgress_arcFinishedColor, DEFAULT_FINISHED_COLOR);
        unfinishedStrokeColor = typedArray.getColor(R.styleable.ArcProgress_arcUnfinishedColor, DEFAULT_UNFINISHED_COLOR);
        max = typedArray.getInteger(R.styleable.ArcProgress_arcProgressMax, 100);
        progress = typedArray.getInteger(R.styleable.ArcProgress_arcProgress, 0);
        arcFullDegree = typedArray.getInteger(R.styleable.ArcProgress_arcFullDegree, ARC_FULL_DEGREE);
        STROKE_WIDTH = typedArray.getInteger(R.styleable.ArcProgress_arcWidth, 0);
        animaTime = typedArray.getInteger(R.styleable.ArcProgress_animaTime, 500);
        draggingEnabled = typedArray.getBoolean(R.styleable.ArcProgress_draggingEnabled, false);
        describeText = typedArray.getString(R.styleable.ArcProgress_describeText);
        textColor = typedArray.getColor(R.styleable.ArcProgress_describeTextColor, DEFAULT_FINISHED_COLOR);
        typedArray.recycle();
    }

    @Override
    public void invalidate() {//调用invalidate方法只会执行onDraw
        super.invalidate();
    }

    private void initPainters() {
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        progressPaint.setAntiAlias(true);//防抖动

        /**  线帽
         * Paint.Cap.BUTT   没有
         * Paint.Cap.ROUND  圆的
         * Paint.Cap.SQUARE 方形
         */
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
    }

    /**
     * 用于测量视图的大小，主要是用来测量自己和内容的来确定宽度和高度
     * int widthMeasureSpec, int heightMeasureSpec，该参数表示控件可获得的空间以及关于这个空间描述的元数据
     * widthMeasureSpec和heightMeasureSpec这两个值通常情况下都是由父视图经过计算后传递给子视图的，说明父视图会在一定程度上决定子视图的大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (width == 0 || height == 0) {
            width = getMeasuredWidth();
            height = getMeasuredHeight();

            circleRadius = Math.min(width, height) / 2;
            if (STROKE_WIDTH <= 0) STROKE_WIDTH = circleRadius / 12;//弧线的宽度默认为半径 1/12
            circleRadius -= STROKE_WIDTH;
            circleRadius -= 0.6 * STROKE_WIDTH;

            centerX = width / 2;
            centerY = height / 2;

            /**
             * left：矩形左边线条离 y 轴的距离
             * top：矩形上面线条离 x 轴的距离
             * right：矩形右边线条离 y 轴的距离
             *  bottom：矩形底部线条离 x 轴的距离
             */
            //圆弧所在矩形区域
            circleRectF = new RectF(centerX - circleRadius, centerY - circleRadius,
                    centerX + circleRadius, centerY + circleRadius);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startAngle = 90 + ((360 - arcFullDegree) >> 1); //进度条起始点
        float sweep1 = arcFullDegree * (progress / max);
        float sweep2 = arcFullDegree - sweep1;

        progressPaint.setColor(finishedStrokeColor);
        progressPaint.setStyle(Paint.Style.STROKE);//空心 描边
        progressPaint.setStrokeWidth(STROKE_WIDTH);
        canvas.drawArc(circleRectF, startAngle, sweep1, false, progressPaint);

        progressPaint.setColor(unfinishedStrokeColor);
        canvas.drawArc(circleRectF, startAngle + sweep1, sweep2, false, progressPaint);


        progressPaint.setStyle(Paint.Style.FILL);//空心 描边
        progressPaint.setStrokeWidth(0);
        float progressRadians = (float) (((360.0f - ARC_FULL_DEGREE) / 2 + sweep1) / 180 * Math.PI);
        float thumbX = centerX - circleRadius * (float) Math.sin(progressRadians);
        float thumbY = centerY + circleRadius * (float) Math.cos(progressRadians);
        progressPaint.setColor(Color.parseColor("#33d64444"));
        canvas.drawCircle(thumbX, thumbY, STROKE_WIDTH * 1.6f, progressPaint);
        progressPaint.setColor(Color.parseColor("#99d64444"));
        canvas.drawCircle(thumbX, thumbY, STROKE_WIDTH * 1.2f, progressPaint);
        progressPaint.setColor(Color.WHITE);
        canvas.drawCircle(thumbX, thumbY, STROKE_WIDTH * 0.8f, progressPaint);

        textPaint.setTextSize(circleRadius >> 1);
        textPaint.setColor(textColor);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String textTime = decimalFormat.format(100 * progress / max);
        float textLen = textPaint.measureText(textTime);
        textPaint.getTextBounds("8", 0, 1, textBounds);
        float textTimeH = textBounds.height();
        float extra = textTime.startsWith("1") ? -textPaint.measureText("1") / 2 : 0;
        canvas.drawText(textTime, centerX - textLen / 2 + extra, centerY - 30 + textTimeH / 2, textPaint);

        //百分号
        textPaint.setTextSize(circleRadius >> 2);
        canvas.drawText("%", centerX + textLen / 2 + extra + 5, centerY - 30 + textTimeH / 2, textPaint);

        textPaint.setTextSize(circleRadius / 5);
        if (TextUtils.isEmpty(describeText)) describeText = "进度";
        textLen = textPaint.measureText(describeText);
        textPaint.getTextBounds(describeText, 0, describeText.length(), textBounds);
        float describeTextH = textBounds.height();
        canvas.drawText(describeText, centerX - textLen / 2, centerY + textTimeH / 2 + describeTextH, textPaint);

    }


    private boolean isDragging = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!draggingEnabled) {
            return super.onTouchEvent(event);
        }

        //处理拖动事件
        float currentX = event.getX();
        float currentY = event.getY();

        switch (event.getAction()) {//判断手指状态
            case MotionEvent.ACTION_DOWN://按下
                //判断是否在进度条thumb位置
                if (checkOnArc(currentX, currentY)) {
                    float newProgress = calDegreeByPosition(currentX, currentY) / ARC_FULL_DEGREE * max;
                    setProgress(newProgress);
                    isDragging = true;
                }
                break;
            case MotionEvent.ACTION_MOVE://滑动
                if (isDragging) {
                    //判断拖动时是否移出去了
                    if (checkOnArc(currentX, currentY)) {
                        setProgress(calDegreeByPosition(currentX, currentY) / ARC_FULL_DEGREE * max);
                    } else {
                        isDragging = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP://离开
                isDragging = false;
                break;
        }
        return true;
    }

    //返回A的正确舍入正平方根
    private float calDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }


    /**
     * 判断该点是否在弧线上（附近）
     */
    private boolean checkOnArc(float currentX, float currentY) {
        float distance = calDistance(currentX, currentY, centerX, centerY);
        float degree = calDegreeByPosition(currentX, currentY);
        return distance > circleRadius - STROKE_WIDTH * 5 //点击区域弧内五倍STROKE_WIDTH
                && distance < circleRadius + STROKE_WIDTH * 1//点击区域弧外五倍STROKE_WIDTH
                && (degree >= -8 &&//圆弧开始位置
                degree <= ARC_FULL_DEGREE + 8);//圆弧结束位置
    }


    /**
     * 根据当前位置，计算出进度条已经转过的角度。
     */
    private float calDegreeByPosition(float currentX, float currentY) {
        float a1 = (float) (Math.atan(1.0f * (centerX - currentX) / (currentY - centerY)) / Math.PI * 180);
        if (currentY < centerY) {
            a1 += 180;
        } else if (currentY > centerY && currentX > centerX) {
            a1 += 360;
        }


        return a1 - (360 - ARC_FULL_DEGREE) / 2;
    }


    public float getProgress() {
        return progress;
    }

    public void setProgressAnima(float progress) {
        final float thisProgress = checkProgress(progress);
        new Thread(new Runnable() {
            @Override
            public void run() {
                float oldProgress = ArcProgress.this.progress;
                for (float i = 1.0f; i <= 100f; i++) {
                    ArcProgress.this.progress = oldProgress + (thisProgress - oldProgress) * i / 100;
                    postInvalidate();
                    SystemClock.sleep(animaTime / 100);
                }
            }
        }).start();
    }

    public void setProgress(float progress) {
        this.progress = checkProgress(progress);
        invalidate();
    }

    public float getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    private float checkProgress(float progress) {
        if (progress < 0) return 0;
        return progress > max ? max : progress;
    }

    private int dp2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }
}
