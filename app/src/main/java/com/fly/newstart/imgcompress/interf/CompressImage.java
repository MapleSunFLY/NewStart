package com.fly.newstart.imgcompress.interf;

import com.fly.newstart.imgcompress.entity.PhotoEntity;

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
 * 包    名 : com.fly.newstart.imgcompress.interf
 * 作    者 : FLY
 * 创建时间 : 2019/5/14
 * 描述: 图片集合的压缩时的监听
 */
public interface CompressImage {

    //开始压缩
    void compress();

    //图片集合的压缩结果赶回
    interface CompressListener {
        //成功
        void onCompressSuccess(ArrayList<PhotoEntity> images);

        //失败
        void onCompressFailed(ArrayList<PhotoEntity> images, int errorNum);
    }

}
