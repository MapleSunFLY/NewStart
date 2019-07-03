package com.fly.newstart.load;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fly.myview.loading.core.LoadingService;
import com.fly.myview.loading.core.LoadingSir;
import com.fly.myview.loading.page.HintPage;
import com.fly.myview.loading.page.Page;
import com.fly.myview.loading.page.ProgressPage;
import com.fly.newstart.R;

public class LoadingDefaultPageActivity extends AppCompatActivity {

    private LoadingService loadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_default_page);

        ProgressPage loadingCallback = new ProgressPage.Builder()
                .setTitle("Loading", R.style.Hint_Title)
//                .setAboveSuccess(true)// 在成功视图上方附加加载视图
                .build();

        HintPage hintCallback = new HintPage.Builder()
                .setTitle("Error", R.style.Hint_Title)
                .setSubTitle("Sorry, buddy, I will try it again.")
                .setHintImg(R.mipmap.error)
                .build();

        LoadingSir loadSir = new LoadingSir.Builder()
                .addPage(loadingCallback)
                .addPage(hintCallback)
                .setDefaultPage(ProgressPage.class)
                .build();

        loadService = loadSir.register(this, new Page.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService.showPage(ProgressPage.class);
                PostUtil.postSuccessDelayed(loadService);

            }
        });
        PostUtil.postCallbackDelayed(loadService, HintPage.class);
    }
}
