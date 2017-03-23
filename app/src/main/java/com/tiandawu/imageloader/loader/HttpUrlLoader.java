package com.tiandawu.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.tiandawu.imageloader.request.BitmapRequest;
import com.tiandawu.imageloader.utils.ImageViewHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tiandawu on 2017/3/13.
 */

public class HttpUrlLoader extends BaseLoader {
    @Override
    protected Bitmap loadBitmap(final BitmapRequest bitmapRequest) {
        downloadImageByURI(bitmapRequest.getImageURI(), getCacheFile(bitmapRequest.getImageURIMd5()));
//        final InputStream inputStream = getBitmapStream(bitmapRequest.getImageURI());
//        if (inputStream == null) {
//            return null;
//        }
//        final BufferedInputStream bis = new BufferedInputStream(inputStream);
//        try {
//            bis.mark(inputStream.available());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        BitmapDecoder decoder = new BitmapDecoder() {
//            @Override
//            protected Bitmap decodeBitmapWithOptions(BitmapFactory.Options options) {
//                Bitmap bitmap = BitmapFactory.decodeStream(bis, null, options);
//                if (options.inJustDecodeBounds) {
//                    // 第一次只读取图片的快高后
//                    try {
//                        bis.reset();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    //将图片真正加载到内存后
//                    try {
//                        bis.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                return bitmap;
//            }
//        };

        BitmapDecoder decoder = new BitmapDecoder() {
            @Override
            protected Bitmap decodeBitmapWithOptions(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(getCacheFile(bitmapRequest.getImageURIMd5()).getAbsolutePath(), options);
            }
        };
        return decoder.decodeBitmap(ImageViewHelper.getImageViewWidth(bitmapRequest.getImageView()),
                ImageViewHelper.getImageViewHeight(bitmapRequest.getImageView()));
    }

    private InputStream getBitmapStream(String imageURI) {
        InputStream is = null;
        try {
            URL url = new URL(imageURI);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            return is;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    is = null;
                }
            }
        }
        return null;
    }

    public static boolean downloadImageByURI(String urlStr, File file) {
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            is = conn.getInputStream();
            fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                is = null;
            }

            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                fos = null;
            }
        }

        return false;

    }


    private File getCacheFile(String uniquePath) {
        File file = new File(Environment.getExternalStorageDirectory(), "ImageLoader");
        if (!file.exists()) {
            file.mkdir();
        }
        return new File(file, uniquePath);
    }
}
