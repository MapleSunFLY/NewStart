package com.fly.newstart.common.base;

import android.support.v7.app.AppCompatActivity;

/**
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
 * 包    名 : com.fly.newstart.common.base
 * 作    者 : FLY
 * 创建时间 : 2017/8/7
 * <p>
 * 描述: Activity的基类
 */

public class BaseActivity extends AppCompatActivity {
    /**
     * tag,用于打印log,如L.i(TAG,"xxxxxx")
     */
    protected final String TAG = "FLY."+this.getClass().getSimpleName();
}
