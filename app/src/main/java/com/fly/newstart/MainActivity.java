package com.fly.newstart;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fly.newstart.announcement.AnnouncementActivity;
import com.fly.newstart.broadcast.BroadcastActivity;
import com.fly.newstart.circlemenu.CircleMenuActivity;
import com.fly.newstart.dialog.DialogActivity;
import com.fly.newstart.dynamicproxy.text.ProxyActivity;
import com.fly.newstart.gettxt.GetTxtActivity;
import com.fly.newstart.imgcompress.ImgCompressActivity;
import com.fly.newstart.ioc.text.IOCActivity;
import com.fly.newstart.myokhttp.HttpActivity;
import com.fly.newstart.myview.ProgressActivity;
import com.fly.newstart.neinterface.text.AActivity;
import com.fly.newstart.network.NetworkManager;
import com.fly.newstart.network.annotation.Network;
import com.fly.newstart.network.test.NetworkActivity;
import com.fly.newstart.network.type.NetType;
import com.fly.newstart.pdf.PDFViewActivity;
import com.fly.newstart.permission.PermissionsActivity;
import com.fly.newstart.plugin.MainPluginActivity;
import com.fly.newstart.rx.RxActivity;
import com.fly.newstart.scancode.ScanActivity;
import com.fly.newstart.signature.LinePathViewActivity;
import com.fly.newstart.utils.LocationUtils;
import com.fly.newstart.webview.WebViewActivity;
import com.shangyi.android.core.base.BaseLiveDataActivity;
import com.shangyi.android.utils.LogUtils;

public class MainActivity extends BaseLiveDataActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textLocation = (TextView) findViewById(R.id.tv_location);
        Location location = LocationUtils.getInstance(this).showLocation();
        if (location != null) {
            String address = "纬度：" + location.getLatitude() + "经度：" + location.getLongitude();
            textLocation.setText(address);
        } else {
            textLocation.setText("未获取");
        }

        NetworkManager.getDefault().registerObserver(this);
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
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
                intent.setClass(MainActivity.this, ScanActivity.class);
                break;
            case R.id.btnProperty:
                intent.setClass(MainActivity.this, CircleMenuActivity.class);
                break;
            case R.id.btnGetTxT:
                intent.setClass(MainActivity.this, GetTxtActivity.class);
                break;
            case R.id.btnBroadcast:
                intent.setClass(MainActivity.this, BroadcastActivity.class);
                break;
            case R.id.btnPermissions:
                intent.setClass(MainActivity.this, PermissionsActivity.class);
                break;
            case R.id.btnNeinterface:
                intent.setClass(MainActivity.this, AActivity.class);
                break;
            case R.id.btnIOC:
                intent.setClass(MainActivity.this, IOCActivity.class);
                break;
            case R.id.btnProxy:
                intent.setClass(MainActivity.this, ProxyActivity.class);
                break;
            case R.id.btnPlugin:
                intent.setClass(MainActivity.this, MainPluginActivity.class);
                break;
            case R.id.btnNetwork:
                intent.setClass(MainActivity.this, NetworkActivity.class);
                break;
            case R.id.btnCompress:
                intent.setClass(MainActivity.this, ImgCompressActivity.class);
                break;
            case R.id.btnDialog:
                intent.setClass(MainActivity.this, DialogActivity.class);
                break;
            default:
                Log.d(TAG, "onClick: " + view.getId());
                return;
        }
        startActivity(intent);
    }

    @Network(netType = NetType.WIFI)
    public void network(NetType netType) {
        LogUtils.d(TAG,"当前网络状况：" + netType.name());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NetworkManager.getDefault().removeObserver(this);
    }
}
