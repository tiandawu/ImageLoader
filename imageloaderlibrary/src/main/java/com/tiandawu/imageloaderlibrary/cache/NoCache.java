package com.tiandawu.imageloaderlibrary.cache;

import android.graphics.Bitmap;

import com.tiandawu.imageloaderlibrary.request.BitmapRequest;

/**
 * Created by tiandawu on 2017/3/17.
 */

public class NoCache implements BitmapCache {

    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {

    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        return null;
    }

    @Override
    public boolean remove(BitmapRequest bitmapRequest) {
        return false;
    }

    @Override
    public void clear() {

    }
}
