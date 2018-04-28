package com.fly.newstart.okgo.model;

import android.content.Context;

import com.lzy.okgo.OkGo;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(O)(OK)---/$\
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
 * 包    名 : com.fly.newstart.okgo.model
 * 作    者 : FLY
 * 创建时间 : 2017/8/7
 * <p>
 * 描述: OkGo操作的基类
 */

public class BaseModel {
    protected Context activity;

    public BaseModel(Context activity) {
        this.activity = activity;
    }

    public void cancel(){
        OkGo.getInstance().cancelTag(this);
    }
}
