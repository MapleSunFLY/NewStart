package com.fly.myview.prompt;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.shangyi.android.utils.ViewUtils;


/**
 * 包    名 : com.shangyi.android.msgpromptlibrary.prompt
 * 作    者 : FLY
 * 创建时间 : 2019/6/20
 * 描述: 提示view
 */
@SuppressLint("AppCompatCustomView")
class PromptView extends ImageView {

    /**
     * 没有提示
     */
    public static final int PROMPT_NONE = 104;

    /**
     * 加载
     */
    public static final int PROMPT_LOADING = 105;

    /**
     * 提示
     */
    public static final int PROMPT_TIPS = 106;

    /**
     * 对话
     */
    public static final int PROMPT_DIALOG = 107;

    /**
     * 表单
     */
    public static final int PROMPT_SHEET = 108;

    /**
     * 图片广告
     */
    public static final int PROMPT_AD = 109;

    /**
     * 属性配置
     */
    private PromptDialog.Builder builder;

    /**
     * 调用帮助
     */
    private PromptDialog promptDialog;

    /**
     * 当前窗口类型
     */
    private int currentType;

    private int width;

    private int height;

    /**
     * 画笔
     */
    private Paint paint;

    private ValueAnimator animator;

    /**
     * 画布宽
     */
    private int canvasWidth;

    /**
     * 画布高
     */
    private int canvasHeight;

    /**
     * 原点 X偏移距离
     */
    private int transX;

    /**
     * 原点 Y偏移距离
     */
    private int transY;

    /**
     * 广告图片
     */
    private Bitmap adBitmap;

    /**
     * 底部关闭图片
     */
    private Drawable drawableClose;

    /**
     * 按钮高度
     */
    private float bottomHeight;

    /**
     * 表单高度
     */
    private float sheetHeight;

    /**
     * 表单列表矩形
     */
    private RectF roundTouchRect;

    /**
     * 表单底部按钮矩形
     */
    private RectF roundRect;

    /**
     * 文本的区域
     */
    private Rect textRect;

    /**
     * 表单数组
     */
    private PromptButton[] buttons = new PromptButton[]{};

    private Matrix max;

    public PromptView(Context context) {
        super(context);
    }

    public PromptView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PromptView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PromptView(Activity context, PromptDialog.Builder builder, PromptDialog promptDialog) {
        super(context);
        this.builder = builder;
        this.promptDialog = promptDialog;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setScaleType(ScaleType.MATRIX);
        initData();
    }

    private void initData() {
        if (paint == null) {
            paint = new Paint();
        }

        if (textRect == null) {
            textRect = new Rect();
        }

        if (roundRect == null) {
            roundTouchRect = new RectF();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (paint == null) return;

        if (canvasWidth == 0) {
            canvasWidth = getWidth();
            canvasHeight = getHeight();
        }
        //画背景
        paint.reset(); //重置画笔
        paint.setAntiAlias(true); //抗锯齿
        paint.setColor(builder.backColor);//设置画笔颜色
        paint.setAlpha(builder.backAlpha);//设置画笔透明度
        canvas.drawRect(0, 0, canvasWidth, canvasHeight, paint); //绘制图像

        switch (currentType) {
            case PROMPT_AD:
                drawAD(canvas);
                break;
            case PROMPT_SHEET:
                drawSheet(canvas);
                break;
            case PROMPT_DIALOG:
                drawDialog(canvas);
                break;
            default:
                drawTips(canvas);
                break;
        }
        super.onDraw(canvas);
    }

    /**
     * 绘制广告
     *
     * @param canvas
     */
    private void drawAD(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) return;
        Rect bound = drawable.getBounds();//获取图片区域
        transX = canvasWidth / 2 - bound.width() / 2;
        transY = canvasHeight / 2 - bound.height() / 2 - bound.height() / 10;
        canvas.translate(transX, transY); //移动原点
        if (adBitmap == null) {
            //根据 Drawable 是否系统不透明 选择图像格式
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE
                    ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            //绘制图片
            Bitmap bitmap = Bitmap.createBitmap(drawable.getMinimumWidth(), drawable.getMinimumHeight(), config);
            Canvas ca = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            drawable.draw(ca);
            adBitmap = createRoundConerImage(bitmap, config);
        }
        //将绘制图片画到画布上
        canvas.drawBitmap(adBitmap, 0, 0, null);

        //绘制关闭图片
        if (drawableClose == null) {
            drawableClose = getResources().getDrawable(builder.closeIcon);
        }
        width = drawableClose.getMinimumWidth() / 2;
        height = drawableClose.getMinimumHeight() / 2;
        int left = bound.width() / 2 - width;
        int top = bound.height() + height;
        int right = left + width * 2;
        int bottom = top + height * 2;
        drawableClose.setBounds(left, top, right, bottom);
        drawableClose.draw(canvas);
        //canvas.save();//锁画布(为了保存之前的画布状态)
    }

