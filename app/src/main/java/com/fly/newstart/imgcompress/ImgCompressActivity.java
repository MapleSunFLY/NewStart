package com.fly.newstart.imgcompress;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fly.newstart.R;
import com.fly.newstart.imgcompress.compress.CompressImageManager;
import com.fly.newstart.imgcompress.config.CompressConfig;
import com.fly.newstart.imgcompress.entity.PhotoEntity;
import com.fly.newstart.imgcompress.interf.CompressImage;
import com.fly.newstart.imgcompress.utils.CachePathUtils;
import com.fly.newstart.imgcompress.utils.CommonUtils;
import com.fly.newstart.imgcompress.utils.Constants;
import com.fly.newstart.imgcompress.utils.UriParseUtils;
import com.fly.newstart.utils.LogUtil;
import com.shangyi.android.utils.ListUtils;
import com.shangyi.android.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;

public class ImgCompressActivity extends AppCompatActivity implements CompressImage.CompressListener {

    private CompressConfig compressConfig;//压缩配置

    private ProgressDialog progressDialog;//压缩加载框

    private String cameraCachePath;//拍照源文件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_compress);

        compressConfig = CompressConfig.builder()
                .setUnCompressMinPixel(1000) //最小像素不压缩，默认：1000
                .setUnCompressNormalPixel(2000) //标准像素不压缩，默认：2000
                .setMaxPixel(1200)// 长或宽不超过的最大像素（单位px），默认：1200
                .setMaxSize(100 * 1024)// 压缩到的最大大小（单位），默认：200 * 1024 = 200KB
                .enablePixelCompress(true)// 是否启动像素压缩，默认：true
                .enableQualityCompress(true)// 是否启动质量压缩，默认：true
                .enableReserveRaw(true)// 是否保存源文件，默认：true
                .setCacheDir("")// 压缩后缓存图片目录，非文件路径，默认：Constants.COMPRESS_CACHE
                .setShowCompressDialog(true)// 是否显示压缩进度条，默认：false
                .create();
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
        cameraCachePath = file.getAbsolutePath();
        //启动拍照
        CommonUtils.hasCamera(this, CommonUtils.getCameraIntent(outputUri), Constants.CAMERA_CODE);
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
            preCompress(cameraCachePath);
        }

        //相册返回
        if (requestCode == Constants.ALBUM_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String path = UriParseUtils.getpath(this, uri);
                // 压缩
                preCompress(cameraCachePath);
            }

        }
    }

    private void preCompress(String cameraCachePath) {
        ArrayList<PhotoEntity> photoEntities = new ArrayList<>();
        photoEntities.add(new PhotoEntity(cameraCachePath));
        if (ListUtils.isEmpty(photoEntities)) comperss(photoEntities);
    }

    private void comperss(ArrayList<PhotoEntity> photoEntities) {
        if (compressConfig.getShowCompressDialog()) { //配置了开启Dialog
            LogUtils.d("open dialog");
            progressDialog = CommonUtils.showProgressDialog(this, "压缩中");
        }
        //真正的压缩
        CompressImageManager.build(this,compressConfig,photoEntities,this).compress();
    }

    @Override
    public void onCompressSuccess(ArrayList<PhotoEntity> images) {
        LogUtils.d("Compress Success");
        if (progressDialog != null) progressDialog.dismiss();
    }

    @Override
    public void onCompressFailed(ArrayList<PhotoEntity> images, int error) {
        if (progressDialog != null) progressDialog.dismiss();
    }
}
