package com.fly.newstart.masstouch.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.fly.newstart.R;

/**
 * 版    权 ：华润创业有限公司
 * 项目名称 : CRB_CRM
 * 包    名 : com.fly.newstart.masstouch.widget
 * 作    者 : FLY
 * 创建时间 : 2017/9/27
 * <p>
 * 描述:
 */

public class DragViewSingleTouch extends View {

    private static final String TAG = "DragViewSingleTouch";

    Bitmap mBitmap;         // 图片
    RectF mBitmapRectF;     // 图片所在区域
    Matrix mBitmapMatrix;   // 控制图片的 matrix

    boolean canDrag = false;
    PointF lastPoint = new PointF(0, 0);
    private Paint mDeafultPaint;

    public DragViewSingleTouch(Context context) {
        this(context,null);
    }

    public DragViewSingleTouch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mDeafultPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        // 调整图片大小
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = 960/2;
        options.outHeight = 800/2;

        mBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.poly_test, options);
        mBitmapRectF = new RectF(0,0,mBitmap.getWidth(), mBitmap.getHeight());
        mBitmapMatrix = new Matrix();
    }
}