    /**
     * 根据原图添加圆角
     *
     * @param source 图片
     * @param config 图像格式
     * @return
     */
    private Bitmap createRoundConerImage(Bitmap source, Bitmap.Config config) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(source.getWidth(), source.getHeight(), config);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
        float mRadius = builder.iconRadius;
        canvas.drawRoundRect(rect, mRadius, mRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 绘制表单
     *
     * @param canvas
     */
    private void drawSheet(Canvas canvas) {
        if (roundTouchRect == null) roundTouchRect = new RectF();
        roundTouchRect.set(0, canvasHeight - bottomHeight, canvasWidth, canvasHeight);
        canvas.translate(0, canvasHeight - bottomHeight);//把当前画布的原点移到(x,y)

        //绘制底部按钮
        paint.reset();//重置画笔
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setAlpha(builder.roundAlpha);
        float padBottom = ViewUtils.dip2px(builder.sheetCellPad);//表单外边距
        float pxSheetCellHeight = ViewUtils.dip2px(builder.sheetCellHeight);
        float left = padBottom;
        float top = sheetHeight - padBottom - pxSheetCellHeight;
        float right = canvasWidth - padBottom;
        float bottom = sheetHeight - padBottom;
        float round = ViewUtils.dip2px(builder.round);
        if (roundRect == null) roundRect = new RectF();
        roundRect.set(left, top, right, bottom);
        canvas.drawRoundRect(roundRect, round, round, paint);//绘制底部按钮背景

        bottom = top - padBottom / 2;
        top = 0;

        //绘制标题
        String text = builder.text;
        if (!TextUtils.isEmpty(text)) {
            paint.reset();
            paint.setColor(builder.textColor);
            paint.setTextSize(ViewUtils.dip2px(builder.textSize));
            paint.setAntiAlias(true);
            paint.getTextBounds(text, 0, text.length(), textRect);
            top = -textRect.height() - 1.5f * padBottom;
            canvas.drawText(text, canvasWidth / 2 - textRect.width() / 2, top / 2 + textRect.height() / 2, paint);
        }

        //列表背景
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setAlpha(builder.roundAlpha);
        roundRect.set(left, top, right, bottom);
        canvas.drawRoundRect(roundRect, round, round, paint);

        //画分割线
        paint.setColor(Color.GRAY);
        paint.setAlpha(100);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);

        if (!TextUtils.isEmpty(text)) {
            canvas.drawLine(left, 0, right, 0, paint);
        }

        top = bottom - pxSheetCellHeight;
        canvas.drawLine(left, top, right, top, paint);

        //绘制取消按钮
        PromptButton button = buttons[buttons.length - 1];
        String buttonText = button.getText();
        paint.reset();
        paint.setColor(button.getTextColor());
        paint.setTextSize(ViewUtils.dip2px(button.getTextSize()));
        paint.setAntiAlias(true);
        paint.getTextBounds(buttonText, 0, buttonText.length(), textRect);
        bottom = sheetHeight - padBottom - pxSheetCellHeight / 2 + textRect.height() / 2;
        left = canvasWidth / 2 - textRect.width() / 2;
        canvas.drawText(buttonText, left, bottom, paint);

        if (button.getRect() == null) {
            button.setRect(new RectF(padBottom, canvasHeight -
                    padBottom - pxSheetCellHeight,
                    canvasWidth - padBottom, canvasHeight - padBottom));
        }

        //点击效果
        if (button.isFocus()) {
            paint.reset();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setAlpha(builder.sheetPressAlph);
            RectF rect = new RectF(padBottom, sheetHeight -
                    padBottom - pxSheetCellHeight,
                    canvasWidth - padBottom, sheetHeight -
                    padBottom);
            canvas.drawRoundRect(rect, round, round, paint);
        }

        //第一个选择
        button = buttons[1];
        buttonText = button.getText();
        paint.reset();
        paint.setColor(button.getTextColor());
        paint.setTextSize(ViewUtils.dip2px(button.getTextSize()));
        paint.setAntiAlias(true);
        paint.getTextBounds(buttonText, 0, buttonText.length(), textRect);

        bottom = sheetHeight - 1.5f * padBottom -
                pxSheetCellHeight * 1.5f + textRect.height() / 2;
        left = canvasWidth / 2 - textRect.width() / 2;
        canvas.drawText(buttonText, left, bottom, paint);

        if (button.getRect() == null) {
            button.setRect(new RectF(padBottom, canvasHeight - 1.5f * padBottom - 2f * pxSheetCellHeight,
                    canvasWidth - padBottom, canvasHeight - 1.5f * padBottom - pxSheetCellHeight));
        }

        if (button.isFocus()) {
            float[] outerR = new float[]{0, 0, 0, 0, round, round, round, round};
            ShapeDrawable mDrawables = new ShapeDrawable(new RoundRectShape(outerR, null, null));
            mDrawables.getPaint().setColor(Color.BLACK);
            mDrawables.getPaint().setAlpha(builder.sheetPressAlph);
            RectF rect = button.getRect();
            Rect rectPre = new Rect((int) rect.left, (int) (rect.top - canvasHeight + sheetHeight),
                    (int) rect.right, (int) (rect.bottom - canvasHeight + sheetHeight));
            mDrawables.setBounds(rectPre);
            mDrawables.draw(canvas);
        }

        //后续按钮
        for (int i = 2; i < buttons.length; i++) {
            button = buttons[i];
            buttonText = button.getText();
            paint.reset();
            paint.setColor(button.getTextColor());
            paint.setTextSize(ViewUtils.dip2px(button.getTextSize()));
            paint.setAntiAlias(true);
            paint.getTextBounds(buttonText, 0, buttonText.length(), textRect);
            bottom = sheetHeight - 1.5f * padBottom - (i + 0.5f) * pxSheetCellHeight + textRect.height() / 2;
            left = canvasWidth / 2 - textRect.width() / 2;
            canvas.drawText(buttonText, left, bottom, paint);
            if (button.getRect() == null) {
                button.setRect(new RectF(padBottom, canvasHeight - 1.5f * padBottom - (i + 1f) * pxSheetCellHeight,
                        canvasWidth - padBottom, canvasHeight - 1.5f * padBottom - i * pxSheetCellHeight));
            }

            if (i != buttons.length - 1) {
                paint.setColor(Color.GRAY);
                paint.setAlpha(100);
                paint.setStrokeWidth(1);
                paint.setAntiAlias(true);
                top = sheetHeight - 1.5f * padBottom - (i + 1) * pxSheetCellHeight;
                canvas.drawLine(padBottom, top, canvasWidth - padBottom, top, paint);
            }

        }
    }

