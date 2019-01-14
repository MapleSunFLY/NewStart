package com.fly.myview.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
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
 * 描述: 统计折线图
 */
public class StatisticsLineChartView extends View {


    /**
     * 折线颜色
     */
    private int mLineColor;

    /**
     * 折线高度
     */
    private float mLineHeight;

    /**
     * 折线点数
     */
    private List<Integer> mLines = new ArrayList<>();

    /**
     * X坐标值点数
     */
    private List<String> mNums = new ArrayList<String>();

    /**
     * 折线单位
     */
    private String mLineUnit = "";

    /**
     * 数字单位
     */
    private String mNumUnit = "";

    /**
     * 虚线颜色
     */
    private int mDottedLineColor;

    /**
     * 实线颜色
     */
    private int mSolidLineColor;

    /**
     * max背景颜色
     */
    private int mMaxBackgroundColor;

    /**
     * min背景颜色
     */
    private int mMinBackgroundColor;

    /**
     * max Or min背景所在矩形区域
     */
    private RectF mRectF = new RectF(0, 0, 0, 0);

    /**
     * 提示文本颜色
     */
    private int mTextColor;

    /**
     * 提示文本大小
     */
    private float mTextSize;

    private float oldX;
    private float oldY;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 画笔
     */
    private Paint mDottedPaint;


    public StatisticsLineChartView(Context context) {
        this(context, null);
    }

