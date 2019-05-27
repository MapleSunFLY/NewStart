package com.fly.newstart.network.type;

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
 * 包    名 : com.fly.newstart.network.type
 * 作    者 : FLY
 * 创建时间 : 2019/5/27
 * 描述: 网络类型
 */
public enum  NetType {
    /**
     * 只要有网，包括WIFI /GPRS
     */
    AUTO,

    /**
     * WIFI网络0
     */
    WIFI,

    /**
     * 主要是PC / 笔记本电脑 / PDA设备
     */
    CMNET,

    /**
     * 手机上网
     */
    CMWAP,

    /**
     * 没有网络
     */
    NONE;
}
