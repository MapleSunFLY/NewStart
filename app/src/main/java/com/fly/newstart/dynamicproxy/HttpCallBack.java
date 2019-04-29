package com.fly.newstart.dynamicproxy;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
 * 包    名 : com.fly.newstart.dynamicproxy
 * 作    者 : FLY
 * 创建时间 : 2019/4/25
 * 描述: 回调实现解析数据
 */
public abstract class HttpCallBack<Result> implements ICallBack {
    @Override
    public void onSuccess(String result) {
        //将网络访问框架得到的数据解析
        Gson gson = new Gson();
        //获取HttpCallBack后面的泛型
        Type type = analysisClassInfo(this);
        Result objResult = gson.fromJson(result, type);
        onsuccess(objResult);
    }

    private Type analysisClassInfo(Object obj) {
        //相当于可以得到参数化内型，类型变量，基础类型
        Type genType = obj.getClass().getGenericSuperclass();
        Type[] actualType = ((ParameterizedType) genType).getActualTypeArguments();
        return actualType[0];
    }

    public abstract void onsuccess(Result objResult);

    @Override
    public void onFailure() {

    }
}
