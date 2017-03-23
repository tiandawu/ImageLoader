package com.tiandawu.imageloaderlibrary.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tiandawu.imageloaderlibrary.cache.IOUtil;
import com.tiandawu.imageloaderlibrary.utils.L;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tiandawu on 2017/3/20.
 */

public class BitmapDecoder {
    public static Bitmap decodeStreamForBitmap(InputStream inputStream, int reqWidth, int reqHeight) {

        Bitmap bitmap = null;
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = null;
        try {
            bis = new BufferedInputStream(inputStream);
            baos = new ByteArrayOutputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            byte buffer[] = new byte[1024];
            int len;
            while ((len = bis.read(buffer, 0, buffer.length)) > 0) {
                baos.write(buffer, 0, len);
            }
            byte[] imageData = baos.toByteArray();
            BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);//获取到图片的真实宽高
            caculateSampleSize(options, reqWidth, reqHeight);//计算缩放的比例
            bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
        } catch (IOException e) {
            e.printStackTrace();
            L.e("decode异常了" + e.getMessage());
        } finally {
            IOUtil.closeQuietly(bis);
            IOUtil.closeQuietly(baos);
            IOUtil.closeQuietly(inputStream);
        }
        return bitmap;
    }


    private static void caculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int relWidth = options.outWidth;
        int relHeight = options.outHeight;
        int sampleSize = 1;
        if (relWidth > relWidth || relHeight > relHeight) {
            int widthSampleSize = Math.round((float) relWidth / (float) relWidth);
            int heightSampleSize = Math.round((float) relHeight / (float) reqHeight);
            sampleSize = Math.max(widthSampleSize, heightSampleSize);
        }
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
    }

}
