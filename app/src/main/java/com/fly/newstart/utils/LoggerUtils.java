package com.fly.newstart.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
 * 描述: 一个Gsom分行打印的工具类
 */

public class LoggerUtils {

    public static void printResult(String json){
        //TODO 打印格式改变
        String ss =json;
        String tag = null;
        if (json.indexOf("{") > 0) {
            tag = json.substring(0, json.indexOf("{"));
            ss = json.substring(json.indexOf("{"));
        }
        if (json.indexOf("{") > -1) {
            try {
                Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(ss);
                ss = gson1.toJson(jsonElement);
                if (!TextUtils.isEmpty(tag)) {
                    ss = tag + "\n" + ss;
                }
                Log.e("gosnutil", ss);
            } catch (Exception e) {
            }
        }
    }

    public static void printParam(String json){
        //TODO 打印格式改变
        String ss =json;
        String tag = null;
        if (json.indexOf("{") > 0) {
            tag = json.substring(0, json.indexOf("{"));
            ss = json.substring(json.indexOf("{"));
        }
        if (json.indexOf("{") > -1) {
            try {
                Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(ss);
                ss = gson1.toJson(jsonElement);
                if (!TextUtils.isEmpty(tag)) {
                    ss = tag + "\n" + ss;
                }
                Log.e("Request", ss);
            } catch (Exception e) {
            }
        }
    }
}
