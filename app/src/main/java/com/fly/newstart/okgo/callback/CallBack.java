package com.fly.newstart.okgo.callback;

import com.fly.newstart.okgo.ResponseJson;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(O)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$$$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.newstart.okgo.callback
 * 作    者 : FLY
 * 创建时间 : 2017/8/9
 * <p>
 * 描述:
 */

public interface CallBack<T> {
    /** 请求网络开始前，UI线程 */
    void onStart(Request<T, ? extends Request> request);

    /** 对返回数据进行操作的回调， UI线程 */
    void onSuccess(Response<ResponseJson<T>> response);

    /** 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程 */
    void onError(Response<ResponseJson<T>> response);

    /** 请求网络结束后，UI线程 */
    void onFinish();

}
