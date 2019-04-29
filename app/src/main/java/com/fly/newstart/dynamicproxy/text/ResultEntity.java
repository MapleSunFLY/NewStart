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
 *   `-..__.-' _.-\$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.newstart.dynamicproxy.text
 * 作    者 : FLY
 * 创建时间 : 2019/4/28
 * 描述: 返回外层实体
 */
public class ResultEntity<T> {

    public String reason;
    /**
     * 接口返回具体数据内容 JSONObject
     */
    @Expose
    public T result;

    public long error_code;

    public String toJsonString() {
        return GsonUtils.toJson(this);
    }
}
