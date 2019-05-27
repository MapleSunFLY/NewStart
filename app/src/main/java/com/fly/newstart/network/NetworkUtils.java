package com.fly.newstart.network;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fly.newstart.common.base.BaseApplication;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.newstart.network
 * 作    者 : FLY
 * 创建时间 : 2019/5/27
 * 描述: 网络工具类
 */
public class NetworkUtils {

    /**
     * 没有网络
     */
    public static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    public static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    public static final int NETWORK_WIFI = 1;

    @SuppressLint("MissingPerMissino")
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            //返回所有网络信息
            NetworkInfo[] infos = connectivityManager.getAllNetworkInfo();
            if (infos != null && infos.length > 0) {
                for (NetworkInfo info : infos) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @SuppressLint("MissingPerMissino")
    public int getNetWorkState(Context context) {
        //得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //如果网络连接，判断该网络类型
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;//wifi
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;//mobile
            }
        } else {
            //网络异常
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }


    public static void openSeting(Activity context, int requestCode) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        context.startActivityForResult(intent, requestCode);
    }
}
