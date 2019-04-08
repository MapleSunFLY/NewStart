package com.fly.newstart.permission.requestresult;

import android.app.Activity;
import android.content.pm.PackageManager;
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
 * 描述:  处理权限申请的结果，如果未允许，不做处理
 */
public class RequestPermissionsResult implements IRequestPermissionsResult {
    private static RequestPermissionsResult requestPermissionsResult;

    public static RequestPermissionsResult getInstance() {
        if (requestPermissionsResult == null) {
            requestPermissionsResult = new RequestPermissionsResult();
        }
        return requestPermissionsResult;
    }

    @Override
    public boolean doRequestPermissionsResult(Activity activity, @NonNull String[] permissions, @NonNull int[] grantResults, OnPermissionClickListener onPermissionClickListener) {
        boolean isAllGranted = true;
        // 判断是否所有的权限都已经授予了
        for (int grant : grantResults) {
            if (grant != PackageManager.PERMISSION_GRANTED) {
                isAllGranted = false;
                break;
            }
        }

        //已全部授权
        if (isAllGranted) {
            return true;
        } else {
            //什么也不做
        }
        return false;
    }
}

