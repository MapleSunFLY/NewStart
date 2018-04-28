package com.fly.newstart.rx;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
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
 * 包    名 : com.fly.newstart.rx
 * 作    者 : FLY
 * 创建时间 : 2017/8/16
 * <p>
 * 描述: RxJava 初识
 */

public class Rx {
    public Rx(){

    }

    /**
     * 观察者 回调
     */
    private void observer(){
        //观察者类
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                //队列所有任务完成回调
            }

            @Override
            public void onError(Throwable e) {
                //异常会调
            }

            @Override
            public void onNext(String s) {
                //每个事件任务的回调
            }
        };

        //观察者接口 与Observer一样，及Observer会被先转换成该接口
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };
        /**
         * 两者的区别：
         *           1.onStart():是接口(Subscriber)新增的方法，
         *             在事件还未开始发送的时候调用，可以进行一些初始化的工作，
         *             注意该方法在当前线程被调用
         *           2.unsubscriber(),这是接口(Subscriber)另一个接口(Subscription)的方法，
         *             用于取订阅，当该方法被调用时Subscriber不在接收事件，可以用isUnsubscriber()判断状态
         *             unsubscribe() 这个方法很重要，因为在 subscribe() 之后，
         *             Observable 会持有 Subscriber 的引用，
         *             这个引用如果不能及时被释放，将有内存泄露的风险。
         *             所以最好保持一个原则：要在不再使用的时候尽快在合适的地方
         *             （例如 onPause() onStop() 等方法中）调用 unsubscribe() 来解除引用关系，
         *             以避免内存泄露的发生。
         *
         */
    }

    /**
     * 被观察者 view
     */
    private void observable(){
        /**
         * create最基础的方法
         * 在此基础扩展方法
         * 如：just("","","")，form（T[]）,
         */
        Observable observable = Observable.create(new Observable.OnSubscribe<String>(){

            @Override
           public void call(Subscriber<? super String> subscriber) {

            }
        });
    }

    /**
     * 订阅
     */
    private void subscribe(Observable observable,Observer observer){
        observable.subscribe(observer);
    }

}
