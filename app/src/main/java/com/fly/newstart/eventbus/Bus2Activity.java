package com.fly.newstart.eventbus;

import android.os.Bundle;
import android.view.View;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;
import com.fly.newstart.eventbus.bus.EventBus;

public class Bus2Activity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus2);
    }

    public void onClick(View view) {
        EventBus.getDefault().post(new InfoEvent("111111111111111111"));
    }
}
