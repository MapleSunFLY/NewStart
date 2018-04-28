package com.fly.newstart.okgo.callback;

import com.fly.newstart.utils.LoggerUtils;
import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;

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
 * 创建时间 : 2017/8/8
 * <p>
 * 描述:
 */

public abstract class JsonCallback<T> extends AbsCallback<T> {

    //泛型设置
    private Type type;

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        if (response.isSuccessful()){
            Gson gson = new Gson();
            String text = response.body().string();
            LoggerUtils.printResult(text);
            T t = gson.fromJson(text,type);
            return t;
        }
        return null;
    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        String text = response.getException().getMessage();
        LoggerUtils.printResult(text);
        //TODO setError
    }

    public JsonCallback setType(Type type){
        this.type = type;
        return this;
    }
}