    public StatisticsLineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsLineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPainters();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatisticsLineChart);
        mLineUnit = typedArray.getString(R.styleable.StatisticsLineChart_statisticsLineUnit);
        mNumUnit = typedArray.getString(R.styleable.StatisticsLineChart_statisticsNumUnit);
        mLineColor = typedArray.getColor(R.styleable.StatisticsLineChart_statisticsLineColor, Color.parseColor("#FF6770"));
        mDottedLineColor = typedArray.getColor(R.styleable.StatisticsLineChart_statisticsDottedLineColor, Color.parseColor("#D8D8D8"));
        mSolidLineColor = typedArray.getColor(R.styleable.StatisticsLineChart_statisticsSolidLineColor, Color.parseColor("#999999"));
        mMaxBackgroundColor = typedArray.getColor(R.styleable.StatisticsLineChart_statisticsMaxBackgroundColor, Color.parseColor("#FF6770"));
        mMinBackgroundColor = typedArray.getColor(R.styleable.StatisticsLineChart_statisticsMinBackgroundColor, Color.parseColor("#DDDDDD"));
        mLineHeight = typedArray.getDimension(R.styleable.StatisticsLineChart_statisticsLineHeight, ViewUtils.dip2px(1f));
        mTextColor = typedArray.getColor(R.styleable.StatisticsLineChart_statisticsLineTextColor, Color.WHITE);
        mTextSize = typedArray.getDimension(R.styleable.StatisticsLineChart_statisticsLineTextSize, ViewUtils.dip2px(7f));
        typedArray.recycle();
    }

    private void initPainters() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setAntiAlias(true);//防抖动
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mLineHeight);


        mDottedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mDottedPaint.setAntiAlias(true);//防抖动
        mDottedPaint.setStyle(Paint.Style.STROKE);
        mDottedPaint.setStrokeCap(Paint.Cap.ROUND);
        mDottedPaint.setStrokeWidth(mLineHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth() - getPaddingRight() - getPaddingLeft() - mTextSize * 2;
        float height = getHeight() - getPaddingTop() - getPaddingBottom() - mTextSize * 2;
        float originX = getPaddingLeft() + mTextSize;
        float originY = getPaddingTop() + height / 4 * 3;

        drawBackground(canvas, width, height, originX);

        if (mLines == null || mLines.size() == 0) return;
        int max = Collections.max(mLines);
        int min = Collections.min(mLines);
        boolean isMax = true;
        boolean isMin = true;
        double mNumDifference = max - min;
        mNumDifference = mNumDifference <= 0 ? 1 : mNumDifference;
        double coefficient = height * 0.5 / mNumDifference;
        float differenceX = width / (mLines.size() - 1);
        oldX = originX;
        oldY = originY;

        for (int i = 0; i < mLines.size(); i++) {
            int thisDouble = mLines.get(i);
            float cy = (float) (originY + (min - thisDouble) * coefficient);
            if (i == 0) oldY = cy;
            drawLine(canvas, originX + differenceX * i, cy, mLineColor);
        }

        for (int i = 0; i < mLines.size(); i++) {
            int thisDouble = mLines.get(i);
            float cy = (float) (originY + (min - thisDouble) * coefficient);
            if (isMax && thisDouble == max) {
                isMax = false;
                drawMaxSpot(canvas, originX + differenceX * i, cy, mMaxBackgroundColor, String.valueOf(thisDouble));
            } else if (isMin && thisDouble == min) {
                isMin = false;
                drawMinSpot(canvas, originX + differenceX * i, cy, mMinBackgroundColor, String.valueOf(thisDouble));
            }
        }

    }

    private void drawBackground(Canvas canvas, float width, float height, float originX) {
        mDottedPaint.setColor(mDottedLineColor);
        Path path = new Path();
        mDottedPaint.setPathEffect(new DashPathEffect(new float[]{ViewUtils.dip2px(4f), ViewUtils.dip2px(4f)}, 0));
        for (int i = 1; i <= 4; i++) {
            path.moveTo(originX, getPaddingTop() + height / 4 * i);
            path.lineTo(originX + width, getPaddingTop() + height / 4 * i);
            canvas.drawPath(path, mDottedPaint);
            if (i == 4) {
                mPaint.setColor(mSolidLineColor);
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawLine(originX, getPaddingTop() + height / 4 * i, originX + width, getPaddingTop() + height / 4 * i, mPaint);
                for (int j = 0; j < mNums.size(); j++) {
                    String text = mNums.get(j);
                    if (j == mNums.size() - 1) text = text + mNumUnit;
                    mPaint.setStrokeWidth(mLineHeight / 3);
                    mPaint.setTextSize(mTextSize);
                    mPaint.setStyle(Paint.Style.FILL);
                    float mDrawTextWidth = mPaint.measureText(text);
                    if (j < mNums.size() - 1) {
                        canvas.drawText(text, originX + width / (mNums.size() - 1) * j - mTextSize / 3, getPaddingTop() + height + mTextSize * 1.5f, mPaint);
                    } else {
                        canvas.drawText(text, originX + width / (mNums.size() - 1) * j - mDrawTextWidth + mTextSize / 2, getPaddingTop() + height + mTextSize * 1.5f, mPaint);
                    }
                }
            }
        }
    }

    private void drawLine(Canvas canvas, float cx, float cy, int lineColor) {
        mPaint.setColor(lineColor);
        mPaint.setStrokeWidth(mLineHeight);
        canvas.drawLine(oldX, oldY, cx, cy, mPaint);
        oldX = cx;
        oldY = cy;
    }

    private void drawMaxSpot(Canvas canvas, float cx, float cy, int lineColor, String text) {
        text = text + mLineUnit;
        float mDrawTextWidth = mPaint.measureText(text);
        mPaint.setColor(lineColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawCircle(cx, cy, mLineHeight * 2, mPaint);
        Path path = new Path();
        cy = cy - mTextSize * 0.8f;
        path.moveTo(cx, cy + mLineHeight * 2f);
        path.lineTo(cx - mLineHeight * 2f, cy);
        path.lineTo(cx + mLineHeight * 2f, cy);
        path.close();
        canvas.drawPath(path, mPaint);

        if (cx - getPaddingLeft() > mDrawTextWidth / 2) {
            if (getWidth() - getPaddingRight() - cx > mDrawTextWidth / 2) {
                cx = cx - mDrawTextWidth / 2;
            } else cx = cx + mTextSize / 3 - mDrawTextWidth;
        } else cx = cx - mTextSize / 3;


        mRectF.left = cx - mTextSize / 2;
        mRectF.top = cy - mTextSize * 1.5f;
        mRectF.right = cx + mTextSize / 2 + mDrawTextWidth;
        mRectF.bottom = cy;
        canvas.drawRect(mRectF, mPaint);

        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        canvas.drawText(text, cx, cy - mTextSize / 3, mPaint);
    }

    private void drawMinSpot(Canvas canvas, float cx, float cy, int lineColor, String text) {
        text = text + mLineUnit;
        float mDrawTextWidth = mPaint.measureText(text);
        mPaint.setColor(lineColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawCircle(cx, cy, mLineHeight * 2, mPaint);
        Path path = new Path();
        cy = cy + mTextSize * 0.8f;
        path.moveTo(cx, cy - mLineHeight * 2f);
        path.lineTo(cx - mLineHeight * 2f, cy);
        path.lineTo(cx + mLineHeight * 2f, cy);
        path.close();
        canvas.drawPath(path, mPaint);

        if (cx - getPaddingLeft() > mDrawTextWidth / 2) {
            if (getWidth() - getPaddingRight() - cx > mDrawTextWidth / 2) {
                cx = cx - mDrawTextWidth / 2;
            } else cx = cx + mTextSize / 3 - mDrawTextWidth;
        } else cx = cx - mTextSize / 3;


        mRectF.left = cx - mTextSize / 2;
        mRectF.top = cy;
        mRectF.right = cx + mTextSize / 2 + mDrawTextWidth;
        mRectF.bottom = cy + mTextSize * 1.3f;
        canvas.drawRect(mRectF, mPaint);

        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        canvas.drawText(text, cx, cy + mTextSize, mPaint);
    }

    public void setLines(List<Integer> mLines) {
        this.mLines = mLines == null ? new ArrayList<Integer>() : mLines;
        invalidate();
    }

    public void setNums(List<String> mNums) {
        this.mNums = mNums;
        invalidate();
    }

    public void setUnit(String mNumUnit) {
        this.mNumUnit = mNumUnit;
        invalidate();
    }
}
