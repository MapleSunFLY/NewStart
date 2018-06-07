package com.fly.newstart.viewmode;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.fly.newstart.common.base.BaseActivity;
import com.fly.newstart.common.base.BaseApplication;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.newstart.viewmode
 * 作    者 : FLY
 * 创建时间 : 2018/5/25
 * <p>
 * 描述:
 */

public abstract class BaseViewModel extends ViewModel {
    protected final CompositeSubscription subscription = new CompositeSubscription();
    protected MutableLiveData<RestErrorInfo> errorLiveData = new MutableLiveData<>();
    protected BaseActivity mActivity;

    public MutableLiveData<RestErrorInfo> getErrorLiveData() {
        return errorLiveData;
    }

    public void setActivity(BaseActivity mActivity) {
        this.mActivity = mActivity;
    }

    public <T> void submitRequest(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError, Action0 onComplete) {
        if (onComplete == null) {
            subscription.add(observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError));
        } else {
            subscription.add(observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError, onComplete));
        }
    }

    public <T> void submitRequest(Observable<T> observable, final Action1<? super T> onNext, final Action1<Throwable> onError) {
        submitRequest(observable, onNext, onError, null);
    }

    public <T> void submitRequest(Observable<T> observable, final Action1<? super T> onNext, final Action0 onComplete) {
        submitRequest(observable, onNext, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                errorLiveData.postValue(getError(throwable));
            }
        }, onComplete);
    }

    public <T> void submitRequest(Observable<T> observable, Action1<? super T> onNext) {
        submitRequest(observable, onNext, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                errorLiveData.postValue(getError(throwable));
            }
        }, null);
    }

    protected void sendError(ResponseJson r) {
        if (r == null) return;
        errorLiveData.postValue(getError(r));
        if (mActivity!=null)mActivity.setProgressVisible(false);
    }
    protected void sendError(String r) {
        if (r == null) return;
        errorLiveData.postValue(new RestErrorInfo(r));
    }
    protected void sendError(RestErrorInfo r) {
        if (r == null) return;
        errorLiveData.postValue(r);
    }
    protected void sendError(MutableLiveData<RestErrorInfo> errorLiveData,RestErrorInfo r) {
        if (r == null) return;
        errorLiveData.postValue(r);
    }
    protected void sendError(MutableLiveData<RestErrorInfo> errorLiveData, ResponseJson r) {
        if (r == null) return;
        errorLiveData.postValue(getError(r));
    }

    protected void sendError(int r) {
        sendError(errorLiveData, r);
    }

    protected void sendError(MutableLiveData<RestErrorInfo> errorLiveData, int r) {
        if (r <= 0) return;
        errorLiveData.postValue(getError(r));
    }
    protected void sendError(MutableLiveData<RestErrorInfo> errorLiveData,String error) {
        if (TextUtils.isEmpty(error)) return;
        errorLiveData.postValue(new RestErrorInfo(error));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        onDestroy();
    }

    public void onDestroy() {
        subscription.clear();
    }

    public void cancelTask() {
        subscription.clear();
    }

    public RestErrorInfo getError(Throwable throwable) {
        if (throwable instanceof HttpErrorException) {
            return new RestErrorInfo(((HttpErrorException) throwable).getResponseJson());
        }
        if (throwable != null) return new RestErrorInfo(throwable.getMessage());
        return new RestErrorInfo("");
    }

    public RestErrorInfo getError(ResponseJson r) {
        return new RestErrorInfo(r);
    }

    public RestErrorInfo getError(int r) {
        return new RestErrorInfo(BaseApplication.getInstance().getString(r));
    }

    public RestErrorInfo getErrorString(String r) {
        return new RestErrorInfo(r);
    }


    public static String getStringValue(String s) {
        return s == null ? "" : s;
    }

    public static String getString(Integer s) {
        return BaseApplication.getInstance().getString(s);
    }


}

