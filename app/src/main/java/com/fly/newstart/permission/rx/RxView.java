package com.fly.newstart.permission.rx;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.shangyi.android.http.interceptor.Transformer;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * 包    名 : com.fly.newstart.permission.rx
 * 作    者 : FLY
 * 创建时间 : 2019/6/4
 * 描述:
 */
public class RxView {

    public static void textChanges(final TextView view, Consumer<String> onNext) {
        RxDisposableManage.getInstance().add(view.getContext(), Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        emitter.onNext(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                };

                view.addTextChangedListener(watcher);
                emitter.onNext(view.getText().toString());
            }
        }).compose(Transformer.<String>switchSchedulers()).subscribe(onNext));
    }

    public static void click(final View view, Consumer<View> onNext) {
        RxDisposableManage.getInstance().add(view.getContext(), Observable.create(new ObservableOnSubscribe<View>() {
            @Override
            public void subscribe(final ObservableEmitter<View> emitter) throws Exception {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        v.setEnabled(false);
                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                v.setEnabled(true);
                            }
                        }, 350);
                    }
                });
                emitter.onNext(view);
            }
        }).compose(Transformer.<View>switchSchedulers()).subscribe(onNext));
    }
}
