package com.fly.newstart.rx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxActivity extends BaseActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        initView();
        rxDemo();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.tv_rx);
    }

    private void rxDemo() {
        String[] name = {"aa", "bb", "cc", "dd"};
        /*Observable.from(name).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "call: "+s);
            }
        });*/
        Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("as");
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        textView.setText(s);
                    }
                });
    }


}
