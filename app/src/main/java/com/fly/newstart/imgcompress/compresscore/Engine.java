package com.fly.newstart.imgcompress.compresscore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.fly.newstart.imgcompress.inputstream.InputStreamProvider;
import com.shangyi.android.utils.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 包    名 : com.fly.newstart.imgcompress.compresscore
 * 作    者 : FLY
 * 创建时间 : 2019/5/31
 * 描述: 负责启动压缩和管理活动和缓存资源。
 * Responsible for starting compress and managing active and cached resources.
 */
class Engine {
    private InputStreamProvider srcImg;
    private File tagImg;
    private int srcWidth;
    private int srcHeight;
    private int maxSize;
    private int reqWidth;
    private int reqHeight;

    Engine(InputStreamProvider srcImg, File tagImg, int maxSize, int reqWidth, int reqHeight) throws IOException {
        this.tagImg = tagImg;
        this.srcImg = srcImg;
        this.maxSize = maxSize;
        this.reqWidth = reqWidth;
        this.reqHeight = reqHeight;
    }

    File compress() throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;

        BitmapFactory.decodeStream(srcImg.open(), null, options);
        this.srcWidth = options.outWidth;
        this.srcHeight = options.outHeight;

        if (reqWidth * reqHeight > 20000) {
            options.inSampleSize = calculateInSampleSize(reqWidth, reqHeight);
        } else {
            options.inSampleSize = computeSize();
        }

        options.inJustDecodeBounds = false;

        Bitmap tagBitmap = BitmapFactory.decodeStream(srcImg.open(), null, options);

        if (Checker.SINGLE.isJPG(srcImg.open())) {
            tagBitmap = rotatingImage(tagBitmap, Checker.SINGLE.getOrientation(srcImg.open()));
        }

        //   tagBitmap = compressImage(tagBitmap);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int quality = 100;
        do {
            stream.reset(); //重置baos,及清空baos
            tagBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);//这里压缩options%，吧压缩后的数据存放到baos中
            quality -= 10;//每次减少10
            LogUtils.d("FLY110",String.valueOf(quality));
        } while (stream.toByteArray().length / 1024 > maxSize && quality > 10);

        tagBitmap.recycle();

        FileOutputStream fos = new FileOutputStream(tagImg);
        fos.write(stream.toByteArray());
        fos.flush();
        fos.close();
        stream.close();

        return tagImg;
    }

    private int computeSize() {
        srcWidth = srcWidth % 2 == 1 ? srcWidth + 1 : srcWidth;
        srcHeight = srcHeight % 2 == 1 ? srcHeight + 1 : srcHeight;

        int longSide = Math.max(srcWidth, srcHeight);
        int shortSide = Math.min(srcWidth, srcHeight);

        float scale = ((float) shortSide / longSide);
        if (scale <= 1 && scale > 0.5625) {
            if (longSide < 1664) {
                return 1;
            } else if (longSide < 4990) {
                return 2;
            } else if (longSide > 4990 && longSide < 10240) {
                return 4;
            } else {
                return longSide / 1280 == 0 ? 1 : longSide / 1280;
            }
        } else if (scale <= 0.5625 && scale > 0.5) {
            return longSide / 1280 == 0 ? 1 : longSide / 1280;
        } else {
            return (int) Math.ceil(longSide / (1280.0 / scale));
        }
    }

    private Bitmap rotatingImage(Bitmap bitmap, int angle) {
        Matrix matrix = new Matrix();

        matrix.postRotate(angle);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    //像素压缩 calculate计算
    private int calculateInSampleSize(int reqWidth, int reqHeight) {
        final int height = srcWidth;
        final int width = srcHeight;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }

    //缩略像素 和像素压缩 类似
    private static Bitmap createBitmapThumbnail(Bitmap bitmap, float scale) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        Bitmap newBitMap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        return newBitMap;
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
        Bitmap bitmapNew = BitmapFactory.decodeStream(isBm, null, null); //把ByteArrayInputStream数据生成图片
        return bitmapNew;
    }
}