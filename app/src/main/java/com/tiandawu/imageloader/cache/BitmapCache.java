package com.tiandawu.imageloader.cache;

import android.graphics.Bitmap;

import com.tiandawu.imageloader.request.BitmapRequest;

/**
 * Created by tiandawu on 2017/3/13.
 */

public interface BitmapCache {

    void put(BitmapRequest request, Bitmap bitmap);

    Bitmap get(BitmapRequest request);

    void remove(BitmapRequest request);
}
