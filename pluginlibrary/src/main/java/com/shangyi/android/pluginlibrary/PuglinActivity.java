package com.shangyi.android.pluginlibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
 * 创建时间 : 2019/5/9
 * 描述: 连接插件的activity
 */
public class PuglinActivity extends Activity implements IPlugin {

    //判断是否是从主APP调用的，如果是不做任何操作
    //如果是系统调用的不带次参数，需要调用父类方法
    private int mFrom = FROM_INTERNAL;

    //插件的上下文，代理用
    private Activity mProxyActivity;

    @Override
    public void attach(Activity proxyActivity) {
        mProxyActivity = proxyActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mFrom = savedInstanceState.getInt(FROM_KEY);
        }

        if (mFrom == FROM_INTERNAL) super.onCreate(savedInstanceState);

    }

    public Activity getProxyActivity() {
        return mProxyActivity;
    }

    @Override
    public void setContentView(int layoutResID) {
        if (mFrom == FROM_INTERNAL) {
            super.setContentView(layoutResID);
        } else if (mProxyActivity != null) {
            mProxyActivity.setContentView(layoutResID);
        } else {
            Log.e("PuglinActivity", "插件的上下文为空");
        }
    }

    @Override
    public void onStart() {
        if (mFrom == FROM_INTERNAL) super.onStart();
    }

    @Override
    public void onRestart() {
        if (mFrom == FROM_INTERNAL) super.onRestart();
    }

    @Override
    public void onResume() {
        if (mFrom == FROM_INTERNAL) super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFrom == FROM_INTERNAL) super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        if (mFrom == FROM_INTERNAL) super.onPause();
    }

    @Override
    public void onStop() {
        if (mFrom == FROM_INTERNAL) super.onStop();
    }

    @Override
    public void onDestroy() {
        if (mFrom == FROM_INTERNAL) super.onDestroy();
    }

}
