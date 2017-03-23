package com.tiandawu.imageloaderlibrary.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.tiandawu.imageloaderlibrary.request.BitmapRequest;

/**
 * Created by tiandawu on 2017/3/17.
 */

public class DoubleCache implements BitmapCache {
    private MemoryCache mMemoryCache;
    private DiskCache mDiskCache;

    public DoubleCache(Context context) {
        mMemoryCache = new MemoryCache();
        mDiskCache = DiskCache.getInstance(context);
    }

    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {
        mMemoryCache.put(bitmapRequest, bitmap);
        mDiskCache.put(bitmapRequest, bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        Bitmap bitmap = mMemoryCache.get(bitmapRequest);
        if (bitmap == null && bitmapRequest != null) {
            bitmap = mDiskCache.get(bitmapRequest);
            if (bitmap != null) {
                mMemoryCache.put(bitmapRequest, bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public boolean remove(BitmapRequest bitmapRequest) {
        if (mMemoryCache.remove(bitmapRequest) && mDiskCache.remove(bitmapRequest)) {
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        mMemoryCache.clear();
        mDiskCache.clear();
    }
}
