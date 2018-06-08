package com.fly.newstart.Request;

import android.text.TextUtils;

import com.fly.newstart.R;
import com.fly.newstart.Request.sign.Signer;
import com.fly.newstart.common.base.BaseApplication;
import com.fly.newstart.okgo.ResponseJson;
import com.fly.newstart.utils.GsonUtils;
import com.fly.newstart.utils.SysTimeUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.newstart.Request
 * 作    者 : FLY
 * 创建时间 : 2018/6/7
 * <p>
 * 描述: 网络请求
 */

public class BaseRequest<T> {

    private Map<String, Object> mapBody = new HashMap<String, Object>();
    protected Type toJsonType;
    private Object bodyObj;
    protected String headUrl;
    private String url;
    private boolean isAddUrlPara = true;
    private RestMethodEnum restMethodEnum = RestMethodEnum.POST;
    private long beginTime = 0, endTime = 0;
    private boolean isHttps = false;
    protected Object userId;
    private long connectTime = 15 * 1000;
    private long readTime = 20 * 1000;
    protected Map<String, Object> paraPublic = new HashMap<String, Object>();
    private boolean isHtml=false;
    private RequestBody requestBody;

    private static boolean isDebug = false;

    public BaseRequest() {
        headUrl = BaseApplication.getAppContext().getString(R.string.defualt_http_url);
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean isDebug) {
        BaseRequest.isDebug = isDebug;
    }

    public static <T> BaseRequest<T> builder() {
        BaseRequest<T> request = new BaseRequest<T>();
        request.https(false);
        request.setDefaultConnectTime();
        return request;
    }

    public BaseRequest<T> setDefaultConnectTime() {
        return connectTime(BaseApplication.getAppContext().getResources().getInteger(R.integer.time_connect_out))
                .readTime(BaseApplication.getAppContext().getResources().getInteger(R.integer.time_read_out));
    }

    /**
     * 是否走HTTPS通道
     */
    public BaseRequest<T> https(boolean isHttps) {
        this.isHttps = isHttps;
        return this;
    }

    /**
     * 设置访问网络的连接时间
     */
    public BaseRequest<T> connectTime(long connectTime) {
        this.connectTime = connectTime;
        return this;
    }

    /**
     * 设置访问网络的读取时间
     */
    public BaseRequest<T> readTime(long readTime) {
        this.readTime = readTime;
        return this;
    }

    /**
     * 设置访问head
     * 默认为缓存库里面的
     */
    public BaseRequest<T> headUrl(String headUrl) {
        this.headUrl = headUrl;
        return this;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public BaseRequest<T> setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    /**
     * 转换JSON的对象 参考goson
     */
    public BaseRequest<T> setToJsonType(Type toJsonType) {
        this.toJsonType = toJsonType;
        return this;
    }

    /**
     * 网络访问url
     */
    public BaseRequest<T> url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 网络访问url
     *
     * @param url res api
     */
    public BaseRequest<T> url(int url) {
        this.url = BaseApplication.getAppContext().getString(url);
        return this;
    }

    public BaseRequest<T> addPublicPara(String key, Object obj) {
        if (paraPublic.containsKey(key)) paraPublic.remove(key);
        paraPublic.put(key, obj);
        return this;
    }

    public BaseRequest<T> addPublicParaMap(HashMap<String, Object> keyMap) {
        for (Map.Entry<String, Object> entry : keyMap.entrySet()) {
            addPublicPara(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * userId
     */
    public BaseRequest<T> userId(Object userId) {
        this.userId = userId;
        return this;
    }

    /**
     * 添加网络访问参数
     */
    public BaseRequest<T> addBody(Object obj) {
        bodyObj = obj;
        return this;
    }

    /**
     * 当HTML 为true时 不返回rest错误
     * @param html
     * @return
     */
    public BaseRequest<T> html(boolean html) {
        isHtml = html;
        return this;
    }

    public RestMethodEnum getRestMethodEnum() {
        return restMethodEnum;
    }

    /**
     * 访问方式
     */
    public BaseRequest<T> restMethod(RestMethodEnum restMethodEnum) {
        this.restMethodEnum = restMethodEnum;
        return this;
    }

    public String getBodyObj() {
        try {
            if (bodyObj != null) return bodyObj.toString();
        } catch (Exception e) {
        }
        if (mapBody == null || mapBody.size() == 0) return null;
        try {
            return GsonUtils.toJson(mapBody);
        } catch (Exception e) {
            return null;
        }
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public boolean isHttps() {
        return isHttps;
    }

    /**
     * 返回网络访问对象 没开线程
     */
    public Observable<T> requestJson() {
        Observable<T> observable = RxNet.newRequest(this)
                .map(new Func1<String, T>() {
                    @Override
                    public T call(String s) {
                        return GsonUtils.fromJson(s, toJsonType);
                    }
                });
        observable = observable.map(new Func1<T, T>() {
            @Override
            public T call(T e) {
                if (e instanceof ResponseJson) {
                    ResponseJson responseJson = (ResponseJson) e;
                    if (responseJson.isOk()) {
                        SysTimeUtil.initTime(BaseApplication.getAppContext(), responseJson.ts);
                    }
                    responseJson.execTime = endTime - beginTime;
                }
                return e;
            }
        });
        return observable;
    }

    public String getUrl() {
        if (isAddUrlPara) {
            String s = getHeadUrl() + url;
            isAddUrlPara = false;
            isHtml = false;
            if (s.indexOf("?") > -1) {
                return url = s + "&" + Signer.toSign(getParaPublic(), restMethodEnum, getBodyObj());
            } else {
                return url = s + "?" + Signer.toSign(getParaPublic(), restMethodEnum, getBodyObj());
            }
        } else return url;
    }

    protected Map<String, Object> getParaPublic() {
        if (userId != null && !TextUtils.isEmpty(userId.toString())) {
            if (paraPublic.containsKey("userId")) paraPublic.remove("userId");
            paraPublic.put("userId", userId);
        }
        return paraPublic;
    }

    public OkHttpClient.Builder getOKHttpBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(connectTime, TimeUnit.MILLISECONDS)
                .writeTimeout(connectTime, TimeUnit.MILLISECONDS)
                .readTimeout(readTime, TimeUnit.MILLISECONDS);
        if (isHttps()) {
            try {
                builder.sslSocketFactory(getCertificates(BaseApplication.getAppContext().getAssets().open("cert.cer")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return builder;
    }

    public okhttp3.Request getOKHttpRequest() {
        okhttp3.Request request = null;
        if (restMethodEnum == RestMethodEnum.POST) {
            RequestBody requestBody = new FormBody.Builder().add("data", getBodyObj()).build();
            request = new okhttp3.Request.Builder().url(getUrl())
                    .post(requestBody).build();
        } else if (restMethodEnum == RestMethodEnum.GET) {
            request = new okhttp3.Request.Builder().url(getUrl()).get().build();
        }
        return request;
    }


    private static SSLSocketFactory getCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
            return sslContext.getSocketFactory();

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("not exists ssl");
    }
}
