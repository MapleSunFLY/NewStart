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

public abstract class MyCallback<T> implements CallBack<T>{
    @Override
    public void onStart(Request<T, ? extends Request> request) {

    }

    @Override
    public void onSuccess(Response<ResponseJson<T>> response) {

    }

    @Override
    public void onError(Response<ResponseJson<T>> response) {

    }

    @Override
    public void onFinish() {

    }
}
