package com.fly.newstart.imgcompress.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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
 * 创建时间 : 2019/5/13
 * 描述: 图片压缩工具
 */
public class CompressUtils2 {

    //按大小缩放
    private Bitmap getImage(String srcPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //开始读取图片，此时吧 options.inJustDecodeBounds 设置true
        options.inJustDecodeBounds = true;
        Bitmap bitmap;//此时返回bm为空
        options.inJustDecodeBounds = false;
        float w = options.outWidth;
        float h = options.outHeight;

        float hh = 800f;
        float ww = 480f;
        //缩放比，由于是固定比列缩放，只用宽或者高其中之一个数据进行计算
        int be = 1;//1 表示不缩放+
        if (w > h && w > ww) {
            be = (int) (w / ww);
        } else if (w < h && h > hh) {
            be = (int) (h / hh);
        }
        if (be <= 0) be = 1;
        options.inSampleSize = be;//设置缩放比列

        bitmap = BitmapFactory.decodeFile(srcPath, options);//重新读取图片
        return compressImage(bitmap);
    }

    //图片质量压缩
    private Bitmap compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {//循环判断如果压缩后图片是否大于100kb,如果大于继续压缩
            baos.reset(); //重置baos,及清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，吧压缩后的数据存放到baos中
            options -= 10;//每次减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmapNew = BitmapFactory.decodeStream(isBm,null,null); //把ByteArrayInputStream数据生成图片
        return bitmapNew;
    }

    //图片按比例大小压缩
    private  Bitmap comp(Bitmap image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,baos);
        if (baos.toByteArray().length/1024>1024){//判断图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream()时溢出）
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG,50,baos);//压缩百分之五十，吧压缩后的数据存放到baos上
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读取图片，此时吧options.injustDecodeBounds 设置为true
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm,null,newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        float h = newOpts.outHeight;

        float hh = 800f;
        float ww = 480f;
        //缩放比，由于是固定比列缩放，只用宽或者高其中之一个数据进行计算
        int be = 1;//1 表示不缩放+
        if (w > h && w > ww) {
            be = (int) (w / ww);
        } else if (w < h && h > hh) {
            be = (int) (h / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比列
        //重新读入图片，注意此时newOpts.inJustDecodeBounds = false;
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm,null,newOpts);
        return compressImage(bitmap);
    }
}
