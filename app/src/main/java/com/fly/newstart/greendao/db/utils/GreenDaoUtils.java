package com.fly.newstart.greendao.db.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fly.newstart.common.base.BaseApplication;
import com.fly.newstart.common.config.BeasConfig;
import com.fly.newstart.greendao.db.helper.DaoOpenHelper;
import com.fly.newstart.greendao.gen.DaoMaster;
import com.fly.newstart.greendao.gen.DaoSession;



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
 * 包    名 : com.fly.newstart.greendao.dp.utils
 * 作    者 : FLY
 * 创建时间 : 2017/7/17
 * <p>
 * 描述:
 */

public class GreenDaoUtils  {
    private static final String DEFAULT_DB_NAME = BeasConfig.DB_NAME;
    public static GreenDaoUtils instances;
    private static Context mContext;
    private static String DB_NAME ;


    public static DaoSession init(Context context,boolean isDebog){
        return init(context,DEFAULT_DB_NAME,isDebog);
    }

    public static DaoSession init(Context context,String dbName,boolean isDebog){
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        mContext = context.getApplicationContext();
        DB_NAME = dbName;

        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以,应该做一层封装，来实现数据库的安全升级。
        DaoOpenHelper mHelper = new DaoOpenHelper(context,DEFAULT_DB_NAME);
        SQLiteDatabase  db = mHelper.getWritableDatabase();

        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        return new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        return BaseApplication.getMainDaoSession();
    }
}