    private void drawDialog(Canvas canvas) {
        int pxPad = ViewUtils.dip2px(builder.padding);
        int pxRound = ViewUtils.dip2px(builder.round);

        width = ViewUtils.dip2px(builder.dialogidth);
        height = ViewUtils.dip2px(builder.btnHeight);

        //计算开始位置
        float popWidth = Math.max(textRect.width() + pxPad * 2, 2 * width);
        if (width * 2 < textRect.width() + pxPad * 2) {
            width = (textRect.width() + pxPad * 2) / 2;
        }
        float popHeight = textRect.height() + 3 * pxPad + height * 2 + height;

        float transTop = canvasHeight / 2 - popHeight / 2;
        float transLeft = canvasWidth / 2 - popWidth / 2;
        canvas.translate(transLeft, transTop);//移动原点

        //绘制文本
        paint.reset();
        paint.setColor(builder.textColor);
        paint.setTextSize(ViewUtils.dip2px(builder.textSize));
        paint.setAntiAlias(true);
        float top = pxPad * 2 + height * 2 + textRect.height();
        float left = popWidth / 2 - textRect.width() / 2;
        canvas.drawText(builder.text, left, top, paint);

        //绘制背景
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(builder.roundColor);
        paint.setAlpha(builder.roundAlpha);
        if (roundTouchRect == null) {
            roundTouchRect = new RectF();
        }
        roundTouchRect.set(transLeft, transTop, transLeft + popWidth, transTop + popHeight);
        if (roundRect == null) {
            roundRect = new RectF(0, 0, popWidth, popHeight);
        }
        canvas.drawRoundRect(roundRect, pxRound, pxRound, paint);

        //绘制对话分界线
        top = top + pxPad;
        paint.setColor(Color.DKGRAY);
        paint.setAntiAlias(true);
        canvas.drawLine(0, top, popWidth, top, paint);

        //一个按钮情况
        if (buttons.length == 1) {
            PromptButton button = buttons[0];
            if (button.isFocus()) {
                paint.reset();
                paint.setAntiAlias(true);
                paint.setColor(button.getFocusBacColor());
                paint.setStyle(Paint.Style.FILL);

                canvas.drawRect(0, top, popWidth, top + height - pxRound, paint);
                canvas.drawCircle(pxRound, top + height - pxRound, pxRound, paint);
                canvas.drawCircle(popWidth - pxRound, top + height - pxRound, pxRound, paint);
                canvas.drawRect(pxRound, top + height - pxRound, popWidth - pxRound, top + height, paint);
            }

            String buttonText = button.getText();
            paint.reset();
            paint.setColor(button.getTextColor());
            paint.setTextSize(ViewUtils.dip2px(button.getTextSize()));
            paint.setAntiAlias(true);
            paint.getTextBounds(buttonText, 0, buttonText.length(), textRect);

            button.setRect(new RectF(transLeft, transTop + top,
                    transLeft + popWidth, transTop + top + height));

            canvas.drawText(buttonText, popWidth / 2 - textRect.width() / 2,
                    top + textRect.height() / 2 + height / 2, paint);
        } else if (buttons.length == 2) {

            canvas.drawLine(popWidth / 2, top, popWidth / 2, popHeight, paint);

            for (int i = 0; i < buttons.length; i++) {
                PromptButton button = buttons[i];
                if (button.isFocus()) {
                    paint.reset();
                    paint.setAntiAlias(true);
                    paint.setColor(button.getFocusBacColor());
                    paint.setStyle(Paint.Style.FILL);
//                        paint.setAlpha(120);
                    canvas.drawRect(width * i, top + 1, width * (i + 1), top + 1 + height - pxRound, paint);
                    if (i == 0) {
                        canvas.drawCircle(pxRound, top + height - pxRound, pxRound, paint);
                        canvas.drawRect(pxRound, top + height - pxRound, width * (i + 1), top + height, paint);
                    } else if (i == 1) {
                        canvas.drawCircle(width * 2 - pxRound, top + height - pxRound, pxRound, paint);
                        canvas.drawRect(width, top + height - pxRound, width * 2 - pxRound, top + height, paint);
                    }
                }
                String buttonText = button.getText();
                paint.reset();
                paint.setColor(button.getTextColor());
                paint.setTextSize(ViewUtils.dip2px(button.getTextSize()));
                paint.setAntiAlias(true);
                paint.getTextBounds(buttonText, 0, buttonText.length(), textRect);

                button.setRect(new RectF(transLeft + i * width, transTop + top,
                        transLeft + i * width + width, transTop + top + height));
                canvas.drawText(buttonText, width / 2 - textRect.width() / 2 + i * width,
                        top + textRect.height() / 2 + height / 2, paint);
            }

        }
//        canvas.translate(popWidth / 2 - width, pxPad);
    }

