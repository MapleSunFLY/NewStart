package com.fly.myview.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.fly.myview.R;
import com.shangyi.android.utils.ViewUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.myview.chart
 * 作    者 : FLY
 * 创建时间 : 2018/12/14
 * 描述: 折线图
 */
public class LineChartView extends View {

    /**
     * 默认颜色
     */
    private final int DEFAULT_COLOR = getResources().getColor(R.color.line_chart_color);

    /**
     * 默认Null颜色
     */
    private final int DEFAULT_NULL_COLOR = getResources().getColor(R.color.line_chart_null_color);


    /**
     * 颜色
     */
    private int mLineColor;

    /**
     * Null颜色
     */
    private int mNullLineColor;

    private int mIntervalColor;

    /**
     * 折线高度
     */
    private float mLineHeight;

    private float mSpotRadius;

    private int mMinSpotNum;

    /**
     * 折线点数
     */
    private List<Double> mLines = new ArrayList<>();

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 提示文本颜色
     */
    private int mTextColor;

    /**
     * 提示文本大小
     */
    private float mTextSize;

    private boolean mTextVisibility;

    private float oldX;
    private float oldY;


    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPainters();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineChart);
        mLineColor = typedArray.getColor(R.styleable.LineChart_lineColor, DEFAULT_COLOR);
        mNullLineColor = typedArray.getColor(R.styleable.LineChart_lineNullColor, DEFAULT_NULL_COLOR);
        mIntervalColor = typedArray.getColor(R.styleable.LineChart_lineIntervalColor, DEFAULT_NULL_COLOR);
        mLineHeight = typedArray.getDimension(R.styleable.LineChart_lineHeight, ViewUtils.dip2px(2f));
        mSpotRadius = typedArray.getDimension(R.styleable.LineChart_lineSpotRadius, ViewUtils.dip2px(3f));
        mMinSpotNum = typedArray.getInteger(R.styleable.LineChart_lineMinSpotNum, 6);
        mTextSize = typedArray.getDimension(R.styleable.LineChart_lineTextSize, ViewUtils.dip2px(10f));
        mTextColor = typedArray.getColor(R.styleable.LineChart_lineTextColor, DEFAULT_COLOR);
        mTextVisibility = typedArray.getBoolean(R.styleable.LineChart_lineTextVisibility, true);
        typedArray.recycle();
    }

    private void initPainters() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setAntiAlias(true);//防抖动
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth() - getPaddingRight() - getPaddingLeft() - mSpotRadius * 2f - mTextSize * 2f;
        float height = getHeight() - getPaddingTop() - getPaddingBottom() - mSpotRadius - mTextSize;

        float originX = getPaddingLeft() + mSpotRadius + mTextSize;
        float originY = height / 2 + getPaddingTop() + mSpotRadius + mTextSize;
        float middleY = getHeight() / 2;
        oldX = originX;
        oldY = middleY;
        double coefficient = 1;
        double average = 0;
        if (mLines.size() > 0) {
            average = (Collections.max(mLines) + Collections.min(mLines)) / 2;
            double mNumDifference = Collections.max(mLines) - Collections.min(mLines);
            mNumDifference = mNumDifference <= 0 ? 1 : mNumDifference;
            coefficient = height / mNumDifference;
        }
        float differenceX;
        if (mMinSpotNum > mLines.size()) {
            differenceX = width / (mMinSpotNum - 1);
            for (int i = 0; i < mMinSpotNum; i++) {
                int index = i + mLines.size() - mMinSpotNum;
                if (i < mMinSpotNum - mLines.size() || mLines.get(index) == null || mLines.get(index) <= 0) {
                    drawLine(canvas, originX + differenceX * i, middleY, mNullLineColor);
                } else {
                    Double thisDouble = mLines.get(index);
                    float cy = (float) (originY + (average - thisDouble) * coefficient);
                    if (i == 0) oldY = cy;
                    drawLine(canvas, originX + differenceX * i, cy, index == 0 ? mNullLineColor : mLineColor);
                }
            }

            for (int i = 0; i < mMinSpotNum; i++) {
                int index = i + mLines.size() - mMinSpotNum;
                if (i < mMinSpotNum - mLines.size() || mLines.get(index) == null || mLines.get(index) <= 0) {
                    drawSpotText(canvas, originX + differenceX * i, middleY, mNullLineColor, "");
                } else {
                    Double thisDouble = mLines.get(index);
                    float cy = (float) (originY + (average - thisDouble) * coefficient);
                    drawSpotText(canvas, originX + differenceX * i, cy, mLineColor, String.valueOf(thisDouble));
                }
            }
        } else {
            differenceX = width / (mLines.size() - 1);
            for (int i = 0; i < mLines.size(); i++) {
                Double thisDouble = mLines.get(i);
                if (thisDouble == null || thisDouble <= 0) {
                    drawLine(canvas, originX + differenceX * i, middleY, mNullLineColor);
                } else {
                    float cy = (float) (originY + (average - thisDouble) * coefficient);
                    if (i == 0) oldY = cy;
                    drawLine(canvas, originX + differenceX * i, cy, mLineColor);
                }
            }
            for (int i = 0; i < mLines.size(); i++) {
                Double thisDouble = mLines.get(i);
                if (thisDouble == null || thisDouble <= 0) {
                    drawSpotText(canvas, originX + differenceX * i, middleY, mNullLineColor, "");
                } else {
                    float cy = (float) (originY + (average - thisDouble) * coefficient);
                    drawSpotText(canvas, originX + differenceX * i, cy, mLineColor, String.valueOf(thisDouble));
                }
            }
        }
    }


    private void drawSpotText(Canvas canvas, float cx, float cy, int lineColor, String text) {
        mPaint.setColor(mIntervalColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, mSpotRadius * 1.8f, mPaint);
        mPaint.setColor(lineColor);
        mPaint.setStrokeWidth(mLineHeight / 2);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx, cy, mSpotRadius, mPaint);

        if (!TextUtils.isEmpty(text) && mTextVisibility) {
            mPaint.setTextSize(mTextSize);
            float mDrawTextWidth = mPaint.measureText(text);
            canvas.drawText(text, cx - mDrawTextWidth / 2, cy - mLineHeight * 3f - ViewUtils.px2dip(mTextSize), mPaint);
        }
    }

    private void drawLine(Canvas canvas, float cx, float cy, int lineColor) {
        mPaint.setColor(lineColor);
        mPaint.setStrokeWidth(mLineHeight);
        canvas.drawLine(oldX, oldY, cx, cy, mPaint);
        oldX = cx;
        oldY = cy;
    }

    public void setLines(List<Double> mLines) {
        this.mLines = mLines == null ? new ArrayList<Double>() : mLines;
        invalidate();
    }
}
