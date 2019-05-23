package com.fly.newstart.imgcompress.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.fly.newstart.utils.LogUtil;
import com.shangyi.android.utils.LogUtils;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.newstart.imgcompress.utils
 * 作    者 : FLY
 * 创建时间 : 2019/5/14
 * 描述:
 */
public class CommonUtils {

    /**
     * 判断设备是否有相机功能
     *
     * @param activity    上下文
     * @param intent      相机意图
     * @param requestCode 回调标识码
     */
    public static void hasCamera(Activity activity, Intent intent, int requestCode) {
        if (activity == null) {
            LogUtils.e("CommonUtils：Activity is null");
            return;
        }

        PackageManager pm = activity.getPackageManager();
        boolean hasCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                || Camera.getNumberOfCameras() > 0;
        if (hasCamera) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            Toast.makeText(activity, "当前设备没有相机", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取拍照的Intent
     *
     * @param outPutUri 拍照后图片的输出uri
     * @return 返回Intent，方便封装跳转
     */
    public static Intent getCameraIntent(Uri outPutUri) {
        Intent intent = new Intent();
        intent.addFlags(intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE); //设置action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri); //将拍照的照片保存到指URI
        return intent;
    }

    /**
     * 跳转到图库选择
     *
     * @param activity    上下文
     * @param requestCode 回调码
     */
    public static void openAlbum(Activity activity, int requestCode) {
        //调用图库，获取所有本地图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 显示圆形进度对话框
     *
     * @param activity      上下文
     * @param progressTitle 显示的标题
     * @return ProgressDialog
     */
    public static ProgressDialog showProgressDialog(Activity activity, String... progressTitle) {
        if (activity == null || activity.isFinishing()) return null;
        String title = "提示";
        if (progressTitle != null && progressTitle.length > 0) title = progressTitle[0];
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }
}
