package com.fly.newstart.signature;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.fly.myview.LinePathView;
import com.fly.newstart.R;

import java.io.IOException;

public class LinePathViewActivity extends AppCompatActivity {

    private LinePathView mPathView;
    private Button mBtnClear;
    private Button mBtnSave;
    private ScrollView mScrollView;
    boolean aBoolean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_path_view);
        mPathView = (LinePathView) findViewById(R.id.view);
        mPathView.setFocusable(true);
        mScrollView = (ScrollView) findViewById(R.id.sv_path);

        //修改背景、笔宽、颜色
        mPathView.setBackColor(Color.WHITE);
        mPathView.setPaintWidth(20);
        mPathView.setPenColor(Color.BLACK);

        mPathView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        mScrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPathView.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });


        mBtnClear = (Button) findViewById(R.id.btn_clear);
        mBtnSave = (Button) findViewById(R.id.btn_save);

        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除
                mPathView.clear();
                mPathView.setBackColor(Color.WHITE);
                mPathView.setPaintWidth(20);
                mPathView.setPenColor(Color.BLACK);
            }
        });

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存
                if (mPathView.getTouched()) {
                    try {
                        mPathView.save("/sdcard/qm.png", true, 10);
                        setResult(100);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LinePathViewActivity.this, "您没有签名~", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
