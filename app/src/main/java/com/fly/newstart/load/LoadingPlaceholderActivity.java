package com.fly.newstart.load;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fly.myview.loading.core.LoadingService;
import com.fly.myview.loading.core.LoadingSir;
import com.fly.myview.loading.page.Page;
import com.fly.newstart.R;
import com.fly.newstart.load.page.PlaceholderPage;

public class LoadingPlaceholderActivity extends AppCompatActivity {

    private LoadingService loadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_placeholder);

        LoadingSir loadSir = new LoadingSir.Builder()
                .addPage(new PlaceholderPage())
                .setDefaultPage(PlaceholderPage.class)
                .build();
        loadService = loadSir.register(this, new Page.OnReloadListener() {
            @Override
            public void onReload(View v) {
                //do retry logic...
            }
        });
        PostUtil.postSuccessDelayed(loadService);
    }
}
