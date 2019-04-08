package com.fly.newstart.permission.request;

import android.app.Activity;

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
 * 包    名 : com.fly.newstart.permission.request
 * 作    者 : FLY
 * 创建时间 : 2019/4/8
 * 描述: 申请权限
 */
public interface IRequestPermissions {
    /**
     * 请求权限
     * @param activity 上下文
     * @param permissions 权限集合
     * @param resultCode 请求码
     * @return 如果权限已全部允许，返回true; 反之，请求权限，在
     */
    boolean requestPermissions(Activity activity, String[] permissions, int resultCode);
}
