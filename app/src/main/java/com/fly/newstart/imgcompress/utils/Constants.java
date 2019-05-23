package com.fly.newstart.imgcompress.utils;

import android.os.Environment;

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
 * 描述: 常量
 */
public class Constants {

    public static final int CAMERA_CODE = 1001;//相机

    public static final int ALBUM_CODE = 1002;//相册

    //缓存根目录
    public static final String BASE_CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/";

    //压缩后目录
    public static final String COMPRESS_CACHE = "compress_cache";


}
