package com.fly.newstart.myokhttp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;
import com.fly.newstart.myokhttp.utils.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HttpActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        OkHttpUtils.getInstance();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_http_get:
                OkHttpUtils.doGet("http://192.168.1.151:8080/user", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "onFailure: "+call.isExecuted() );
                        Log.e(TAG, "onFailure: "+e.toString() );
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()){
                            Log.e(TAG, "onResponse: "+response.body().string());
                        }
                        if(response.body()!=null){
                            response.body().close();
                        }
                    }
                });
                break;
            default:
                Log.d(TAG, "onClick: "+view.getId());
                break;
        }
    }
}
