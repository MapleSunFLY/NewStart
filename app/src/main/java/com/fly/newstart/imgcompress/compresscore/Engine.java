package com.fly.newstart.imgcompress.compresscore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.fly.newstart.imgcompress.inputstream.InputStreamProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 /**
 * 包    名 : com.fly.newstart.imgcompress.compresscore
 * 作    者 : FLY
 * 创建时间 : 2019/5/31
 * 描述: 负责启动压缩和管理活动和缓存资源。
 * @Description Responsible for starting compress and managing active and cached resources.
 */
class Engine {
    private InputStreamProvider srcImg;
    private File tagImg;
    private int srcWidth;
    private int srcHeight;
    private int maxSize;
    private boolean focusAlpha;

    Engine(InputStreamProvider srcImg, File tagImg, int maxSize, boolean focusAlpha) throws IOException {
        this.tagImg = tagImg;
        this.srcImg = srcImg;
        this.maxSize = maxSize;
        this.focusAlpha = focusAlpha;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;

        BitmapFactory.decodeStream(srcImg.open(), null, options);
        this.srcWidth = options.outWidth;
        this.srcHeight = options.outHeight;
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

    File compress() throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = computeSize();

        Bitmap tagBitmap = BitmapFactory.decodeStream(srcImg.open(), null, options);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        if (Checker.SINGLE.isJPG(srcImg.open())) {
            tagBitmap = rotatingImage(tagBitmap, Checker.SINGLE.getOrientation(srcImg.open()));
        }


        if (focusAlpha) {
            int quality = 100;
            while (stream.toByteArray().length / 1024 > maxSize) {//循环判断如果压缩后图片是否大于100kb,如果大于继续压缩
                stream.reset(); //重置baos,及清空baos
                tagBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);//这里压缩options%，吧压缩后的数据存放到baos中
                quality -= 10;//每次减少10
            }
        } else tagBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        tagBitmap.recycle();

        FileOutputStream fos = new FileOutputStream(tagImg);
        fos.write(stream.toByteArray());
        fos.flush();
        fos.close();
        stream.close();

        return tagImg;
    }
}