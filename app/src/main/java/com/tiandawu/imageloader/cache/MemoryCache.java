package com.tiandawu.imageloader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.tiandawu.imageloader.request.BitmapRequest;

/**
 * Created by tiandawu on 2017/3/13.
 */

public class MemoryCache implements BitmapCache {

    private LruCache<String, Bitmap> mLruCache;

    public MemoryCache() {
        int maxCacheSize = (int) (Runtime.getRuntime().freeMemory() / 8);
        mLruCache = new LruCache<String, Bitmap>(maxCacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        mLruCache.put(request.getImageURIMd5(), bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        return mLruCache.get(request.getImageURIMd5());
    }

    @Override
    public void remove(BitmapRequest request) {
        mLruCache.remove(request.getImageURIMd5());
    }
}
