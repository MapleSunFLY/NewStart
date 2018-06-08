package com.fly.newstart.Request;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.fly.newstart.R;
import com.fly.newstart.Request.BaseRequest;
import com.fly.newstart.common.base.BaseApplication;
import com.fly.newstart.utils.GsonUtils;
import com.fly.newstart.utils.LogUtil;
import com.fly.newstart.viewmode.ResponseJson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.newstart.utils
 * 作    者 : FLY
 * 创建时间 : 2018/6/7
 * <p>
 * 描述:
 */

public class RxNet {

    private static String code;

    public static Observable<String> newRequest(final BaseRequest request) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                request.setBeginTime(System.currentTimeMillis());
                String url = request.getUrl();
                if (TextUtils.isEmpty(url)) {
                    throw new RuntimeException("http url is empty !");
                }
                LogUtil.print(request.getBeginTime() + " url:" + url);
                String s = submitRequest(subscriber, request);
                if (!request.isHtml() && TextUtils.isEmpty(s)) {
                    ResponseJson responseJson = new ResponseJson();
                    responseJson.code = "";
                    responseJson.msg = BaseApplication.getAppContext().getString(R.string.text_network_error)+(TextUtils.isEmpty(code)?"":code);
                    s = GsonUtils.toJson(responseJson);
                } else if (request.isHtml() && TextUtils.isEmpty(s)) {
                    s = "";
                }
                request.setEndTime(System.currentTimeMillis());
                subscriber.onNext(s);
                subscriber.onCompleted();
            }
        });
    }

    private synchronized static String submitRequest(Subscriber subscriber, BaseRequest request) {
        OkHttpClient client = request.getOKHttpBuilder().build();
        String result = null;
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        Call call = null;
        try {
            okhttp3.Request r = request.getOKHttpRequest();
            if (r == null) {
                return null;
            }
            final Call call1 = client.newCall(r);
            call = call1;
            subscriber.add(unSubscribeInUiThread(new Action0() {
                @Override
                public void call() {
                    if (call1 != null)
                        call1.cancel();
                }
            }));
            Response response = call.execute();
            if (response.code() == 200) {
                is = response.body().byteStream();
                if (is != null) {
                    bos = new ByteArrayOutputStream();
                    byte b[] = new byte[1024];
                    int i = 0;
                    while ((i = is.read(b, 0, b.length)) != -1) {
                        bos.write(b, 0, i);
                    }
                    result = new String(bos.toByteArray(), "UTF-8");
                } else {
                    code = "（404）";
                    result = null;
                }
            } else {
                code = "（" + response.code() + "）";
                LogUtil.print("error code:" + response.code() + " url:" + request.getUrl());
            }
        } catch (MalformedURLException e) {
            code = "（400）";
            e.printStackTrace();
            LogUtil.print("MalformedURLException" + request.getUrl());
        } catch (SocketTimeoutException e) {
            code = "（400）";
            e.printStackTrace();
            LogUtil.print("SocketTimeoutException" + request.getUrl());
        } catch (IOException e) {
            code = "（404/500）";
            e.printStackTrace();
            LogUtil.print("IOException" + request.getUrl());
        } catch (Exception e) {
            code = "（500）";
            e.printStackTrace();
            LogUtil.print("IOException" + request.getUrl());
        } finally {
            try {
                if (call != null) {
                    call.cancel();
                }
                if (is != null) {
                    is.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
            }
        }
        return result;
    }

    public static Subscription unSubscribeInUiThread(final Action0 unSubscribe) {
        return Subscriptions.create(new Action0() {
            @Override
            public void call() {
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    unSubscribe.call();
                } else {
                    final Scheduler.Worker inner = AndroidSchedulers.mainThread().createWorker();
                    inner.schedule(new Action0() {
                        @Override
                        public void call() {
                            unSubscribe.call();
                            inner.unsubscribe();
                        }
                    });
                }
            }
        });
    }
}
