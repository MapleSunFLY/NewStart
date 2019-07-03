package com.fly.myview.loading.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

import com.fly.myview.loading.LoadingSirUtils;
import com.fly.myview.loading.page.Page;
import com.fly.myview.loading.page.SuccessCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 包    名 : com.fly.myview.loading.core
 * 作    者 : FLY
 * 创建时间 : 2019/7/2
 * 描述: 最终显示在用户面前的视图View，替换了原布局，是LoadingService直接操作对象，要显示的状态页的视图会被添加到LoadingLayout上。
 */
public class LoadingLayout extends FrameLayout {

    private Map<Class<? extends Page>, Page> pageMap = new HashMap<>();
    private Context context;
    private Page.OnReloadListener onReloadListener;
    private Class<? extends Page> prePage;//准备
    private Class<? extends Page> curPage;//当前
    private static final int PAGE_CUSTOM_INDEX = 1;

    public LoadingLayout(@NonNull Context context) {
        super(context);
    }

    public LoadingLayout(@NonNull Context context, Page.OnReloadListener onReloadListener) {
        this(context);
        this.context = context;
        this.onReloadListener = onReloadListener;
    }

    /**
     * 设置成功的界面
     *
     * @param page
     */
    public void setupSuccessLayout(Page page) {
        addStatePage(page);
        View successView = page.getRootView();
        successView.setVisibility(View.GONE);
        addView(successView);
        curPage = SuccessCallback.class;
    }

    /**
     * 设置状态界面
     *
     * @param page
     */
    public void setupPage(Page page) {
        Page clonePage = page.copy();
        clonePage.setPage(null, context, onReloadListener);
        addStatePage(clonePage);
    }

    /**
     * 设置界面
     *
     * @param page
     * @param transport
     */
    public void setPage(Class<? extends Page> page, Transport transport) {
        if (transport == null) {
            return;
        }
        checkPageExist(page);
        transport.order(context, pageMap.get(page).obtainRootView());
    }


    /**
     * 添加不同状态界面
     *
     * @param page
     */
    public void addStatePage(Page page) {
        if (!pageMap.containsKey(page.getClass())) {
            pageMap.put(page.getClass(), page);
        }
    }

    /**
     * 显示界面
     *
     * @param page
     */
    public void showPage(final Class<? extends Page> page) {
        checkPageExist(page);
        if (LoadingSirUtils.isMainThread()) {
            showPageView(page);
        } else {
            postToMainThread(page);
        }
    }

    /**
     * 获取当前页面
     *
     * @return
     */
    public Class<? extends Page> getCurrentPage() {
        return curPage;
    }

    private void postToMainThread(final Class<? extends Page> status) {
        post(new Runnable() {
            @Override
            public void run() {
                showPageView(status);
            }
        });
    }

    private void showPageView(Class<? extends Page> status) {
        if (prePage != null) {
            if (prePage == status) {
                return;
            }
            pageMap.get(prePage).onDetach();
        }
        if (getChildCount() > 1) {
            removeViewAt(PAGE_CUSTOM_INDEX);
        }

        for (Class key : pageMap.keySet()) {
            if (key == status) {
                SuccessCallback successCallback = (SuccessCallback) pageMap.get(SuccessCallback.class);
                if (key == SuccessCallback.class) {
                    successCallback.show();
                } else {
                    successCallback.showWithCallback(pageMap.get(key).getSuccessVisible());
                    View rootView = pageMap.get(key).getRootView();
                    addView(rootView);
                    pageMap.get(key).onAttach(context, rootView);
                }
                prePage = status;
            }
        }
        curPage = status;
    }

    private void checkPageExist(Class<? extends Page> page) {
        if (!pageMap.containsKey(page)) {
            throw new IllegalArgumentException(String.format("The Page (%s) is nonexistent.", page
                    .getSimpleName()));
        }
    }
}
