package com.fly.newstart.imgcompress;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fly.newstart.R;
import com.fly.newstart.imgcompress.compress.utils.CachePathUtils;
import com.fly.newstart.imgcompress.compress.utils.CommonUtils;
import com.fly.newstart.imgcompress.compress.utils.Constants;
import com.fly.newstart.imgcompress.compress.utils.UriParseUtils;

import java.io.File;

public class ImgCompressActivity extends AppCompatActivity {


    private ProgressDialog progressDialog;//压缩加载框

    private String cameraCachePath;//拍照源文件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_compress);
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

        }

        //相册返回
        if (requestCode == Constants.ALBUM_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String path = UriParseUtils.getpath(this, uri);
                // 压缩

            }

        }
    }
}
