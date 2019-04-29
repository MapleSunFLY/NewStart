package com.fly.newstart.dynamicproxy.text;

import com.google.gson.annotations.Expose;
import com.shangyi.android.utils.GsonUtils;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.postop.code.http
 * 作    者 : FLY
 * 创建时间 : 2018/9/20
 * 描述: 返回数据的外包装类
 */
public class ResponseJson<T> {

    public static final int SUCCESS_CODE = 0;

    /**
     * 调用返回状态码
     */
    @Expose
    public int apiStatus = -1;

    /**
     * 调用返回Token状态
     */
    @Expose
    public int sysStatus;

    /**
     * 返回的错误消息
     */
    public String info;
    /**
     * 服务器当前时间戳
     */
    @Expose
    public long timestamp;
    /**
     * 接口返回具体数据内容 JSONObject
     */
    @Expose
    public T data;

    public long execTime;

    public boolean isSuccess() {
        return apiStatus == SUCCESS_CODE && sysStatus == SUCCESS_CODE;
    }

    /**
     * //token状态为1、3、4、5（token未绑定），则跳转到登录页
     * @return
     */
    public boolean isNotToken() {
        return apiStatus != SUCCESS_CODE && sysStatus <= 5 && sysStatus > SUCCESS_CODE;
    }

    /**
     * 如果返回token状态为6（token不存在），则重新获取token；
     * @return
     */
    public boolean isNothingToken() {
        return  apiStatus != SUCCESS_CODE && sysStatus == 6 ;
    }

    public String toJsonString() {
        return GsonUtils.toJson(this);
    }
}