    private void drawTips(Canvas canvas) {
        int pxPad = ViewUtils.dip2px(builder.padding);
        int pxRound = ViewUtils.dip2px(builder.round);

        width = ViewUtils.dip2px(builder.dialogidth);
        height = ViewUtils.dip2px(builder.btnHeight);

        //计算开始位置
        float popWidth = Math.max(ViewUtils.dip2px(100), textRect.width() + pxPad * 2);
        float popHeight = textRect.height() + 3 * pxPad + height * 2;
        float transTop = canvasHeight / 2 - popHeight / 2;
        float transLeft = canvasWidth / 2 - popWidth / 2;
        canvas.translate(transLeft, transTop);//移动原点

        if (!TextUtils.isEmpty(builder.text)) {
            //绘制文本
            paint.reset();
            paint.setColor(builder.textColor);
            paint.setTextSize(ViewUtils.dip2px(builder.textSize));
            paint.setAntiAlias(true);
            float top = pxPad * 2 + height * 2 + textRect.height();
            float left = popWidth / 2 - textRect.width() / 2;
            canvas.drawText(builder.text, left, top, paint);
        }

        //绘制背景
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(builder.roundColor);
        paint.setAlpha(builder.roundAlpha);
        if (roundTouchRect == null) {
            roundTouchRect = new RectF();
        }
        roundTouchRect.set(transLeft, transTop, transLeft + popWidth, transTop + popHeight);
        if (roundRect == null) {
            roundRect = new RectF(0, 0, popWidth, popHeight);
        }
        canvas.drawRoundRect(roundRect, pxRound, pxRound, paint);
//        canvas.translate(popWidth / 2 - width, pxPad);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (currentType == PROMPT_DIALOG || currentType == PROMPT_SHEET) {

            //判断关闭
            if (builder.cancleAble //空白可关闭
                    && event.getAction() == MotionEvent.ACTION_UP //手指抬起
                    && !roundTouchRect.contains(x, y)) { //在对话区域
                promptDialog.dismiss();
            }

            if (buttons == null || buttons.length <= 0) return !builder.touchAble;
            for (final PromptButton button : buttons) {
                //判断点击区域
                if (button.getRect() != null && button.getRect().contains(x, y)) {
                    //点击变色
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        button.setFocus(true);
                        invalidate();
                    }

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        button.setFocus(false);
                        invalidate();
                        if (button.isDismissAfterClick()) {
                            promptDialog.dismiss();
                        }
                        if (button.getListener() != null) {
                            if (button.isDelyClick()) {
                                postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        button.getListener().onClick(button);
                                    }
                                }, button.getDelyTime());
                            } else {
                                button.getListener().onClick(button);
                            }
                            return true;
                        }
                    }
                }
            }
        } else if (currentType == PROMPT_AD) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if ((drawableClose != null && drawableClose.getBounds()
                        .contains((int) event.getX() - transX, (int) event.getY() - transY)) || builder.cancleAble) {
                    promptDialog.dismiss();
                } else if (getDrawable() !=
                        null && getDrawable().getBounds().contains((int) event.getX() - transX, (int) event.getY() - transY)) {
                    promptDialog.onAdClick();
                    promptDialog.dismiss();
                }

            }
        }
        return !builder.touchAble;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (adBitmap != null) adBitmap.recycle();

        adBitmap = null;

        if (animator != null) animator.cancel();
        animator = null;
        buttons = null;
        promptDialog.onDetach();
        currentType = PROMPT_NONE;
    }

    void showSomthingAlert(PromptButton... button) {
        this.buttons = button;
        if (buttons == null) {
            showLoading();
        } else if (buttons.length > 2) {
            sheetHeight = ViewUtils.dip2px(1.5f * builder.sheetCellPad + builder.sheetCellHeight * buttons.length);
            showSomthing(PROMPT_SHEET);
            startSheet();
        } else showSomthing(PROMPT_DIALOG);
    }

    private void showSomthing(int currentType) {
        this.currentType = currentType;
        endAnimator();
        if (builder.icon > 0) setImageDrawable(getResources().getDrawable(builder.icon));
        if (max != null) {
            max.setRotate(0, width, height);
            setImageMatrix(max);
        }
        invalidate();
    }

    public void showLoading() {
        if (builder.isClose) {
            showSomthing(PROMPT_TIPS);
        } else {
            currentType = PROMPT_LOADING;
            if (builder.icon > 0) setImageDrawable(getResources().getDrawable(builder.icon));
            start();
        }
    }

    public void showAd() {
        this.currentType = PROMPT_AD;
        endAnimator();
    }

    /**
     * loading start
     */
    private void start() {
        if (max == null || animator == null) {
            max = new Matrix();
            animator = ValueAnimator.ofInt(0, 12);
            animator.setDuration(12 * 80);
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(Integer.MAX_VALUE);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float degrees = 30 * (Integer) valueAnimator.getAnimatedValue();
                    max.setRotate(degrees, width, height);
                    setImageMatrix(max);
                }
            });
        }
        if (!animator.isRunning())
            animator.start();
    }

    /**
     * 停止旋转
     */
    private void endAnimator() {
        if (animator != null && animator.isRunning()) {
            animator.end();
        }
    }


    /**
     * 底部Sheet 动画
     */
    private void startSheet() {
        ValueAnimator bottomToTopAnim = ObjectAnimator.ofFloat(0, 1);
        bottomToTopAnim.setDuration(promptDialog.viewAnimDuration);
        bottomToTopAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float value = (Float) valueAnimator.getAnimatedValue();
                bottomHeight = sheetHeight * value;
                invalidate();
            }
        });
        bottomToTopAnim.start();
    }

    /**
     * 底部Sheet 退出动画
     */
    public void dismissSheet() {
        if (currentType != PROMPT_SHEET) return;
        if (builder.withAnim) {
            ValueAnimator bottomToTopAnim = ObjectAnimator.ofFloat(1, 0);
            bottomToTopAnim.setDuration(promptDialog.viewAnimDuration);
            bottomToTopAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Float value = (Float) valueAnimator.getAnimatedValue();
                    bottomHeight = sheetHeight * value;
                    invalidate();
                }
            });
            bottomToTopAnim.start();
        } else invalidate();
    }

    public int getCurrentType() {
        return currentType;
    }
}
