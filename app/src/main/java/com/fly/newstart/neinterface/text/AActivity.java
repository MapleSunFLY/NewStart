package com.fly.newstart.neinterface.text;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;
import com.fly.newstart.neinterface.FunctionHasParamHasResult;
import com.fly.newstart.neinterface.FunctionHasParamNoResult;
import com.fly.newstart.neinterface.FunctionManager;
import com.fly.newstart.neinterface.FunctionNoParamHasResult;
import com.fly.newstart.neinterface.FunctionNoParamNoResult;

public class AActivity extends BaseActivity {

    public static final String FUNCTION_1 = "functon01";
    public static final String FUNCTION_2 = "functon02";
    public static final String FUNCTION_3 = "functon03";
    public static final String FUNCTION_4 = "functon04";

    private TextView mTvShow;
    private Button mBtnStartB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        mTvShow = findViewById(R.id.tvShow);
        mBtnStartB = findViewById(R.id.btnStartB);

        mBtnStartB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AActivity.this.startActivity(new Intent(AActivity.this, BActivity.class));
            }
        });

        //注册方法1-无参数无返回值类型方法

        FunctionManager.getInstance().addFunction(new FunctionNoParamNoResult(FUNCTION_1) {
            @Override
            public void function() {
                mTvShow.setText("调用了方法1,无参数无返回值类型方法");
            }
        });

        FunctionManager.getInstance().addFunction(new FunctionNoParamHasResult<String>(FUNCTION_2) {
            @Override
            public String function() {
                return "调用了方法2,无参数有返回值类型方法";
            }
        });

        FunctionManager.getInstance().addFunction(new FunctionHasParamNoResult<String>(FUNCTION_3) {
            @Override
            public void function(String s) {
                mTvShow.setText(s);
            }
        });

        FunctionManager.getInstance().addFunction(new FunctionHasParamHasResult<String, String>(FUNCTION_4) {
            @Override
            public String function(String s) {
                return s;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FunctionManager.getInstance().removeFunctionNoParamNoResult(FUNCTION_1);
        FunctionManager.getInstance().removeFunctionNoParamHasResult(FUNCTION_2);
        FunctionManager.getInstance().removeFunctionHasParamNoResult(FUNCTION_3);
        FunctionManager.getInstance().removeFunctionHasParamHasResult(FUNCTION_4);
    }
}
