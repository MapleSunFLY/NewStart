package com.shangyi.android.pluginlibrary;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

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
 * 描述: 插件apk的管理
 */
public class PluginManager {

    private static PluginManager instance;

    private PluginManager() {
    }

    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }

    private Context mContext;

    private PluginApk mPluginApk;

    public void init(Context context) {
        this.mContext = context;
    }

    /**
     * 检测是否调用初始化方法
     */
    private void checkInitialize() {
        if (mContext == null) {
            throw new ExceptionInInitializerError("请先调用 PluginManager.getInstance().init(this) 初始化！");
        }
    }

    //加载插件apk，服务器下载保存路径
    public void loadApk(String apkPath) {
        checkInitialize();
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);

        if (packageInfo == null) {
            Log.d("PluginManager", "loadApk: 加载插件apk失败");
            return;
        }

        DexClassLoader dexClassLoader = createDexClassLoader(apkPath);
        AssetManager assetManager = createAssetManager(apkPath);
        Resources resources = createResources(assetManager);
        mPluginApk = new PluginApk(packageInfo, dexClassLoader, resources);
    }

    public PluginApk getPluginApk() {
        return mPluginApk;
    }

    //创建访问插件apk的DexClassLoader对象加载插件代码
    private DexClassLoader createDexClassLoader(String apkPath) {
        File file = mContext.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath, file.getAbsolutePath(), null, mContext.getClassLoader());
    }

    //访问插件apk的Aseetmanger对象
    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(assetManager, apkPath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //创建访问插件apk的Resource对象加载资源文件
    private Resources createResources(AssetManager assetManager) {
        Resources resources = mContext.getResources();
        return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
    }

}
