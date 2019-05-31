package com.fly.newstart.imgcompress.compress.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
 * 包    名 : com.fly.newstart.imgcompress.compress.utils
 * 作    者 : FLY
 * 创建时间 : 2019/5/13
 * 描述: 图片压缩工具
 */
public class CompressUtils1 {

    /**
     * 压缩图片文件到Bitmap
     *
     * @param filePath 图片文件地址
     * @param quality  要压缩的质量
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int quality) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // 计算采样，现在主流手机比较多的是800*480分辨率
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        //用样本大小集解码位图
        options.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
        if (bm == null) {
            return null;
        }
        //读取图片角度
        int degree = readPictureDegree(filePath);

        //旋转位图
        bm = rotateBitmap(bm, degree);




        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        } finally {
            {
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bm;
    }

    //calculate计算
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        //raw height and width of icon
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }

        return inSampleSize;
    }

    private static int readPictureDegree(String filePath) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null) return null;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        // setting post totate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    /**
     * Bitmap对象保存成图片文件
     *
     * @param bitmap
     * @param filePath
     */
    public static void saveBitmapFile(Bitmap bitmap, String filePath) {
        File file = new File(filePath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File saveImgFile(Context context, Bitmap bitmap, String fileName) {
        if (fileName == null) {
            Log.d("CompressUtils1", "fileName is null ");
            return null;
        }
        fileName = fileName + ".png";
        String path = context.getFilesDir().getAbsolutePath();
        String lastFilePath = path + "/" + fileName;
        File file = new File(lastFilePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
