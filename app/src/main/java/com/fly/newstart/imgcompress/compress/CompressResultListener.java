package com.fly.newstart.imgcompress.compress;

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
 * 包    名 : com.fly.newstart.imgcompress.interf
 * 作    者 : FLY
 * 创建时间 : 2019/5/14
 * 描述: 单张图片压缩时的监听
 */
public interface CompressResultListener {

    //成功
    void onCompressSuccess(String imgPath);

    //失败
    void onCompressFailed(String imgPath, String error);
}
