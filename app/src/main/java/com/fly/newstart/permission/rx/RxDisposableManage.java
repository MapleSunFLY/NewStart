package com.fly.newstart.permission.rx;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 包    名 : com.fly.newstart.permission.rx
 * 作    者 : FLY
 * 创建时间 : 2019/6/4
 * 描述:  rxjava 很容易内存很容易泄漏 ，对 rx Disposable 进行统一管理
 */
public class RxDisposableManage {

    private HashMap<Object, CompositeDisposable> mMaps;

    private static RxDisposableManage disposableManage;

    public static RxDisposableManage getInstance() {
        if (disposableManage == null) {
            synchronized (RxDisposableManage.class) {
                if (disposableManage == null) {
                    disposableManage = new RxDisposableManage();
                }
            }
        }
        return disposableManage;
    }

    private RxDisposableManage() {
        mMaps = new HashMap<>();
    }

    public void add(Object tag, Disposable disposable) {
        if (null == tag) {
            return;
        }
        //tag下的一组或一个请求，用来处理一个页面的所以请求或者某个请求
        //设置一个相同的tag就行就可以取消当前页面所有请求或者某个请求了
        CompositeDisposable compositeDisposable = mMaps.get(tag);
        if (compositeDisposable == null) {
            CompositeDisposable compositeDisposableNew = new CompositeDisposable();
            compositeDisposableNew.add(disposable);
            mMaps.put(tag, compositeDisposableNew);
        } else {
            compositeDisposable.add(disposable);
        }
    }

    public void remove(Object tag) {
        if (null == tag) {
            return;
        }
        if (!mMaps.isEmpty()) {
            mMaps.remove(tag);
        }
    }

    public void cancel(Object tag) {
        if (null == tag) {
            return;
        }
        if (mMaps.isEmpty()) {
            return;
        }
        if (null == mMaps.get(tag)) {
            return;
        }
        if (!mMaps.get(tag).isDisposed()) {
            mMaps.get(tag).dispose();
            mMaps.remove(tag);
        }
    }

    public void cancel(Object... tags) {
        if (null == tags) {
            return;
        }
        for (Object tag : tags) {
            cancel(tag);
        }
    }

    public void cancelAll() {
        if (mMaps.isEmpty()) {
            return;
        }
        Iterator<Map.Entry<Object, CompositeDisposable>> it = mMaps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, CompositeDisposable> entry = it.next();
            CompositeDisposable disposable = entry.getValue();
            //如果直接使用map的remove方法会报这个错误java.util.ConcurrentModificationException
            //所以要使用迭代器的方法remove
            if (null != disposable) {
                if (!disposable.isDisposed()) {
                    disposable.dispose();
                    it.remove();
                }
            }
        }
    }
}

