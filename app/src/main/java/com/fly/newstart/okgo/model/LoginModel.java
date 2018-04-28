package com.fly.newstart.okgo.model;

import android.content.Context;
import android.util.Log;

import com.fly.newstart.okgo.ResponseJson;
import com.fly.newstart.okgo.callback.JsonCallback;
import com.fly.newstart.okgo.callback.MyCallback;
import com.fly.newstart.okgo.entity.UserBean;
import com.fly.newstart.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.Map;

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
 * 包    名 : com.fly.newstart.okgo.model
 * 作    者 : FLY
 * 创建时间 : 2017/8/7
 * <p>
 * 描述:
 */

public class LoginModel extends BaseModel {

    public LoginModel(Context activity) {
        super(activity);
    }

    public void login(Context activity, String url, Map<String,String> param, final JsonCallback<ResponseJson<UserBean>> callBack){
        String params = GsonUtils.toJson(param);
        Log.e("TAG",params);
        OkGo.<ResponseJson<UserBean>>post(url).tag(activity)
                .params("data",params)
                .execute(callBack.setType(new TypeToken<ResponseJson<UserBean>>(){}.getType()));

    }


}
