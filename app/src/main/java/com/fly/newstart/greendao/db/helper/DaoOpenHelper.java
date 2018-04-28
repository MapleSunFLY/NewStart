package com.fly.newstart.greendao.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fly.newstart.greendao.gen.DaoMaster;

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
 * 包    名 : com.fly.newstart.greendao.db.helper
 * 作    者 : FLY
 * 创建时间 : 2017/8/6
 * <p>
 * 描述: greenDao帮助类
 */

public class DaoOpenHelper extends DaoMaster.OpenHelper {
    public DaoOpenHelper(Context context, String name) {
        super(context, name);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);

        switch (oldVersion){
            case 1:
                //不能先删除表
//                TestEntityDao.dropTable(db, true);

//                TestEntityDao.createTable(db, true);

                // 加入新字段 score
//                db.execSQL("ALTER TABLE 'TEST_ENTITY' ADD 'SCORE' TEXT;");
                break;
        }
    }
}
