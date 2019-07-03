package com.fly.myview.loading.core;


import android.support.annotation.NonNull;

import com.fly.myview.loading.LoadingSirUtils;
import com.fly.myview.loading.page.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 包    名 : com.fly.myview.loading.core
 * 作    者 : FLY
 * 创建时间 : 2019/7/2
 * 描述: 提供单例模式获取全局唯一实例，内部保存配置信息，根据配置创建LoadingService。
 * LoadingSir提供beginBuilder()...commit()来设置全局配置。
 */
public class LoadingSir {

    private static volatile LoadingSir loadingSir;
    private Builder builder;

    public static LoadingSir getDefault() {
        if (loadingSir == null) {
            synchronized (LoadingSir.class) {
                if (loadingSir == null) {
                    loadingSir = new LoadingSir();
                }
            }
        }
        return loadingSir;
    }

    private LoadingSir() {
        this.builder = new Builder();
    }

    private void setBuilder(@NonNull Builder builder) {
        this.builder = builder;
    }

    private LoadingSir(Builder builder) {
        this.builder = builder;
    }

    public LoadingService register(@NonNull Object target) {
        return register(target, null, null);
    }

    public LoadingService register(Object target, Page.OnReloadListener onReloadListener) {
        return register(target, onReloadListener, null);
    }

    /**
     * LoadingSir注册后返回的是LoadingService
     *
     * @param target           目标上下文
     * @param onReloadListener 重新加载监听
     * @param convertor        转换器
     * @param <T>
     * @return
     */
    public <T> LoadingService register(Object target, Page.OnReloadListener onReloadListener, Convertor<T>
            convertor) {
        TargetContext targetContext = LoadingSirUtils.getTargetContext(target);
        return new LoadingService<>(convertor, targetContext, onReloadListener, builder);
    }

    public static Builder beginBuilder() {
        return new Builder();
    }

    /**
     * Builder主要提供添加状态页，和设置默认状态页的方法
     */
    public static class Builder {
        private List<Page> pages = new ArrayList<>();
        private Class<? extends Page> defaultPage;

        /**
         * 添加显示界面
         *
         * @param page
         * @return
         */
        public Builder addPage(@NonNull Page page) {
            pages.add(page);
            return this;
        }

        public Builder setDefaultPage(@NonNull Class<? extends Page> defaultPage) {
            this.defaultPage = defaultPage;
            return this;
        }

        List<Page> getPages() {
            return pages;
        }

        Class<? extends Page> getDefaultPage() {
            return defaultPage;
        }

        public void commit() {
            getDefault().setBuilder(this);
        }

        public LoadingSir build() {
            return new LoadingSir(this);
        }
    }
}
