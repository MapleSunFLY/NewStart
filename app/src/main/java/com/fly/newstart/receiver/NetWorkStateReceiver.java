package com.fly.newstart.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.fly.newstart.R;

/**
 * 包    名 : com.fly.newstart.receiver
 * 作    者 : FLY
 * 创建时间 : 2017/11/14
 * <p>
 * 描述: 网络变化的广播
 */

public class NetWorkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("FLY110","onReceive :");
    }

}