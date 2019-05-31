package com.fly.newstart.imgcompress;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.fly.newstart.R;
import com.fly.newstart.imgcompress.compress.utils.CachePathUtils;
import com.fly.newstart.imgcompress.compress.utils.CommonUtils;
import com.fly.newstart.imgcompress.compress.utils.Constants;
import com.fly.newstart.imgcompress.compress.utils.UriParseUtils;
import com.fly.newstart.imgcompress.compresscore.CompressImage;
import com.fly.newstart.imgcompress.interf.CompressionPredicate;
import com.fly.newstart.imgcompress.interf.OnCompressListener;
import com.fly.newstart.permission.PermissionUtils;
import com.fly.newstart.permission.request.RequestPermissions;
import com.shangyi.android.utils.LogUtils;

import java.io.File;

public class ImgCompressActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_compress);
        //PermissionUtils.getInstance().getPermissionNames(ListUtils.<String>newArrayList( Manifest.permission.WRITE_EXTERNAL_STORAGE,));
        requestPermissions();
    }

    //请求权限
    private boolean requestPermissions() {
        String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};
        return RequestPermissions.getInstance().requestPermissions(
                this,
                permissions,
                PermissionUtils.ResultCode1);
    }

    //拍照
    public void camers(View view) {
        //Android 7.0 File 路径的变更，需要使用 FileProvider 来做
        Uri outputUri;
        File file = CachePathUtils.getCameraCacheFile();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            outputUri = UriParseUtils.getCameraOutPutUri(this, file);
        } else {
            outputUri = Uri.fromFile(file);
        }
    }

    //相册
    public void album(View view) {
        CommonUtils.openAlbum(this, Constants.ALBUM_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //拍照返回
        if (requestCode == Constants.CAMERA_CODE && resultCode == RESULT_OK) {
            // 压缩

        }

        //相册返回
        if (requestCode == Constants.ALBUM_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String path = UriParseUtils.getRealPathFromUriAboveApi19(this, uri);

                CompressImage.with(this)
                        .load(path)
                        //.leastCompressSize(100)
                        .maxSize(200)
                        .setTargetDir(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CompressImage/")
                        .filter(new CompressionPredicate() {
                            @Override
                            public boolean apply(String path) {
                                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                            }
                        })
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                LogUtils.e("FLY110", "压缩开始");
                            }

                            @Override
                            public void onSuccess(File file) {
                                LogUtils.e("FLY110", "压缩成功·" + file.getAbsolutePath());

                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtils.e("FLY110", "出现问题:" + e.getLocalizedMessage());
                            }
                        }).launch();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
