package com.fly.newstart.process;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
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
 * 包    名 : com.fly.newstart.process
 * 作    者 : FLY
 * 创建时间 : 2017/7/27
 * <p>
 * 描述: activity数据保存
 */

public class StateActivity extends BaseActivity{

    private static final String SAVE_INSTANCE_EDT = "SaveInstanceEdT";

    private EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avtivity);
        initView();

        if (editText !=null && savedInstanceState != null){
            editText.setText(savedInstanceState.getString(SAVE_INSTANCE_EDT));
        }

        if (savedInstanceState != null){
            Log.i(TAG, "savedInstanceState: "+savedInstanceState);
        }
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.edt_activity);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (null != outState){
            outState.putString(SAVE_INSTANCE_EDT,editText.getText().toString());
        }
        Log.i(TAG, "onSaveInstanceState: "+outState );
        super.onSaveInstanceState(outState, outPersistentState);

    }
}
