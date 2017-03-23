package com.tiandawu.imageloaderlibrary.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.tiandawu.imageloaderlibrary.request.BitmapRequest;

/**
 * Created by tiandawu on 2017/3/17.
 */

public class MemoryCache implements BitmapCache {

    private LruCache<String, Bitmap> mLruCache;

    public MemoryCache() {
        int size = (int) (Runtime.getRuntime().freeMemory() / 8);
        mLruCache = new LruCache<String, Bitmap>(size) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    @Override

    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {
        mLruCache.put(bitmapRequest.getMD5ImageURI(), bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        return mLruCache.get(bitmapRequest.getMD5ImageURI());
    }

    @Override
    public boolean remove(BitmapRequest bitmapRequest) {
        try {
            mLruCache.remove(bitmapRequest.getMD5ImageURI());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void clear() {
    }
}
