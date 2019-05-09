package com.shangyi.android.pluginlibrary;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

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
 * 描述: 插件apk信息的实体对象
 */
public class PluginApk {

    public PackageInfo mPackageInfo;//apk的解析

    public DexClassLoader mDexClassLoader;//dex靠PathClassLoader加载

    public Resources mResources;// 图片以及xml资源靠Resource加载

    public AssetManager mAssetManager;//用于支持创建Resources

    public PluginApk(PackageInfo mPackageInfo, DexClassLoader mDexClassLoader, Resources mResources) {
        this.mPackageInfo = mPackageInfo;
        this.mDexClassLoader = mDexClassLoader;
        this.mResources = mResources;
        this.mAssetManager = mResources.getAssets();
    }
}
