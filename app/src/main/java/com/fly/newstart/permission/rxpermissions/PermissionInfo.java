package com.fly.newstart.permission.rxpermissions;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
 * 包    名 : com.fly.newstart.permission
 * 作    者 : FLY
 * 创建时间 : 2019/4/8
 * 描述: 权限帮助
 */
public class PermissionInfo {
    private String PermissionTip1;
    private String PermissionTip2;
    private String PermissionDialogPositiveButton;
    private String PermissionDialogNegativeButton;
    private HashMap<String, String> permissions;
    private static PermissionInfo permissionInfo;

    public static PermissionInfo getInstance() {
        if (permissionInfo == null) {
            permissionInfo = new PermissionInfo();
        }
        return permissionInfo;
    }

    private PermissionInfo() {
        initPermissions();
    }

    public HashMap<String, String> getPermissions() {
        if (permissions == null) {
            initPermissions();
        }
        return permissions;
    }

    private void initPermissions() {
        PermissionTip1 = "亲爱的用户 \n\n软件部分功能需要请求您的手机权限，请允许以下权限：\n\n";//权限提醒
        PermissionTip2 = "\n请到 “应用信息 -> 权限” 中授予！";//权限提醒
        PermissionDialogPositiveButton = "去手动授权";
        PermissionDialogNegativeButton = "取消";
        permissions = new HashMap<>();
        //联系人/通讯录权限
        permissions.put("android.permission.WRITE_CONTACTS", "--通讯录/联系人");
        permissions.put("android.permission.GET_ACCOUNTS", "--通讯录/联系人");
        permissions.put("android.permission.READ_CONTACTS", "--通讯录/联系人");
        //电话权限
        permissions.put("android.permission.READ_CALL_LOG", "--电话");
        permissions.put("android.permission.READ_PHONE_STATE", "--电话");
        permissions.put("android.permission.CALL_PHONE", "--电话");
        permissions.put("android.permission.WRITE_CALL_LOG", "--电话");
        permissions.put("android.permission.USE_SIP", "--电话");
        permissions.put("android.permission.PROCESS_OUTGOING_CALLS", "--电话");
        permissions.put("com.android.voicemail.permission.ADD_VOICEMAIL", "--电话");
        //日历权限
        permissions.put("android.permission.READ_CALENDAR", "--日历");
        permissions.put("android.permission.WRITE_CALENDAR", "--日历");
        //相机拍照权限
        permissions.put("android.permission.CAMERA", "--相机/拍照");
        //传感器权限
        permissions.put("android.permission.BODY_SENSORS", "--传感器");
        //定位权限
        permissions.put("android.permission.ACCESS_FINE_LOCATION", "--定位");
        permissions.put("android.permission.ACCESS_COARSE_LOCATION", "--定位");
        //文件存取
        permissions.put("android.permission.READ_EXTERNAL_STORAGE", "--文件存储");
        permissions.put("android.permission.WRITE_EXTERNAL_STORAGE", "--文件存储");
        //音视频、录音权限
        permissions.put("android.permission.RECORD_AUDIO", "--音视频/录音");
        //短信权限
        permissions.put("android.permission.READ_SMS", "--短信");
        permissions.put("android.permission.RECEIVE_WAP_PUSH", "--短信");
        permissions.put("android.permission.RECEIVE_MMS", "--短信");
        permissions.put("android.permission.RECEIVE_SMS", "--短信");
        permissions.put("android.permission.SEND_SMS", "--短信");
        permissions.put("android.permission.READ_CELL_BROADCASTS", "--短信");
    }



    /**
     * 获得权限名称集合（去重）
     *
     * @param permission 权限数组
     * @return 权限名称
     */
    public String getPermissionNames(List<String> permission) {
        if (permission == null || permission.size() == 0) {
            return "\n";
        }
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<>();
        HashMap<String, String> permissions = getPermissions();
        for (int i = 0; i < permission.size(); i++) {
            String name = permissions.get(permission.get(i));
            if (name != null && !list.contains(name)) {
                list.add(name);
                sb.append(name);
                sb.append("\n");
            }
        }
        return sb.toString();
    }


    public PermissionInfo addPermission(String permissionName, String permissionDescribe) {
        if (!TextUtils.isEmpty(permissionName) && permissions != null) {
            permissions.put(permissionName, permissionDescribe);
        }
        return this;
    }

    public PermissionInfo setPermissionTip1(String permissionTip1) {
        PermissionTip1 = permissionTip1;
        return this;
    }

    public PermissionInfo setPermissionTip2(String permissionTip2) {
        PermissionTip2 = permissionTip2;
        return this;
    }

    public PermissionInfo setPermissionDialogPositiveButton(String permissionDialogPositiveButton) {
        PermissionDialogPositiveButton = permissionDialogPositiveButton;
        return this;
    }

    public PermissionInfo setPermissionDialogNegativeButton(String permissionDialogNegativeButton) {
        PermissionDialogNegativeButton = permissionDialogNegativeButton;
        return this;
    }

    public String getPermissionTip1() {
        return PermissionTip1;
    }

    public String getPermissionTip2() {
        return PermissionTip2;
    }

    public String getPermissionDialogPositiveButton() {
        return PermissionDialogPositiveButton;
    }

    public String getPermissionDialogNegativeButton() {
        return PermissionDialogNegativeButton;
    }
}

