package com.fly.newstart.permission.rxpermissions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;

import com.fly.newstart.permission.rx.RxDisposableManage;
import com.fly.newstart.permission.rxpermissions.interf.PermissionCallback;
import com.fly.newstart.permission.rxpermissions.interf.PermissionClickListener;
import com.shangyi.android.utils.ListUtils;
import com.shangyi.android.utils.LogUtils;

import java.util.List;

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

    public void requestEach(FragmentActivity activity, PermissionCallback permissionCallback, String... permissions) {
        this.requestEach(activity, permissionCallback, false, null, permissions);
    }

    public void requestEach(FragmentActivity activity, PermissionCallback permissionCallback,
                            PermissionClickListener permissionClickListener, String... permissions) {
        this.requestEach(activity, permissionCallback, true, permissionClickListener, permissions);
    }

    public void requestEach(final FragmentActivity activity, final PermissionCallback permissionCallback,
                            final boolean isShowDialog, final PermissionClickListener permissionClickListener,
                            final String... permissions) {
        RxDisposableManage.getInstance().add(activity, new RxPermissions(activity).requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    private List<String> granteds = ListUtils.newArrayList();
                    private List<String> refuses = ListUtils.newArrayList();
                    private List<String> noMoreReminders = ListUtils.newArrayList();
                    private int index = 0;

                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            LogUtils.i(TAG, permission.name + " is granted.");
                            granteds.add(permission.name);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            LogUtils.i(TAG, permission.name + " is denied. More info should be provided.");
                            refuses.add(permission.name);
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            LogUtils.i(TAG, permission.name + " is denied.");
                            noMoreReminders.add(permission.name);
                        }
                        index++;
                        if (index == permissions.length) {
                            permissionCallback.onComplete(granteds, refuses, noMoreReminders);
                            if ((refuses.size() + noMoreReminders.size() > 0) && isShowDialog) {
                                String permissionName = PermissionInfo.getInstance().getPermissionNames(refuses);
                                permissionName += PermissionInfo.getInstance().getPermissionNames(noMoreReminders);
                                openAppDetails(activity, permissionName, permissionClickListener);
                            }
                        }
                    }
                }));
    }


    /**
     * 打开APP详情页面，引导用户去设置权限
     *
     * @param activity        页面对象
     * @param permissionNames 权限名称（如是多个，使用\n分割）
     */
    public void openAppDetails(final Activity activity, String permissionNames, final PermissionClickListener permissionClickListener) {
        StringBuilder sb = new StringBuilder();
        sb.append(PermissionInfo.getInstance().getPermissionTip1());
        sb.append(permissionNames);
        sb.append(PermissionInfo.getInstance().getPermissionTip2());
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(sb.toString());
        builder.setPositiveButton(PermissionInfo.getInstance().getPermissionDialogPositiveButton(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                activity.startActivity(intent);
                if (permissionClickListener != null) permissionClickListener.onClick(true);
            }
        });
        builder.setNegativeButton(PermissionInfo.getInstance().getPermissionDialogNegativeButton(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (permissionClickListener != null) permissionClickListener.onClick(false);
            }
        });
        builder.show();
    }
}
