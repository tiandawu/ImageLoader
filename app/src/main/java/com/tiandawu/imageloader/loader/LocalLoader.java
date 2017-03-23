package com.tiandawu.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.tiandawu.imageloader.request.BitmapRequest;
import com.tiandawu.imageloader.utils.ImageViewHelper;

import java.io.File;

/**
 * Created by tiandawu on 2017/3/13.
 */

public class LocalLoader extends BaseLoader {
    @Override
    protected Bitmap loadBitmap(BitmapRequest bitmapRequest) {
        final String path = Uri.parse(bitmapRequest.getImageURI()).getPath();
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        BitmapDecoder decoder = new BitmapDecoder() {
            @Override
            protected Bitmap decodeBitmapWithOptions(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(path, options);
            }
        };
        return decoder.decodeBitmap(ImageViewHelper.getImageViewWidth(bitmapRequest.getImageView()),
                ImageViewHelper.getImageViewHeight(bitmapRequest.getImageView()));
    }
}
