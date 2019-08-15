package com.fly.newstart.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;
import com.fly.newstart.eventbus.bus.EventBus;
import com.fly.newstart.eventbus.bus.Subscribe;

public class Bus1Activity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus1);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void msg(InfoEvent infoEvent) {
        Log.e(TAG, "msg: " + infoEvent.getInfo());
    }

    public void onClick(View view) {
        startActivity(new Intent(this, Bus2Activity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
