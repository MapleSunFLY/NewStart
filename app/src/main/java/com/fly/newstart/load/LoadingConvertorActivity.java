package com.fly.newstart.load;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fly.myview.loading.core.Convertor;
import com.fly.myview.loading.core.LoadingService;
import com.fly.myview.loading.core.LoadingSir;
import com.fly.myview.loading.page.Page;
import com.fly.myview.loading.page.SuccessCallback;
import com.fly.newstart.R;
import com.fly.newstart.load.page.EmptyPage;
import com.fly.newstart.load.page.ErrorPage;
import com.fly.newstart.load.page.LoadingPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoadingConvertorActivity extends AppCompatActivity {

    private LoadingService loadService;
    private HttpResult mHttpResult = new HttpResult(new Random().nextInt(2), new ArrayList<>());
    private static final int SUCCESS_CODE = 0x00;
    private static final int ERROR_CODE = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_convertor);
        LoadingSir loadSir = new LoadingSir.Builder()
                .addPage(new LoadingPage())
                .addPage(new ErrorPage())
                .addPage(new EmptyPage())
                .setDefaultPage(LoadingPage.class)
                .build();
        loadService = loadSir.register(this, new Page.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService.showPage(LoadingPage.class);
                PostUtil.postCallbackDelayed(loadService, SuccessCallback.class);
            }
        }, new Convertor<HttpResult>() {
            @Override
            public Class<? extends Page> map(HttpResult httpResult) {
                Class<? extends Page> resultCode;
                switch (httpResult.getResultCode()) {
                    //成功回调
                    case SUCCESS_CODE:
                        if (httpResult.getData().size() == 0) {
                            resultCode = EmptyPage.class;
                        } else {
                            resultCode = SuccessCallback.class;
                        }
                        break;
                    case ERROR_CODE:
                        resultCode = ErrorPage.class;
                        break;
                    default:
                        resultCode = ErrorPage.class;
                }
                return resultCode;
            }
        });

        //模拟请求
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadService.showWithConvertor(mHttpResult);
            }
        }, 5000);
    }

    private class HttpResult {
        private int resultCode;
        private List<Object> data;

        HttpResult(int resultCode, List<Object> data) {
            this.resultCode = resultCode;
            this.data = data;
        }

        int getResultCode() {
            return resultCode;
        }

        public List<Object> getData() {
            return data;
        }
    }
}
