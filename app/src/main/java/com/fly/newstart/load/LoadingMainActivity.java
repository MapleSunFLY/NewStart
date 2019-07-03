package com.fly.newstart.load;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fly.myview.loading.core.LoadingSir;
import com.fly.newstart.R;
import com.fly.newstart.load.page.EmptyPage;
import com.fly.newstart.load.page.ErrorPage;
import com.fly.newstart.load.page.LoadingPage;

public class LoadingMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_main);

        //初始化，显示界面
        LoadingSir.beginBuilder()
                .addPage(new LoadingPage())
                .addPage(new ErrorPage())
                .addPage(new EmptyPage())
                .setDefaultPage(LoadingPage.class)
                .commit();
    }

    public void inActivity(View view) {
        startActivity(new Intent(this, LoadingNormalActivity.class));
    }

    public void showPlaceholder(View view) {
        startActivity(new Intent(this, LoadingPlaceholderActivity.class));
    }

    public void showDefault(View view) {
        startActivity(new Intent(this, LoadingDefaultPageActivity.class));
    }

    public void showConvertor(View view) {
        startActivity(new Intent(this, LoadingConvertorActivity.class));
    }
}
