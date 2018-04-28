package com.fly.myview.cloze.announcement;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.fly.myview.R;

import java.util.List;

/**
 * 版    权 ：博智信息
 * 项目名称 : 水井坊
 * 包    名 : com.fly.myview.cloze.announcement
 * 作    者 : FLY
 * 创建时间 : 2018/1/15
 * <p>
 * 描述: 公告界面
 */

public class UPMarqueeView extends ViewFlipper {
    private Context mContext;
    /**
     * 是否开启动画
     */
    private boolean isSetAnimDuration = true;
    /**
     * 时间间隔
     */
    private int interval = 3000;
    /**
     * 动画时间
     */
    private int animDuration = 100;

    private OnClickListener onClickListener;

    public UPMarqueeView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public UPMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        setFlipInterval(interval);
        if (isSetAnimDuration) {
            Animation animIn = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_in);
            animIn.setDuration(animDuration);
            setInAnimation(animIn);
            Animation animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_out);
            animOut.setDuration(animDuration);
            setOutAnimation(animOut);
        }
    }

    /**
     * 设置循环滚动的View数组
     */
    public UPMarqueeView setViews(final List<UPMarqueeViewData> datas) {
        if (datas == null || datas.size() == 0) {
            return this;
        }
        int size = datas.size();
        for (int i = 0; i < size; i += 2) {
            final int position = i;
            //根布局
            LinearLayout item = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_up_marquee, null);
            //设置监听
            item.findViewById(R.id.ll_one).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener!=null){
                        onClickListener.onClick(view,datas.get(position));
                    }
                    //Toast.makeText(mContext, "你点击了" + datas.get(position).getValue(), Toast.LENGTH_SHORT).show();
                }
            });
            //控件赋值
            ((TextView) item.findViewById(R.id.tv_img_one)).setText(datas.get(position).getTitle());
            ((TextView) item.findViewById(R.id.tv_one)).setText(datas.get(position).getValue());
            //当数据是奇数时，最后那个item仅有一项
            if (position + 1 < size) {
                item.findViewById(R.id.ll_two).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onClickListener!=null){
                            onClickListener.onClick(view,datas.get(position+1));
                        }
                        //Toast.makeText(mContext, "你点击了" + datas.get(position + 1).getValue(), Toast.LENGTH_SHORT).show();
                    }
                });
                ((TextView) item.findViewById(R.id.tv_img_two)).setText(datas.get(position + 1).getTitle());
                ((TextView) item.findViewById(R.id.tv_two)).setText(datas.get(position + 1).getValue());
            } else item.findViewById(R.id.ll_two).setVisibility(View.GONE);
            addView(item);
        }
        return this;
    }

    public boolean isSetAnimDuration() {
        return isSetAnimDuration;
    }

    public UPMarqueeView setSetAnimDuration(boolean isSetAnimDuration) {
        this.isSetAnimDuration = isSetAnimDuration;
        return this;
    }

    public int getInterval() {
        return interval;
    }

    public UPMarqueeView setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    public int getAnimDuration() {
        return animDuration;
    }

    public UPMarqueeView setAnimDuration(int animDuration) {
        this.animDuration = animDuration;
        return this;
    }

    public UPMarqueeView setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public interface OnClickListener {
        void onClick(View view,UPMarqueeViewData data);
    }

    public static class UPMarqueeViewData {

        public int id;//标示—便于转换
        private String title;//红色头
        private String value;//公告内容

        public UPMarqueeViewData() {

        }

        public UPMarqueeViewData(int id, String title, String value) {
            this.id = id;
            this.title = title;
            this.value = value;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}