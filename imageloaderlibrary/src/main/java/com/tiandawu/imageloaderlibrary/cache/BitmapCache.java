package com.tiandawu.imageloaderlibrary.cache;

import android.graphics.Bitmap;

import com.tiandawu.imageloaderlibrary.request.BitmapRequest;

/**
 * Created by tiandawu on 2017/3/17.
 */

public interface BitmapCache {
    void put(BitmapRequest bitmapRequest, Bitmap bitmap);

    Bitmap get(BitmapRequest bitmapRequest);

    boolean remove(BitmapRequest bitmapRequest);

    void clear();
}
