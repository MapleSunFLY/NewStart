package com.fly.newstart.permission.rxpermissions;

/**
 * 包    名 : com.fly.newstart.permission.rxpermissions
 * 作    者 : FLY
 * 创建时间 : 2019/6/4
 * 描述: 权限回调
 */
public interface PermissionCallback {

    /**
     * 授予
     *
     * @param permissionName 权限名称
     */
    void onGranted(String permissionName);


    /**
     * 拒绝下次提醒
     *
     * @param permissionName 权限名称
     */
    void onRefuse(String permissionName);

    /**
     * 拒绝并不再提醒
     *
     * @param permissionName 权限名称
     */
    void onNoMoreReminder(String permissionName);

}
