package com.fly.newstart.dynamicproxy;

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
 * 包    名 : com.fly.newstart.dynamicproxy
 * 作    者 : FLY
 * 创建时间 : 2019/4/25
 * 描述: 请求回调接口
 */
public interface ICallBack {

    void onSuccess(String result);

    void onFailure();
}
