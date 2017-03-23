package com.tiandawu.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by tiandawu on 2017/3/13.
 */

public abstract class BitmapDecoder {

    public Bitmap decodeBitmap(int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //只读取图片的信息，不将图片真正加载到内存
        options.inJustDecodeBounds = true;
        //根据options加载bitmap,获取图片宽高到options
        decodeBitmapWithOptions(options);
        //根据ImageView的宽高，计算图片的缩放比例
        calculateSampleSizeWithOptions(options, reqWidth, reqHeight);
        return decodeBitmapWithOptions(options);
    }

    private void calculateSampleSizeWithOptions(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //从options中获取图片的宽高
        int optWidth = options.outWidth;
        int optHeight = options.outHeight;
        //默认的缩放比例
        int inSampleSize = 1;
        if (optWidth > reqWidth || optHeight > reqHeight) {
            int widthRation = Math.round((float) optWidth / (float) reqWidth);
            int heightRation = Math.round((float) optHeight / (float) reqHeight);

            inSampleSize = Math.max(widthRation, heightRation);
        }

        options.inSampleSize = inSampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        //当系统内存不足时，可以回收bitmap
        options.inPurgeable = true;
        options.inInputShareable = true;
    }

    protected abstract Bitmap decodeBitmapWithOptions(BitmapFactory.Options options);
}
