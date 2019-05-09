package com.shangyi.android.pluginlibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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
 * 包    名 : com.shangyi.android.pluginlibrary
 * 作    者 : FLY
 * 创建时间 : 2019/5/8
 * 描述: 判断调用方式 管理Activity生命周期
 */
public interface IPlugin {

    String FROM_KEY = "FromKey"; //判断调用的传参key

    int FROM_INTERNAL = 0; // 从内部调用 手机系统调用
    int FROM_EXTERNAL = 1; // 从外边调用 主app调用

    //绑定，代理Activity，传入插件上下文
    void attach(Activity proxyActivity);

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onRestart();

    void onResume();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onPause();

    void onStop();

    void onDestroy();
}
