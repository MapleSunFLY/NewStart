package com.fly.newstart.permission.requestresult;

import android.app.Activity;
import android.support.annotation.NonNull;

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
 * 包    名 : com.fly.newstart.permission.requestresult
 * 作    者 : FLY
 * 创建时间 : 2019/4/8
 * 描述: 申请回调处理
 */
public interface IRequestPermissionsResult {
    /**
     * 处理权限请求结果
     *
     * @param activity
     * @param permissions  请求的权限数组
     * @param grantResults 权限请求结果数组
     * @return 处理权限结果如果全部通过，返回true；否则，引导用户去授权页面
     */
    boolean doRequestPermissionsResult(Activity activity, @NonNull String[] permissions, @NonNull int[] grantResults, OnPermissionClickListener onPermissionClickListener);
}
