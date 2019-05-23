package com.fly.newstart.imgcompress.compress;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.fly.newstart.imgcompress.ImgCompressActivity;
import com.fly.newstart.imgcompress.config.CompressConfig;
import com.fly.newstart.imgcompress.entity.PhotoEntity;
import com.fly.newstart.imgcompress.interf.CompressImage;
import com.fly.newstart.imgcompress.interf.CompressResultListener;
import com.fly.newstart.imgcompress.utils.CompressImageUtils;
import com.shangyi.android.utils.ListUtils;

import java.io.File;
import java.util.ArrayList;

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
 * 包    名 : com.fly.newstart.imgcompress.compress
 * 作    者 : FLY
 * 创建时间 : 2019/5/13
 * 描述: 图片压缩的管理类
 */
public class CompressImageManager implements CompressImage {

    private CompressImageUtils compressImageUtils; //压缩工具类-提炼

    private CompressConfig config; //压缩配置

    private ArrayList<PhotoEntity> images;//需要压缩的图片集合

    private CompressListener listener; //压缩后的监听

    private int errorNum;

    private CompressImageManager(Context context, CompressConfig compressConfig,
                                 ArrayList<PhotoEntity> images, CompressListener listener) {
        this.config = compressConfig;
        this.images = images;
        this.listener = listener;
        this.compressImageUtils = new CompressImageUtils(context, config);
    }

    @Override
    public void compress() {
        if (ListUtils.isEmpty(images)) {
            listener.onCompressFailed(images, 0);
            return;
        }
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i) == null) {
                images.set(i, new PhotoEntity());
            }
        }
        // 开始压缩
        compressImage(images.get(0));
    }

    private void compressImage(final PhotoEntity image) {

        if (TextUtils.isEmpty(image.getOriginalPath())) {
            continueCompress(image, false, "original path is null");
            return;
        }

        File file = new File(image.getOriginalPath());
        if (!file.exists() || !file.isFile()) {
            continueCompress(image, false, "file nothingness");
            return;
        }

        if (file.length() < config.getMaxSize()) {
            continueCompress(image, true, "");
            return;
        }
        //开始压缩
        compressImageUtils.compress(image.getOriginalPath(), new CompressResultListener() {

            @Override
            public void onCompressSuccess(String imgPath) {
                //压缩成功设置压缩图片路径
                image.setCompressPath(imgPath);
                continueCompress(image, true, "");
            }

            @Override
            public void onCompressFailed(String imgPath, String error) {
                continueCompress(image, false, error);
            }
        });
    }


    private void continueCompress(PhotoEntity image, boolean compressed, String error) {
        image.setCompressed(compressed);
        image.setFailedReason(error);
        if (TextUtils.isEmpty(error)) errorNum++;
        //获取当前索引
        int index = images.indexOf(image);

        //判断是否为需要压缩的图片
        if (index == images.size() - 1) {
            callback();
        } else {
            compressImage(images.get(index + 1));
        }

    }

    private void callback() {
        if (errorNum > 0) {
            listener.onCompressFailed(images, errorNum);
        } else {
            listener.onCompressSuccess(images);
        }
    }

    public static CompressImage build(Context context, CompressConfig compressConfig,
                                      ArrayList<PhotoEntity> images, CompressListener listener) {
        return new CompressImageManager(context, compressConfig, images, listener);
    }
}
