package com.fly.newstart.okgo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(O)(OK)---/$\
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
 * 包    名 : com.fly.newstart.okgo
 * 作    者 : FLY
 * 创建时间 : 2017/8/8
 * <p>
 * 描述: 基础数据的返回类型
 */

final public class ResponseJson<T> {

    /**
     * 返回的错误消息
     */
    public String msg;
    /**
     * 调用返回状态码
     */
    @Expose
    public int errcode = -1;
    /**
     * 服务器当前时间戳
     */
    @Expose
    public long ts;
    /**
     * 接口返回具体数据内容 JSONObject 泛型
     */
    @Expose
    public T data;

    public long execTime;

    public boolean isOk() {
        return errcode == 200;
    }

    public String toJsonString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}

