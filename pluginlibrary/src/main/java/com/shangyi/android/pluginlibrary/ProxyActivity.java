package com.shangyi.android.pluginlibrary;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * 描述: 代理activity
 */
public class ProxyActivity extends Activity {

    public static final String CLASS_NAME = "className";

    private String mClassName;

    private PluginApk mPluginApk;

    private IPlugin mIPlugin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getIntent().getStringExtra(CLASS_NAME);
        mPluginApk = PluginManager.getInstance().getPluginApk();

        launchPlaginActivity();

    }

    //启动activity
    private void launchPlaginActivity() {
        if (mPluginApk == null) {
            Log.e("ProxyActivity: ", "加载不了插件apk文件");
            return;
        }

        try {
            Class<?> clazz = mPluginApk.mDexClassLoader.loadClass(mClassName);
            // 实例化Activity 注意：这里的activity是没有生命周期，也没有上下文环境的
            Object object = clazz.newInstance();
            if (object instanceof IPlugin) {
                mIPlugin = (IPlugin) object;
                mIPlugin.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt(IPlugin.FROM_KEY, IPlugin.FROM_EXTERNAL);
                mIPlugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return mPluginApk != null ? mPluginApk.mResources : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return mPluginApk != null ? mPluginApk.mAssetManager : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPluginApk != null ? mPluginApk.mDexClassLoader : super.getClassLoader();
    }
}
