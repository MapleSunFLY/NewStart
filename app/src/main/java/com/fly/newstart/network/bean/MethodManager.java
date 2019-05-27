package com.fly.newstart.network.bean;

import com.fly.newstart.network.type.NetType;

import java.lang.reflect.Method;

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
 * 包    名 : com.fly.newstart.network.bean
 * 作    者 : FLY
 * 创建时间 : 2019/5/27
 * 描述: 保存符合要求的网络监听的注解方法
 */
public class MethodManager {

    /**
     * 参数类型 NetType
     */
    private Class<?> type;

    /**
     * 网络类型 @Network(netType = NetType.AUTO)
     */
    private NetType netType;

    /**
     * 需要执行的方法，注解下的方法
     */
    private Method method;

    public MethodManager(Class<?> type, NetType netType, Method method) {
        this.type = type;
        this.netType = netType;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
