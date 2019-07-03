package com.fly.myview.loading.page;

import android.content.Context;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 包    名 : com.fly.myview.loading.callback
 * 作    者 : FLY
 * 创建时间 : 2019/7/2
 * 描述: 状态页抽象类，抽象自定义布局和自定义点击事件两个方法留给子类实现。
 */
public abstract class Page implements Serializable {

    private View rootView;
    private Context context;
    private OnReloadListener onReloadListener;
    private boolean successViewVisible;

    public Page() {
    }


    protected Page(View view, Context context, OnReloadListener onReloadListener) {
        this.rootView = view;
        this.context = context;
        this.onReloadListener = onReloadListener;
    }

    /**
     * 设置界面信息
     * @param view
     * @param context
     * @param onReloadListener
     * @return
     */
    public Page setPage(View view, Context context, OnReloadListener onReloadListener) {
        this.rootView = view;
        this.context = context;
        this.onReloadListener = onReloadListener;
        return this;
    }

    public View getRootView() {
        int resId = onCreateView();
        if (resId == 0 && rootView != null) {
            return rootView;
        }

        if (onBuildView(context) != null) {
            rootView = onBuildView(context);
        }

        if (rootView == null) {
            rootView = View.inflate(context, onCreateView(), null);
        }
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReloadEvent(context, rootView)) {
                    return;
                }
                if (onReloadListener != null) {
                    onReloadListener.onReload(v);
                }
            }
        });
        onViewCreate(context, rootView);
        return rootView;
    }

    /**
     * 创建view
     *
     * @param context
     * @return
     */
    protected View onBuildView(Context context) {
        return null;
    }

    /**
     * 如果返回true，则在附加回调视图时，SuccessView将可见。
     *
     * @return
     */
    public boolean getSuccessVisible() {
        return successViewVisible;
    }

    /**
     * 设置是否成功
     *
     * @param visible
     */
    protected void setSuccessVisible(boolean visible) {
        this.successViewVisible = visible;
    }

    /**
     * 创建视图
     *
     * @return
     */
    protected abstract int onCreateView();

    /**
     * 在@link onCreateView（）之后立即调用
     * 用于界面创建后操作
     *
     * @param context
     * @param view
     */
    protected void onViewCreate(Context context, View view) {
    }

    /**
     * 重新加载事件
     *
     * @param context
     * @param view
     * @return
     */
    protected boolean onReloadEvent(Context context, View view) {
        return false;
    }

    public Page copy() {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        Object obj = null;
        try {
            oos = new ObjectOutputStream(bao);
            oos.writeObject(this);
            oos.close();
            ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Page) obj;
    }

    /**
     * 获取根视图
     *
     * @return
     */
    public View obtainRootView() {
        if (rootView == null) {
            rootView = View.inflate(context, onCreateView(), null);
        }
        return rootView;
    }

    /**
     * 当回调的rootview附加到其LoadingLayout时调用。
     *
     * @param context
     * @param view
     */
    public void onAttach(Context context, View view) {
    }

    /**
     * 当回调的rootview从其LoadingLayout中移除时调用。
     */
    public void onDetach() {
    }

    /**
     * 重新加载监听
     */
    public interface OnReloadListener extends Serializable {
        void onReload(View v);
    }
}
