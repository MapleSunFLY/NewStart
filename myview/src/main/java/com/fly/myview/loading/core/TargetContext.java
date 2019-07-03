package com.fly.myview.loading.core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 包    名 : com.fly.myview.loading.core
 * 作    者 : FLY
 * 创建时间 : 2019/7/2
 * 描述: 目标上下文
 */
public class TargetContext {

    private Context context;
    private ViewGroup parentView;
    private View oldContent;
    private int childIndex;

    public TargetContext(Context context, ViewGroup parentView, View oldContent, int childIndex) {
        this.context = context;
        this.parentView = parentView;
        this.oldContent = oldContent;
        this.childIndex = childIndex;
    }

    public Context getContext() {
        return context;
    }

    View getOldContent() {
        return oldContent;
    }

    int getChildIndex() {
        return childIndex;
    }

    ViewGroup getParentView() {
        return parentView;
    }
}
