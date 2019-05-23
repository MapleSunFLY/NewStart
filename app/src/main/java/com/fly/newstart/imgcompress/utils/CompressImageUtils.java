package com.fly.newstart.imgcompress.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.fly.newstart.imgcompress.config.CompressConfig;
import com.fly.newstart.imgcompress.interf.CompressResultListener;

import java.io.FileNotFoundException;


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
 * 描述: 文件压缩的工具类
 */
public class CompressImageUtils {

    private Context context;
    private CompressConfig config;
    private Handler mHandler = new Handler();


    public CompressImageUtils(Context context, CompressConfig config) {
        this.context = context;
        this.config = config == null ? CompressConfig.getDefaultConfig() : config;
    }

    public void compress(String imgPath, CompressResultListener listener) {
        if (config.getEnablePixelCompress()) {
            compressImageByPixel(imgPath, listener);
        } else {
            compressImageByQuality(BitmapFactory.decodeFile(imgPath), imgPath, listener);
        }
    }

    /**
     * 多线程压缩图片质量
     *
     * @param bitmap
     * @param imgPath
     * @param listener
     */
    private void compressImageByQuality(Bitmap bitmap, String imgPath, CompressResultListener listener) {
        if (bitmap != null){

        }
    }

    /**
     * 线程压缩图片像素
     *
     * @param imgPath
     * @param listener
     */
    private void compressImageByPixel(String imgPath, CompressResultListener listener) {

    }
}
