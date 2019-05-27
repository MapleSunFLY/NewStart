package com.fly.newstart.network.test;

import android.os.Bundle;
import android.widget.TextView;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;
import com.fly.newstart.network.annotation.Network;
import com.fly.newstart.network.type.NetType;
import com.shangyi.android.utils.LogUtils;

public class NetworkActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
    }

    @Network(netType = NetType.AUTO)
    public void network(NetType netType) {
        LogUtils.d(TAG,"当前网络状况：" + netType.name());
        ((TextView) findViewById(R.id.tvNetwork)).setText("当前网络状况：" + netType.name());
    }
}
