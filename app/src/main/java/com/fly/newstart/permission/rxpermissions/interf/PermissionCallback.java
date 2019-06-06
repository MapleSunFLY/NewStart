package com.fly.newstart.permission.rxpermissions.interf;

import java.util.List;

/**
 * 包    名 : com.fly.newstart.permission.rxpermissions
 * 作    者 : FLY
 * 创建时间 : 2019/6/4
 * 描述: 权限回调
 */
public interface PermissionCallback {

    /**
     * 权限请求完成
     *
     * @param granteds        同意权限
     * @param refuses         拒绝,但下次提醒
     * @param noMoreReminders 拒绝并不再提醒
     */
    void onComplete(List<String> granteds, List<String> refuses, List<String> noMoreReminders);

}
