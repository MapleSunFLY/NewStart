package com.fly.newstart.permission;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;
import com.fly.newstart.permission.request.IRequestPermissions;
import com.fly.newstart.permission.request.RequestPermissions;
import com.fly.newstart.permission.requestresult.IRequestPermissionsResult;
import com.fly.newstart.permission.requestresult.RequestPermissionsResultSetApp;
import com.fly.newstart.permission.rx.RxDisposableManage;
import com.fly.newstart.permission.rx.RxView;
import com.fly.newstart.permission.rxpermissions.PermissionCallback;
import com.fly.newstart.permission.rxpermissions.PermissionHelper;
import com.fly.newstart.permission.utils.FileProviderUtils;
import com.fly.newstart.permission.utils.SystemProgramUtils;

import java.io.File;

import io.reactivex.functions.Consumer;

public class PermissionsActivity extends BaseActivity {

    Button btnPaizhao;
    Button btnXiangce;
    ImageView ivTupian;

    IRequestPermissions requestPermissions = RequestPermissions.getInstance();//动态权限请求
    IRequestPermissionsResult requestPermissionsResult = RequestPermissionsResultSetApp.getInstance();//动态权限请求结果处理

    //需要请求的权限
    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        initView();
        initEvent();
    }

    //初始化控件
    private void initView() {
        btnPaizhao = (Button) findViewById(R.id.paizhao);
        btnXiangce = (Button) findViewById(R.id.xiangce);
        ivTupian = (ImageView) findViewById(R.id.tupian);
    }

    //初始化事件
    private void initEvent() {
        //拍照
        btnPaizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               1ystemProgramUtils.paizhao(PermissionsActivity.this, new File("/mnt/sdcard/tupian.jpg"));
            }
        });

        RxView.click(btnXiangce, new Consumer<View>() {
            @Override
            public void accept(View view) throws Exception {
                PermissionHelper.getInstance().requestEach(PermissionsActivity.this, new PermissionCallback() {

                    @Override
                    public void onGranted(String permissionName) {

                    }

                    @Override
                    public void onRefuse(String permissionName) {

                    }

                    @Override
                    public void onNoMoreReminder(String permissionName) {

                    }
                }, permissions);
            }
        });
        //相册
    }

    //请求权限
    private boolean requestPermissions() {
        //需要请求的权限
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        //开始请求权限
        return requestPermissions.requestPermissions(
                this,
                permissions,
                PermissionUtils.ResultCode1);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    //用户授权操作结果（可能授权了，也可能未授权）
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        //用户给APP授权的结果
//        //判断grantResults是否已全部授权，如果是，执行相应操作，如果否，提醒开启权限
//        if (requestPermissionsResult.doRequestPermissionsResult(this, permissions, grantResults, new OnPermissionClickListener() {
//            @Override
//            public void onClick(boolean isPositive) {
//                if (isPositive) {
//                    Toast.makeText(PermissionsActivity.this, "前往权限管理", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(PermissionsActivity.this, "取消了，打开权限", Toast.LENGTH_LONG).show();
//                }
//            }
//        })) {
//            //请求的权限全部授权成功，此处可以做自己想做的事了
//            //输出授权结果
//            Toast.makeText(PermissionsActivity.this, "授权成功，请重新点击刚才的操作！", Toast.LENGTH_LONG).show();
//        } else {
//            //输出授权结果
//            Toast.makeText(PermissionsActivity.this, "请给APP授权，否则功能无法正常使用！", Toast.LENGTH_LONG).show();
//        }
//    }

    //拍照、相册、图片裁切结果回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        Uri filtUri;
        File outputFile = new File("/mnt/sdcard/tupian_out.jpg");//裁切后输出的图片
        switch (requestCode) {
            case SystemProgramUtils.REQUEST_CODE_PAIZHAO:
                //拍照完成，进行图片裁切
                File file = new File("/mnt/sdcard/tupian.jpg");
                filtUri = FileProviderUtils.uriFromFile(PermissionsActivity.this, file);
                SystemProgramUtils.Caiqie(PermissionsActivity.this, filtUri, outputFile);
                break;
            case SystemProgramUtils.REQUEST_CODE_ZHAOPIAN:
                //相册选择图片完毕，进行图片裁切
                if (data == null || data.getData() == null) {
                    return;
                }
                filtUri = data.getData();
                SystemProgramUtils.Caiqie(PermissionsActivity.this, filtUri, outputFile);
                break;
            case SystemProgramUtils.REQUEST_CODE_CAIQIE:
                //图片裁切完成，显示裁切后的图片
                try {
                    Uri uri = Uri.fromFile(outputFile);
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    ivTupian.setImageBitmap(bitmap);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxDisposableManage.getInstance().cancel(this);
    }
}
