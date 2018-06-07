package com.fly.newstart.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.newstart.utils
 * 作    者 : FLY
 * 创建时间 : 2018/6/4
 * <p>
 * 描述:
 */

public class LogUtil {
    public static void print(Object error) {
        jsonFormatterLog("" + error);
    }

    private static void jsonFormatterLog(String s) {
        if (TextUtils.isEmpty(s)) {
            Log.e("LOG:", "" + s);
            return;
        }
        String json = s;
        String tag = null;
        if (s.indexOf("{") > 0) {
            tag = s.substring(0, s.indexOf("{"));
            json = s.substring(s.indexOf("{"));
        }
        if (s.indexOf("{") > -1) {
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(json);
                json = gson.toJson(jsonElement);
                if (!TextUtils.isEmpty(tag)) {
                    json = tag + "\n" + json;
                }
                Log.e("LOG:", "" + json);
                return;
            } catch (Exception e) {
            }
        }
        Log.e("LOG:", "" + s);
    }
}
