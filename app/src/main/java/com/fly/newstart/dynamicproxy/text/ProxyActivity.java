package com.fly.newstart.dynamicproxy.text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fly.newstart.MainActivity;
import com.fly.newstart.R;
import com.fly.newstart.dynamicproxy.HttpCallBack;
import com.fly.newstart.dynamicproxy.HttpHelper;
import com.fly.newstart.myokhttp.HttpActivity;
import com.fly.newstart.utils.LogUtil;
import com.fly.newstart.webview.WebViewActivity;
import com.shangyi.android.http.*;
import com.shangyi.android.utils.ListUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
    }

    public void onClick(View view) {
        String url = "http://apis.juhe.cn/simpleWeather/query";
        Map<String, Object> param = new HashMap<>();
        param.put("city", "温州");
        param.put("key", "cb965e30b7bde3862929a1512247364b");
        HttpHelper.getInstance().post(url, param, new HttpCallBack<ResultEntity<WeatherEntity>>() {
            @Override
            public void onsuccess(ResultEntity<WeatherEntity> objResult) {
                Log.d("FLY110", "onsuccess: " + objResult.reason);
                if (objResult.result != null) {
                    Log.d("FLY110", "result: " + objResult.result.getCity());
                }
            }
        });
    }
}
