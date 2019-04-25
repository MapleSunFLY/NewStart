package com.fly.newstart.ioc.text;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;
import com.fly.newstart.ioc.annotetion.ContentView;
import com.fly.newstart.ioc.annotetion.InjectView;
import com.fly.newstart.ioc.annotetion.OnClick;
import com.fly.newstart.ioc.annotetion.OnLongClick;

// setContentView(R.layout.activity_ioc);
@ContentView(R.layout.activity_ioc)
public class IOCActivity extends BaseActivity {

    @InjectView(R.id.btnO1) //mBtn01 = findViewById(R.id.btnO1)
    private Button mBtn01;

    @InjectView(R.id.btnO2)
    private Button mBtn02;

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, mBtn01.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    // mBtn01.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { //  }});
    @OnClick({R.id.btnO1, R.id.btnO2})
    public void show(View view) {
        Toast.makeText(this, "shwo(View view)", Toast.LENGTH_SHORT).show();
    }

    // mBtn01.setOnLongClickListener(new View.OnLongClickListener() {@Override public boolean onLongClick(View v) {   return false;  }});
    @OnLongClick({R.id.btnO1, R.id.btnO2})
    public boolean showLong(View view) {
        Toast.makeText(this, "shwoLong(View view)", Toast.LENGTH_SHORT).show();
        return false;
    }


//    @OnClick({R.id.btnO1,R.id.btnO2}) //proxy 代理
//    public void show(){
//        Toast.makeText(this, mBtn01.getText().toString(), Toast.LENGTH_LONG).show();
//    }

    public void initView() {
        //用注解实现，需要代理完成，监听回调的过程
        //Android监听事件是有规律的
        //setxxxListener
        //new View.xxxListener
        //回调执行方法：onxxx()
        //将规律打包成一个对象，用代理去完成这个事件
        //还需要用到AOP  面向切面的技术
        mBtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBtn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        mBtn01.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }
}
