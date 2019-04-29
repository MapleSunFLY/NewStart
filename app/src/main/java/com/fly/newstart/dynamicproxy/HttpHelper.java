package com.fly.newstart.dynamicproxy;

import com.fly.newstart.common.base.BaseApplication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

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
 * 描述: 请求帮助类，连接插件实现 ,隔离了框架与使用
 */
public class HttpHelper implements IHttpProcessor {
    private static HttpHelper instance;
    private static IHttpProcessor mIHttpProcessor;

    private HttpHelper() {}

    public static HttpHelper getInstance() {
        synchronized (HttpHelper.class) {
            if (instance == null) {
                instance = new HttpHelper();
                //更换框架，更换实践
                mIHttpProcessor = new OkHttpProcessor();
               // mIHttpProcessor = new VolleyProcessor(BaseApplication.getAppContext());
            }
        }
        return instance;
    }

    @Override
    public void post(String url, Map<String, Object> param, ICallBack callBack) {
        if (mIHttpProcessor == null) return;
        mIHttpProcessor.post(appendParams(url, param), param, callBack);
    }

    private String appendParams(String url, Map<String, Object> params) {
        if (params == null || params.isEmpty()) return url;

        StringBuffer urlBuffer = new StringBuffer(url);
        if (urlBuffer.indexOf("?") <= 0) {
            urlBuffer.append("?");
        } else if (!urlBuffer.toString().endsWith("?")) {
            urlBuffer.append("&");
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            urlBuffer.append("&").append(entry.getKey()).append("=").append(encode(entry.getValue().toString()));
        }
        return urlBuffer.toString();
    }

    private String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
