package com.fly.newstart.ioc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

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
 * 包    名 : com.fly.newstart.ioc
 * 作    者 : FLY
 * 创建时间 : 2019/4/24
 * 描述: 事件处理拦截器
 */
public class ListenerInvocationHandler implements InvocationHandler {

    //需要拦截的对象
    private Object target;

    //需要拦截的方法集合
    private HashMap<String, Method> methodhMap = new HashMap<>();

    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (target != null) {
            //获取需要拦截的方法名
            String methodName = method.getName();
            //重新赋值，将拦截的方法换为了自定义的方法
            method = methodhMap.get(methodName);//从集合中判断是否需要拦截
            if (method != null) {//确定找到了需要拦截的的方法，才执行自定义方法
                if (method.getParameterTypes().length == 0) { //判断有无参数
                    return method.invoke(target);
                } else return method.invoke(target, args);
            }
        }
        return null;
    }

    /**
     * 将需要拦截的方法加入集合
     *
     * @param methodName 需要拦截的方法名
     * @param method     执行自定义的方法
     */
    public void addMethod(String methodName, Method method) {
        methodhMap.put(methodName, method);
    }
}
