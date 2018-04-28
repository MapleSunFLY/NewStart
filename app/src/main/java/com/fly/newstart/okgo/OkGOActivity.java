package com.fly.newstart.okgo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fly.newstart.common.base.BaseActivity;
import com.fly.newstart.okgo.callback.JsonCallback;
import com.fly.newstart.okgo.callback.MyCallback;
import com.fly.newstart.okgo.entity.UserBean;
import com.fly.newstart.okgo.model.LoginModel;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
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
 * 包    名 : com.fly.newstart.okgo
 * 作    者 : FLY
 * 创建时间 : 2017/8/6
 * <p>
 * 描述:
 */

public class OkGOActivity extends BaseActivity {


   private LoginModel loginModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        loginModel = new LoginModel(this);
    }

    private void login(){
        Map<String,String> param = new HashMap<String, String>();
        loginModel.login(this, "", param, new JsonCallback<ResponseJson<UserBean>>() {


            @Override
            public void onSuccess(Response<ResponseJson<UserBean>> response) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
   }
}
