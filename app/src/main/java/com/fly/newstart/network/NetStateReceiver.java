package com.fly.newstart.network;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fly.newstart.network.annotation.Network;
import com.fly.newstart.network.bean.MethodManager;
import com.fly.newstart.network.type.NetType;
import com.shangyi.android.utils.ListUtils;
import com.shangyi.android.utils.LogUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * 包    名 : com.fly.newstart.network
 * 作    者 : FLY
 * 创建时间 : 2019/5/27
 * 描述: 网络改变的广播
 */
public class NetStateReceiver extends BroadcastReceiver {
    protected final String TAG = "FLY." + this.getClass().getSimpleName();
    private NetType netType;

    /**
     * 记录所有界面及其监听的方法
     */
    private Map<Object, List<MethodManager>> networkListMap;

    public NetStateReceiver() {
        netType = NetType.NONE;
        networkListMap = new HashMap<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent == null || intent.getAction() == null) {
            LogUtils.d(TAG, "网络变化异常");
            return;
        }

        if (ConnectivityManager.CONNECTIVITY_ACTION.equalsIgnoreCase(intent.getAction())) {
            LogUtils.d(TAG, "网络发生变化");
            //全局通知
            netType = getNetWorkState(NetworkManager.getDefault().getApplication());
            post(netType);
        }
    }

    /**
     * 同时分发的过程
     *
     * @param netType 当前网络类型
     */
    private void post(NetType netType) {
        Set<Object> set = networkListMap.keySet();
        if (set.isEmpty()) return;
        for (Object o : set) {
            List<MethodManager> methodManagers = networkListMap.get(o);
            if (ListUtils.isEmpty(methodManagers)) continue;
            for (MethodManager methodManager : methodManagers) {
                if (methodManager.getType().isAssignableFrom(netType.getClass())) {
                    switch (methodManager.getNetType()) {
                        case AUTO:
                            invoke(methodManager, o, netType);
                            break;

                        case WIFI:
                            if (netType == NetType.WIFI || netType == NetType.NONE) {
                                invoke(methodManager, o, netType);
                            }
                            break;

                        case CMWAP:
                            if (netType == NetType.CMWAP || netType == NetType.NONE) {
                                invoke(methodManager, o, netType);
                            }
                            break;

                        case CMNET:
                            if (netType == NetType.CMNET || netType == NetType.NONE) {
                                invoke(methodManager, o, netType);
                            }
                            break;

                        case NONE:
                            if (netType == NetType.NONE) {
                                invoke(methodManager, o, netType);
                            }
                            break;
                    }
                }
            }
        }
    }

    private void invoke(MethodManager methodManager, Object o, NetType netType) {
        try {
            //在目标界面执行方法，且参数为NetType
            methodManager.getMethod().invoke(o, netType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断当前网络状态
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPerMissino")
    private NetType getNetWorkState(Context context) {
        //得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return NetType.NONE;
        //获取当前网络连接信息
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //如果网络连接，判断该网络类型
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            int type = activeNetworkInfo.getType();
            if (ConnectivityManager.TYPE_WIFI == type) {
                return NetType.WIFI;//wifi
            } else if (ConnectivityManager.TYPE_MOBILE == type) {
                if ("cmnet".equals(activeNetworkInfo.getExtraInfo().toLowerCase())) {//mobile
                    return NetType.CMNET;
                } else return NetType.CMWAP;
            }
        }
        return NetType.NONE;
    }

    /**
     * 将应用中所有Activity注册了网络监听的方法，添加到集合
     *
     * @param register 界面对象
     */
    public void registerObserver(Object register) {
        //界面对象所有网络监听方法
        List<MethodManager> methodManagers = networkListMap.get(register);
        if (methodManagers == null) {
            //添加方法
            methodManagers = findAnnotationMethod(register);
            networkListMap.put(register, methodManagers);
        }
        LogUtils.d(TAG, "添加界面：" + register.getClass().getName());
    }

    //反射从注解中找到方法，添加到注解中
    private List<MethodManager> findAnnotationMethod(Object register) {
        List<MethodManager> methodManagers = new ArrayList<>();

        Class<?> clazz = register.getClass();
        //获取界面的所有方法
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            //获取方法注解
            Network network = method.getAnnotation(Network.class);
            if (network == null) continue;

            //方法参数校验
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new RuntimeException(method.getName() + "有且仅有一个参数");
            }

            //过滤方法完成，添加集合(不完整，可优化)
            MethodManager manager = new MethodManager(parameterTypes[0], network.netType(), method);
            methodManagers.add(manager);
        }

        return methodManagers;
    }

    /**
     * 移除界面监听 界面销毁调用
     *
     * @param register 界面对象
     */
    public void removeObserver(Object register) {
        if (!networkListMap.isEmpty()) {
            networkListMap.remove(register);
        }
        LogUtils.d(TAG, "移除界面：" + register.getClass().getName());
    }

    /**
     * 应用退出时，注销广播
     */
    public void unregisterReceiver() {
        if (!networkListMap.isEmpty()) {
            networkListMap.clear();
        }
        NetworkManager.getDefault().getApplication().unregisterReceiver(this);
        networkListMap = null;
        LogUtils.d(TAG, "注销网络监听");
    }
}
