package com.fly.newstart.neinterface.text;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;
import com.fly.newstart.neinterface.FunctionManager;

public class BActivity extends BaseActivity {

    private Button mBtnFuncton01;
    private Button mBtnFuncton02;
    private Button mBtnFuncton03;
    private Button mBtnFuncton04;
    private Button mBtnCloseB;
    private TextView mTvShow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);


        mBtnFuncton01 = findViewById(R.id.btnFuncton01);
        mBtnFuncton02 = findViewById(R.id.btnFuncton02);
        mBtnFuncton03 = findViewById(R.id.btnFuncton03);
        mBtnFuncton04 = findViewById(R.id.btnFuncton04);
        mBtnCloseB = findViewById(R.id.btnCloseB);
        mTvShow = findViewById(R.id.tvShow);


        mBtnFuncton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionManager.getInstance().invokeFunction(AActivity.FUNCTION_1);
            }
        });


        mBtnFuncton02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvShow.setText(FunctionManager.getInstance().invokeFunction(AActivity.FUNCTION_2, String.class));
            }
        });

        mBtnFuncton03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionManager.getInstance().invokeFunction(AActivity.FUNCTION_3, "调用了方法3,有参数无返回值类型方法");
            }
        });

        mBtnFuncton04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvShow.setText(FunctionManager.getInstance().invokeFunction(AActivity.FUNCTION_4, "调用了方法4,有参数有返回值类型方法", String.class));
            }
        });

        mBtnCloseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
