package com.fly.myview.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.fly.myview.R;
import com.shangyi.android.utils.ViewUtils;

import java.util.List;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.myview.progressbar
 * 作    者 : FLY
 * 创建时间 : 2018/9/6
 * 描述: 阶段的进度条
 */

public class StageProgress extends View {

    /**
     * 进度条最大值
     */
    private float mMax = 100;

    /**
     * 进度条当前进度值
     */
    private float mProgress = 0;

    /**
     * 当前阶段
     */
    private int mThisStage = 1;

    /**
     * 总阶段
     */
    private int mAllStage = 4;


    /**
     * 默认已完成颜色
     */
    private final int DEFAULT_FINISHED_COLOR = getResources().getColor(R.color.arc_progress_finished_color);

    /**
     * 默认未完成颜色
     */
    private final int DEFAULT_UNFINISHED_COLOR = getResources().getColor(R.color.arc_progress_unfinished_color);

    /**
     * 已完成进度颜色
     */
    private int mReachedBarColor;

    /**
     * 未完成进度颜色
     */
    private int mUnreachedBarColor;

    /**
     * 未完成文本颜色
     */
    private int mUnreachedTextColor;

    /**
     * 进度条高度
     */
    private float mBarHeight;

    /**
     * 未完成进度条所在矩形区域
     */
    private RectF mUnreachedRectF = new RectF(0, 0, 0, 0);

    /**
     * 已完成进度条所在矩形区域
     */
    private RectF mReachedRectF = new RectF(0, 0, 0, 0);

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 阶段提示文本
     */
    private List<String> mStageTexts;

    private Drawable mUnreachedBarDrawable;

    private boolean mTextVisibility;

    /**
     * 提示文本颜色
     */
    private int mTextColor;

    /**
     * 提示文本
     */
    private String mText;

    /**
     * 提示文本颜色
     */
    private int mTextBackgroundColor;


    public StageProgress(Context context) {
        this(context, null);
    }

