package com.fly.myview.loading.core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fly.myview.loading.page.Page;
import com.fly.myview.loading.page.SuccessCallback;

import java.util.List;

/**
 * 包    名 : com.fly.myview.loading.core
 * 作    者 : FLY
 * 创建时间 : 2019/7/2
 * 描述: 具体操作服务类，提供showSuccess，showCallback，showWithCoverator等方法来进行状态页回调。
 */
public class LoadingService<T> {

    private LoadingLayout loadingLayout;
    private Convertor<T> convertor;

    /**
     * 在LoadingService的构造方法中根据target等信息创建Success视图，并且生成LoadingLayout，相当于LoadingGentleman每次注册都会创建一个LoadingLayout。
     *
     * @param convertor
     * @param targetContext
     * @param onReloadListener
     * @param builder
     */
    LoadingService(Convertor<T> convertor, TargetContext targetContext, Page.OnReloadListener onReloadListener,
                   LoadingSir.Builder builder) {
        this.convertor = convertor;
        Context context = targetContext.getContext();
        View oldContent = targetContext.getOldContent();
        ViewGroup.LayoutParams oldLayoutParams = oldContent.getLayoutParams();
        loadingLayout = new LoadingLayout(context, onReloadListener);
        loadingLayout.setupSuccessLayout(new SuccessCallback(oldContent, context,
                onReloadListener));
        if (targetContext.getParentView() != null) {
            targetContext.getParentView().addView(loadingLayout, targetContext.getChildIndex(), oldLayoutParams);
        }
        initPage(builder);
    }

    /**
     * 初始化显示界面
     *
     * @param builder
     */
    private void initPage(LoadingSir.Builder builder) {
        List<Page> pages = builder.getPages();
        Class<? extends Page> defalutCallback = builder.getDefaultPage();
        if (pages != null && pages.size() > 0) {
            for (Page page : pages) {
                loadingLayout.setupPage(page);
            }
        }
        if (defalutCallback != null) {
            loadingLayout.showPage(defalutCallback);
        }
    }


    /* LoadingService的三个回调方法最终调用的都是 loadingLayout.showPage(page);，用于显示不同界面 */

    /**
     * 显示成功页面
     */
    public void showSuccess() {
        loadingLayout.showPage(SuccessCallback.class);
    }

    /**
     * 显示对应状态页面
     *
     * @param page
     */
    public void showPage(Class<? extends Page> page) {
        loadingLayout.showPage(page);
    }

    /**
     * 用转换器显示
     *
     * @param t
     */
    public void showWithConvertor(T t) {
        if (convertor == null) {
            throw new IllegalArgumentException("You haven't set the Convertor.");
        }
        loadingLayout.showPage(convertor.map(t));
    }

    /**
     * 获取加载布局实体
     *
     * @return
     */
    public LoadingLayout getLoadLayout() {
        return loadingLayout;
    }

    /**
     * 获取当前页面
     *
     * @return
     */
    public Class<? extends Page> getCurrentPage() {
        return loadingLayout.getCurrentPage();
    }

    /**
     * 如果要保持工具栏碎片化，请获取rootview
     *
     * @param context
     * @param rootView
     * @param titleView
     * @return
     */
    public LinearLayout getTitleLoadLayout(Context context, ViewGroup rootView, View titleView) {
        LinearLayout newRootView = new LinearLayout(context);
        newRootView.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        newRootView.setLayoutParams(layoutParams);
        rootView.removeView(titleView);
        newRootView.addView(titleView);
        newRootView.addView(loadingLayout, layoutParams);
        return newRootView;
    }

    /**
     * 动态修改展示界面
     *
     * @param page      要修改的界面（布局、事件）
     * @param transport 界面View处理
     * @return
     */
    public LoadingService<T> setPage(Class<? extends Page> page, Transport transport) {
        loadingLayout.setPage(page, transport);
        return this;
    }
}
