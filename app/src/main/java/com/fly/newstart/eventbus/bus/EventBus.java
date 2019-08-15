package com.fly.newstart.eventbus.bus;

import com.fly.newstart.eventbus.Bus1Activity;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 包    名 : com.fly.newstart.eventbus.bus
 * 作    者 : FLY
 * 创建时间 : 2019/8/12
 * 描述:
 */
public class EventBus {

    private static volatile EventBus instance; // volatile 修饰的变量不允许线程内部的缓存与重排序，不允许修改内存，轻量级的同步块

    //保存订阅方法
    private Map<Object, List<MethodManager>> cacheMap;

    private EventBus() {
        cacheMap = new HashMap<>();
    }

    public static EventBus getDefault() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }


    /**
     * 接受信息方
     *
     * @param getter 接受界面
     */
    public void register(Object getter) {
        List<MethodManager> methodList = cacheMap.get(getter);
        if (methodList == null) {
            methodList = findAnnotationMethod(getter);
            cacheMap.put(getter, methodList);
        }
    }

    /**
     * 获取对应界面注册的方法集合
     *
     * @param getter 注册界面
     * @return 方法集合
     */
    private List<MethodManager> findAnnotationMethod(Object getter) {

        List<MethodManager> methodList = new ArrayList<MethodManager>();
        Class<?> clazz = getter.getClass();

        //获取当前类的所有方法
        Method[] declaredMethods = clazz.getDeclaredMethods();
        if (declaredMethods.length > 0) {
            for (Method declaredMethod : declaredMethods) {

                //获取对应方法的注解
                Subscribe annotation = declaredMethod.getAnnotation(Subscribe.class);
                if (annotation == null) continue;

                //方法校验
                //1、返回值校验
                Type returnType = declaredMethod.getGenericReturnType();
                if (!"void".equals(returnType.toString())) {
                    throw new RuntimeException(declaredMethod.getName() + "这个订阅监听的方法返回必须为 void");
                }

                //2、方法参数的校验
                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new RuntimeException(declaredMethod.getName() + "有且仅有一个参数");
                }

                //符合要求的方法
                MethodManager methodManager = new MethodManager(parameterTypes[0], annotation.threadMode(), declaredMethod);
                methodList.add(methodManager);
            }
        }
        return methodList;
    }

    //发送事件
    public void post(Object setter) {
        Set<Object> set = cacheMap.keySet();
        if (set != null && !set.isEmpty()) {
            for (Object getter : set) {
                List<MethodManager> methodList = cacheMap.get(getter);
                if (methodList == null || methodList.size() < 1) continue;
                for (MethodManager method : methodList) {
                    //判断订阅类实体与发送实体，是否是同一个类或超类
                    if (method.getType().isAssignableFrom(setter.getClass())) {
                        invoke(method, getter, setter);
                    }
                }
            }
        }
    }

    private void invoke(MethodManager method, Object getter, Object setter) {
        Method execute = method.getMethod();
        try {
            execute.invoke(getter, setter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反注册
     *
     * @param getter 注册界面
     */
    public void unregister(Object getter) {
        cacheMap.remove(getter);
    }
}
