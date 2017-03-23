package com.tiandawu.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tiandawu.imageloader.request.BitmapRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tiandawu on 2017/3/13.
 */

public class DiskCache implements BitmapCache {
    private volatile static DiskCache mDiskCache;
    private DiskLruCache mDiskLruCache;
    private String mCacheDir = "cacheImage";
    private static final int MB = 1024 * 1024;


    private DiskCache(Context context) {
        initDiskCache(context);
    }

    private void initDiskCache(Context context) {
        File directory = getDiskCache(context, mCacheDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            mDiskLruCache = DiskLruCache.open(directory, 1, 1, 50 * MB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getDiskCache(Context context, String cacheDir) {
        return new File(context.getCacheDir(), cacheDir);
    }

    public static DiskCache getInstance(Context context) {
        if (mDiskCache == null) {
            synchronized (DiskCache.class) {
                if (mDiskCache == null) {
                    mDiskCache = new DiskCache(context);
                }
            }
        }
        return mDiskCache;
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        DiskLruCache.Editor editor = null;
        OutputStream os = null;
        try {
            editor = mDiskLruCache.edit(request.getImageURIMd5());
            os = editor.newOutputStream(0);
            if (persistBitmap2Disk(bitmap, os)) {
                editor.commit();
            } else {
                editor.abort();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean persistBitmap2Disk(Bitmap bitmap, OutputStream os) {
        BufferedOutputStream bos = new BufferedOutputStream(os);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(bos);
        }
        return true;
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        InputStream inputStream = null;
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(request.getImageURIMd5());
            if (snapshot != null) {
                inputStream = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                IOUtil.closeQuietly(inputStream);
            }
        }
        return null;
    }

    @Override
    public void remove(BitmapRequest request) {
        try {
            mDiskLruCache.remove(request.getImageURIMd5());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
