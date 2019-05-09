package com.fly.newstart.plugin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;
import com.fly.newstart.utils.FileUtils;
import com.shangyi.android.pluginlibrary.PluginManager;
import com.shangyi.android.pluginlibrary.ProxyActivity;

public class MainPluginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_plugin);
        PluginManager.getInstance().init(this);
        findViewById(R.id.btnLoad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //模拟服务器下载插件apk
                String apkPath = FileUtils.copyAssetAndWrite(MainPluginActivity.this, "pluginapk-debug.apk");
                //加载apk
                PluginManager.getInstance().loadApk(apkPath);
            }
        });

        findViewById(R.id.btnSkip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //指定跳转的类名
                Intent intent = new Intent();
                intent.setClass(MainPluginActivity.this, ProxyActivity.class);
                intent.putExtra(ProxyActivity.CLASS_NAME, "com.shangyi.android.pluginapk.PluginActivity");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
