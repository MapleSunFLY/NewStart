package com.fly.newstart;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fly.newstart.common.base.BaseActivity;
import com.fly.newstart.myokhttp.HttpActivity;
import com.fly.newstart.myview.ProgressActivity;
import com.fly.newstart.pdf.PDFViewActivity;
import com.fly.newstart.pdf.PDFViewActivity1;
import com.fly.newstart.rx.RxActivity;
import com.fly.newstart.announcement.AnnouncementActivity;
import com.fly.newstart.scanrode.ScanRodeActivity;
import com.fly.newstart.signature.LinePathViewActivity;
import com.fly.newstart.utils.LocationUtils;
import com.fly.newstart.webview.WebViewActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textLocation = (TextView) findViewById(R.id.tv_location);
        Location location= LocationUtils.getInstance(this).showLocation();
        if (location!=null){
            String address = "纬度：" + location.getLatitude() + "经度：" + location.getLongitude();
            textLocation.setText(address);
        }else {
            textLocation.setText("未获取");
        }
    }

    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.btn_main_okhttp:
                intent.setClass(MainActivity.this, HttpActivity.class);
                break;
            case R.id.btn_main_web:
                intent.setClass(MainActivity.this, WebViewActivity.class);
                break;
            case R.id.btn_main_rx:
                intent.setClass(MainActivity.this, RxActivity.class);
                break;
            case R.id.btn_main_progress:
                intent.setClass(MainActivity.this, ProgressActivity.class);
                break;
            case R.id.btn_photo:
                intent.setClass(MainActivity.this, AnnouncementActivity.class);
                break;
            case R.id.btn_pdf:
                intent.setClass(MainActivity.this, PDFViewActivity.class);
                break;
            case R.id.btn_signature:
                intent.setClass(MainActivity.this, LinePathViewActivity.class);
                break;
            case R.id.btnScanRode:
                intent.setClass(MainActivity.this, ScanRodeActivity.class);
                break;
            default:
                Log.d(TAG, "onClick: "+view.getId());
                break;
        }
        startActivity(intent);
    }
}
