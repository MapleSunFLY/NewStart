package com.fly.newstart.permission.rxpermissions.interf;

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
 * 包    名 : iwangzhe.paizhaocaiqie.permission.requestresult
 * 作    者 : FLY
 * 创建时间 : 2019/4/8
 * 描述: 开启权限点击回调
 */
public interface PermissionClickListener {

    /**
     * 是否去手动打开权限
     *
     * @param isPositive
     */
    void onClick(boolean isPositive);
}
