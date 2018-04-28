package com.fly.newstart.scancode;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import com.fly.newstart.common.base.BaseActivity;


/**
 * Title: ScanActivity
 * Description: 扫码
 * Copyright:Copyright(c)2018
 * Company:
 * CreateTime:2018/4/5  下午3:04
 *
 * @author dengqinsheng
 * @version 1.0
 */
public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {

    private TextView mToolCenterTv;
    private QRCodeView mQRCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        initView();
        activityBack();

        getRxPermission().request(Manifest.permission.CAMERA)
                .subscribe(b -> {
                    if (!b) {
                    }
                });
    }

    private void initView() {
        mToolCenterTv = (TextView) findViewById(R.id.tv_tool_center);
        mToolCenterTv.setText("扫码");
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.e(TAG, "onScanQRCodeSuccess: result:" + result );
        vibrate();
        mQRCodeView.stopSpot();
        mQRCodeView.stopCamera();
    }


    @Override
    public void onScanQRCodeOpenCameraError() {
        NToast.shortToast(this, "打开相机出错");
        NLog.e(this.getClass().getSimpleName(), "打开相机出错");
    }
}
