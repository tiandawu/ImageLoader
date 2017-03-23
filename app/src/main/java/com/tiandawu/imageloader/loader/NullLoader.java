package com.tiandawu.imageloader.loader;

import android.graphics.Bitmap;

import com.tiandawu.imageloader.request.BitmapRequest;

/**
 * Created by tiandawu on 2017/3/13.
 */

public class NullLoader extends BaseLoader {

    @Override
    protected Bitmap loadBitmap(BitmapRequest bitmapRequest) {
        return null;
    }
}
