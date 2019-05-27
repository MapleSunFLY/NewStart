package com.fly.newstart.network;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

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
 * 描述: 网络管理类
 */
public class NetworkManager {

    private static volatile NetworkManager instance;
    private Application application;
    private NetStateReceiver receiver;

    private NetworkManager() {
        receiver = new NetStateReceiver();
    }

    public static NetworkManager getDefault() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    public Application getApplication() {
        return application;
    }

    public void init(Application application) {
        this.application = application;

        //动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        application.registerReceiver(receiver, filter);
    }

    //注册界面监听
    public void registerObserver(Object register) {
        receiver.registerObserver(register);
    }

    //移除界面监听
    public void removeObserver(Object register) {
        receiver.removeObserver(register);
    }

    //注销广播
    public void unregisterReceiver() {
        receiver.unregisterReceiver();
    }


}
