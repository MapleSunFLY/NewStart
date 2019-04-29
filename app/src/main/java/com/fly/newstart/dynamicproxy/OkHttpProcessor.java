package com.fly.newstart.dynamicproxy;

import android.os.Handler;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
 * 描述: okhttp具体实现
 */
public class OkHttpProcessor implements IHttpProcessor {

    private OkHttpClient mOkHttpClient;

    private Handler mHandler;

    public OkHttpProcessor() {
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler();
    }

    @Override
    public void post(String url, Map<String, Object> param, final ICallBack callBack) {
        RequestBody requestBody = appendBody(param);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String str = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess(str);
                        }
                    });
                }
            }
        });
    }

    private RequestBody appendBody(Map<String, Object> param) {
        FormBody.Builder body = new FormBody.Builder();
        if (param == null || param.isEmpty()) {
            return body.build();
        }
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            body.add(entry.getKey(), entry.getValue().toString());
        }
        return body.build();
    }
}
