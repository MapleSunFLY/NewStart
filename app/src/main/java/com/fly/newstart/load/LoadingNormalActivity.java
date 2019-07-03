package com.fly.newstart.load;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fly.myview.loading.core.LoadingService;
import com.fly.myview.loading.core.LoadingSir;
import com.fly.myview.loading.core.Transport;
import com.fly.myview.loading.page.Page;
import com.fly.newstart.R;
import com.fly.newstart.load.page.EmptyPage;
import com.fly.newstart.load.page.LoadingPage;

public class LoadingNormalActivity extends AppCompatActivity {

    private LoadingService loadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_normal);
        loadService = LoadingSir.getDefault().register(this, new Page.OnReloadListener() {
            @Override
            public void onReload(View v) {
                //点击重新加载
                loadService.showPage(LoadingPage.class);

                //请求成功
                PostUtil.postSuccessDelayed(loadService);
            }
        }).setPage(EmptyPage.class, new Transport() {
            @Override
            public void order(Context context, View view) {
                TextView mTvEmpty = (TextView) view.findViewById(R.id.tv_empty);
                mTvEmpty.setText("fine, no data. You must fill it!");
            }
        });

        //模拟请求数据为空
        PostUtil.postCallbackDelayed(loadService, EmptyPage.class);
    }
}
