package com.fly.newstart.load;

import android.os.Handler;
import android.os.Looper;

import com.fly.myview.loading.core.LoadingService;
import com.fly.myview.loading.page.Page;

/**
 * 包    名 : com.fly.newstart.load
 * 作    者 : FLY
 * 创建时间 : 2019/7/2
 * 描述: 模拟网络请求
 */
public class PostUtil {

    public static void postCallbackDelayed(final LoadingService loadService, final Class<? extends Page> clazz) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                loadService.showPage(clazz);
            }
        }, 5000);
    }


    public static void postSuccessDelayed(final LoadingService loadingService) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingService.showSuccess();
            }
        }, 5000);
    }
}
