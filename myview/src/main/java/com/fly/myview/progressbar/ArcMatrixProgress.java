package com.fly.myview.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.fly.myview.R;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.myview.progressbar
 * 作    者 : FLY
 * 创建时间 : 2018/8/22
 * <p>
 * 描述:
 */

public class ArcMatrixProgress extends View {

    /**
     * 控件宽，高
     */
    private int width, height;

    /**
     * 进度条占用弧度
     */
    private int arcOwnDegree;

    private float max, progress;

    /**
     * 进度条颜色
     */
    private int arcFinishedColor, arcUnfinishedColor;

    /**
     * 弧线宽度
     */
    private int arcWidth;

    /**
     * 弧线画笔
     */
    private Paint progressPaint;

    /**
     * 圆心坐标
     */
    private int circularX, circularY;


    public ArcMatrixProgress(Context context) {
        this(context, null);
    }

    public ArcMatrixProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMatrixProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPainters();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcMatrixProgress);
        arcFinishedColor = typedArray.getColor(R.styleable.ArcMatrixProgress_arcMatrixFinishedColor, getResources().getColor(R.color.arc_matrix_progress_finished_color));
        arcUnfinishedColor = typedArray.getColor(R.styleable.ArcMatrixProgress_arcMatrixUnfinishedColor, getResources().getColor(R.color.arc_matrix_progress_unfinished_color));
        arcOwnDegree = typedArray.getInteger(R.styleable.ArcMatrixProgress_arcMatrixOwnDegree,300);
        arcWidth = typedArray.getInteger(R.styleable.ArcMatrixProgress_arcMatrixWitch,0);
        max = typedArray.getInteger(R.styleable.ArcMatrixProgress_arcMatrixProgressMax,100);
        progress = typedArray.getInteger(R.styleable.ArcMatrixProgress_arcMatrixProgress,0);
        typedArray.recycle();
    }

    private void initPainters() {
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setAntiAlias(false);
        progressPaint.setStrokeCap(Paint.Cap.SQUARE);


    }
}
