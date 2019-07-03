package com.fly.myview.loading.page;

import android.content.Context;
import android.view.View;

/**
 * 包    名 : com.fly.myview.loading.callback
 * 作    者 : FLY
 * 创建时间 : 2019/7/2
 * 描述: 成功回调
 */
public class SuccessCallback extends Page {

    public SuccessCallback(View view, Context context, OnReloadListener onReloadListener) {
        super(view, context, onReloadListener);
    }

    @Override
    protected int onCreateView() {
        return 0;
    }

    public void hide() {
        obtainRootView().setVisibility(View.INVISIBLE);
    }

    public void show() {
        obtainRootView().setVisibility(View.VISIBLE);
    }

    public void showWithCallback(boolean successVisible) {
        obtainRootView().setVisibility(successVisible ? View.VISIBLE : View.INVISIBLE);
    }
}
