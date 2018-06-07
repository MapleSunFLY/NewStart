package com.fly.newstart.viewmode;

import android.text.TextUtils;

import com.fly.newstart.utils.GsonUtils;
import com.google.gson.annotations.Expose;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.newstart.viewmode
 * 作    者 : FLY
 * 创建时间 : 2018/6/4
 * <p>
 * 描述: 请求返回的json模型
 */

public class ResponseJson<T> {
    public static final String SUCCESS_CODE="0";

    /**
     * 返回的错误消息
     */
    public String msg;
    /**
     * 调用返回状态码
     */
    @Expose
    public String code ="";
    /**
     * 服务器当前时间戳
     */
    @Expose
    public long ts;
    /**
     * 接口返回具体数据内容 JSONObject
     */
    @Expose
    public T data;

    public long execTime;

    public boolean isOk() {
        return TextUtils.equals(code,SUCCESS_CODE);
    }

    public String toJsonString() {
        return GsonUtils.toJson(this);
    }

}
