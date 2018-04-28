package com.fly.newstart.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

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
 * 包    名 : com.fly.newstart.utils
 * 作    者 : FLY
 * 创建时间 : 2017/8/8
 * <p>
 * 描述:
 */

public class GsonUtils {

    public static <T> T fromJson(String json, Type classOfT) {
        Gson gson = new Gson();

        T t = gson.fromJson(json, classOfT);
        return t;
    }

    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
