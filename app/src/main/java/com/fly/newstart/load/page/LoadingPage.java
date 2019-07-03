package com.fly.newstart.load.page;

import android.content.Context;
import android.view.View;

import com.fly.myview.loading.page.Page;
import com.fly.newstart.R;

/**
 * 包    名 : com.fly.newstart.load.page
 * 作    者 : FLY
 * 创建时间 : 2019/7/2
 * 描述: 加载中界面
 */
public class LoadingPage extends Page {

    @Override
    protected int onCreateView() {
        return R.layout.loading_page_loading_layout;
    }

    @Override
    public boolean getSuccessVisible() {
        return super.getSuccessVisible();
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