    public StageProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StageProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPainters();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StageProgress);
        mMax = typedArray.getInteger(R.styleable.StageProgress_stageMax, (int) mMax);
        mProgress = typedArray.getInteger(R.styleable.StageProgress_stageProgress, (int) mProgress);
        mAllStage = typedArray.getInteger(R.styleable.StageProgress_stageAllStage, mAllStage);
        mThisStage = typedArray.getInteger(R.styleable.StageProgress_stageThisStage, mThisStage);
        mReachedBarColor = typedArray.getColor(R.styleable.StageProgress_stageReachedBarColor, DEFAULT_FINISHED_COLOR);
        mUnreachedBarColor = typedArray.getColor(R.styleable.StageProgress_stageUnreachedBarColor, DEFAULT_UNFINISHED_COLOR);
        mUnreachedTextColor = typedArray.getColor(R.styleable.StageProgress_stageUnreachedTextColor, DEFAULT_UNFINISHED_COLOR);
        mTextColor = typedArray.getColor(R.styleable.StageProgress_stageTextColor, DEFAULT_FINISHED_COLOR);
        mTextBackgroundColor = typedArray.getColor(R.styleable.StageProgress_stageTextBackgroundColor, DEFAULT_UNFINISHED_COLOR);
        mBarHeight = typedArray.getDimension(R.styleable.StageProgress_stageBarHeight, ViewUtils.dip2px(3f));
        mText = typedArray.getString(R.styleable.StageProgress_stageText);
        mTextVisibility = typedArray.getBoolean(R.styleable.StageProgress_stageTextVisibility, true);
        mUnreachedBarDrawable = typedArray.getDrawable(R.styleable.StageProgress_stageUnreachedBarBitmap);
        if (mUnreachedBarDrawable != null) {
            Bitmap bitmap = Bitmap.createBitmap(ViewUtils.dip2px(mBarHeight * 1.5f), ViewUtils.dip2px(mBarHeight * 1.5f), Bitmap.Config.ARGB_8888);
            Canvas cas = new Canvas(bitmap);
            mUnreachedBarDrawable.setBounds(0, 0, ViewUtils.dip2px(mBarHeight * 1.5f), ViewUtils.dip2px(mBarHeight * 1.5f));
            mUnreachedBarDrawable.draw(cas);
        }
        typedArray.recycle();
    }

    private void initPainters() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setAntiAlias(true);//防抖动
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculateDrawRectFWithoutProgressText();

        mPaint.setColor(mUnreachedBarColor);
        canvas.drawRoundRect(mUnreachedRectF, mBarHeight / 2, mBarHeight / 2, mPaint);

        mPaint.setColor(mReachedBarColor);
        canvas.drawRoundRect(mReachedRectF, mBarHeight / 2, mBarHeight / 2, mPaint);

        for (int i = 0; i <= mAllStage; i++) {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(0);
            float cx = i == 0 ? mUnreachedRectF.left : mUnreachedRectF.right / mAllStage * i;
            if (i < mThisStage - 1 || (i == mThisStage - 1 && mProgress == mMax)) {
                mPaint.setColor(mReachedBarColor);
                canvas.drawCircle(cx, getHeight() / 2, mBarHeight * 2.6f, mPaint);
                mPaint.setStrokeWidth(ViewUtils.dip2px(2));
                mPaint.setColor(Color.WHITE);
                canvas.drawLine(cx - 1.2f * mBarHeight, getHeight() / 2 + mBarHeight * 0.2f, cx - mBarHeight * 0.5f, getHeight() / 2 + mBarHeight, mPaint);
                canvas.drawLine(cx - mBarHeight * 0.3f, getHeight() / 2 + mBarHeight, cx + 1.2f * mBarHeight, getHeight() / 2 - mBarHeight, mPaint);
                mPaint.setColor(mReachedBarColor);
            } else if (i == mThisStage - 1 || (i == mThisStage && mProgress == mMax)) {
                mPaint.setColor(Color.parseColor("#40FF6770"));
                canvas.drawCircle(cx, getHeight() / 2, mBarHeight * 2.6f, mPaint);
                mPaint.setColor(mReachedBarColor);
                canvas.drawCircle(cx, getHeight() / 2, mBarHeight * 1.5f, mPaint);
            } else {
                mPaint.setColor(mUnreachedTextColor);
                if (mUnreachedBarDrawable != null) {
                    canvas.save();
                    canvas.translate(cx - mUnreachedBarDrawable.getMinimumWidth() / 3, getHeight() / 2 - mUnreachedBarDrawable.getMinimumWidth() / 2.8f);
                    mUnreachedBarDrawable.draw(canvas);
                    canvas.restore();
                } else {
                    canvas.drawCircle(cx, getHeight() / 2, mBarHeight * 2.6f, mPaint);
                }
            }
            mPaint.setTextSize(mBarHeight * 4.5f);
            String mCurrentDrawText = "S" + (i + 1);
            if (i == mAllStage) mCurrentDrawText = "复评";
            if (mStageTexts != null && i < mStageTexts.size()) {
                mCurrentDrawText = mStageTexts.get(i);
            }
            float mDrawTextWidth = mPaint.measureText(mCurrentDrawText);

            canvas.drawText(mCurrentDrawText, cx - mDrawTextWidth / 2, getHeight() / 2 - mBarHeight * 4f, mPaint);
        }

        if (mTextVisibility && !TextUtils.isEmpty(mText)) {
            float cx = mThisStage == 1 ? mUnreachedRectF.left : mUnreachedRectF.right / mAllStage * mThisStage;
            float mDrawTextWidth = mPaint.measureText(mText);
            float cy = getHeight() / 2 + mBarHeight * 12;

            mPaint.setColor(mTextBackgroundColor);
            Path path = new Path();
            path.moveTo(cx, cy - mPaint.getTextSize() - 3 * mBarHeight);
            path.lineTo(cx - 1.3f * mBarHeight, cy - mPaint.getTextSize() - mBarHeight);
            path.lineTo(cx + 1.3f * mBarHeight, cy - mPaint.getTextSize() - mBarHeight);
            path.close();
            canvas.drawPath(path, mPaint);

            if (cx > mDrawTextWidth / 2) {
                if (getWidth() - cx > mDrawTextWidth / 2) {
                    cx = cx - mDrawTextWidth / 2;
                } else cx = cx + 1.3f * mBarHeight;
            } else cx = cx - 1.3f * mBarHeight;

            mPaint.setStrokeCap(Paint.Cap.BUTT);
            mReachedRectF.left = cx - 1.5f * mBarHeight;
            mReachedRectF.top = cy - mPaint.getTextSize() - mBarHeight;
            mReachedRectF.right = cx + 1.5f * mBarHeight + mDrawTextWidth;
            mReachedRectF.bottom = cy + mPaint.getTextSize() / 2 + 0.8f * mBarHeight;
            canvas.drawRoundRect(mReachedRectF, cx, cy, mPaint);

            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setColor(mTextColor);
            canvas.drawText(mText, cx, cy, mPaint);
        }
    }

    private void calculateDrawRectFWithoutProgressText() {
        mUnreachedRectF.left = getPaddingLeft() + mBarHeight * 4f;
        mUnreachedRectF.top = getHeight() / 2.0f - mBarHeight / 2.0f;
        mUnreachedRectF.right = getWidth() - getPaddingRight() - mBarHeight * 4f;
        mUnreachedRectF.bottom = getHeight() / 2.0f + mBarHeight / 2.0f;

        mReachedRectF.left = mUnreachedRectF.left;
        mReachedRectF.top = mUnreachedRectF.top;
        mReachedRectF.right = mUnreachedRectF.right / mAllStage * (mThisStage - 1) + (mUnreachedRectF.right) / mAllStage * mProgress / mMax;
        mReachedRectF.bottom = mUnreachedRectF.bottom;
    }

    public float getMax() {
        return mMax;
    }

    public void setMax(int max) {
        this.mMax = max;
        invalidate();
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = checkProgress(progress);
        invalidate();
    }

    public int getAllStage() {
        return mAllStage;
    }

    public void setAllStage(int AllStage) {
        this.mAllStage = AllStage;
        invalidate();
    }

    public int getThisStage() {
        return mThisStage;
    }

    public void setThisStage(int mThisStage) {
        this.mThisStage = mThisStage > mAllStage ? mAllStage : mThisStage;
        invalidate();
    }

    public List<String> getStageTexts() {
        return mStageTexts;
    }

    public void setStageTexts(List<String> stageTexts) {
        this.mStageTexts = stageTexts;
        invalidate();
    }

    private float checkProgress(float progress) {
        if (progress < 0) return 0;
        return progress > mMax ? mMax : progress;
    }
}
