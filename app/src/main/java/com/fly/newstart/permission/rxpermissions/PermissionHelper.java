package com.fly.newstart.permission.rxpermissions;

import android.support.v4.app.FragmentActivity;

import com.fly.newstart.permission.rx.RxDisposableManage;
import com.shangyi.android.utils.LogUtils;

import io.reactivex.functions.Consumer;

import static com.fly.newstart.permission.rxpermissions.RxPermissions.TAG;

/**
 * 包    名 : com.fly.newstart.permission.rxpermissions
 * 作    者 : FLY
 * 创建时间 : 2019/6/4
 * 描述: 权限获取的帮助类
 */
public class PermissionHelper {
    private static PermissionHelper permissionHelper;

    public static PermissionHelper getInstance() {
        if (permissionHelper == null) {
            synchronized (PermissionHelper.class) {
                if (permissionHelper == null) {
                    permissionHelper = new PermissionHelper();
                }
            }
        }
        return permissionHelper;
    }

    private PermissionHelper() {
    }

    public void requestEach(FragmentActivity activity, final PermissionCallback permissionCallback, String... permissions) {
        RxDisposableManage.getInstance().add(activity, new RxPermissions(activity).requestEach(permissions)
                .subscribe(new Consumer<Permission>() {

                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            LogUtils.i(TAG, permission.name + " is granted.");
                            permissionCallback.onGranted(permission.name);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            LogUtils.i(TAG, permission.name + " is denied. More info should be provided.");
                            permissionCallback.onNoMoreReminder(permission.name);
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            LogUtils.i(TAG, permission.name + " is denied.");
                            permissionCallback.onRefuse(permission.name);
                        }
                    }
                }));
    }
}
