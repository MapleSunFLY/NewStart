package com.fly.newstart.common.base;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.fly.newstart.greendao.db.utils.GreenDaoUtils;
import com.fly.newstart.greendao.gen.DaoSession;
import com.fly.newstart.utils.HttpUtils;
import com.fly.newstart.utils.Utils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$$$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.newstart.webview
 * 作    者 : FLY
 * 创建时间 : 2017/7/28
 * <p>
 * 描述: 程序的入口,各种初始化操作
 */

public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    private static BaseApplication instance;

    /**
     * 获取到主线程的handler
     */
    private static Handler mMainThreadHandler = null;
    /**
     * 获取到主线程的looper
     */
    private static Looper mMainThreadLooper = null;
    /**
     * 获取到主线程
     */
    private static Thread mMainThead = null;
    /**
     * 获取到主线程的id
     */
    private static int mMainTheadId;

    /**
     * 数据库操作类
     */
    private static DaoSession sDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        this.mMainThreadHandler = new Handler();
        this.mMainThreadLooper = getMainLooper();
        this.mMainThead = Thread.currentThread();
        this.mMainTheadId = android.os.Process.myTid();

        //init utils
        Utils.init(this);

        //tenxun X5 init
        initTbs();

        //数据库初始化
        sDaoSession = GreenDaoUtils.init(this,true );

        //okgo 初始化
        HttpUtils.init(this);
    }



    private void initTbs() {

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }

    /**
     * 获取数据库的DaoSession
     * @return
     */
    public static DaoSession getMainDaoSession(){
        return sDaoSession;
    }

    /**
     * 获取主线程的Handler
     *
     * @return
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的Looper
     *
     * @return
     */
    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    /**
     * 获取主线程
     *
     * @return
     */
    public static Thread getMainThread() {
        return mMainThead;
    }

    /**
     * 获取主线程ID
     *
     * @return
     */
    public static int getMainThreadId() {
        return mMainTheadId;
    }

    /**
     * 获取主线程的 上下文
     *
     * @return
     */
    public static BaseApplication getAppContext() {
        return instance;
    }
}
