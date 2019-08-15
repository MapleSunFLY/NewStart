package com.fly.newstart.eventbus.bus;

import java.lang.reflect.Method;

/**
 * 包    名 : com.fly.newstart.eventbus.bus
 * 作    者 : FLY
 * 创建时间 : 2019/8/12
 * 描述: 保存符合要求订阅方法的 封装类
 */
public class MethodManager {

    /**
     * 参数类型
     */
    private Class<?> type;

    /**
     * 线程模式
     */
    private ThreadMode threadMode;

    /**
     * 执行的订阅方法
     */
    private Method method;

    public MethodManager(Class<?> type, ThreadMode threadMode, Method method) {
        this.type = type;
        this.threadMode = threadMode;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
