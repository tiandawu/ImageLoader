package com.tiandawu.imageloaderlibrary.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tiandawu.imageloaderlibrary.request.BitmapRequest;
import com.tiandawu.imageloaderlibrary.utils.L;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tiandawu on 2017/3/17.
 */

public class DiskCache implements BitmapCache {

    private static DiskCache mDiskCache;
    private DiskLruCache mDiskLruCache;
    private String mCacheDir = "cacheImage";
    private static final int MB = 1024 * 1024;

    private DiskCache(Context context) {
        initDiskCache(context);
    }

    private void initDiskCache(Context context) {
        File directory = getCacheDir(context, mCacheDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            mDiskLruCache = DiskLruCache.open(directory, 1, 1, 50 * MB);
        } catch (IOException e) {
            e.printStackTrace();
            L.e("获取硬盘缓存失败");
        }
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


    private File getCacheDir(Context context, String mCacheDir) {
        return new File(context.getCacheDir(), mCacheDir);
    }

    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {
        DiskLruCache.Editor editor = null;
        OutputStream os = null;
        try {
            editor = mDiskLruCache.edit(bitmapRequest.getMD5ImageURI());
            if (editor != null) {
                os = editor.newOutputStream(0);
                if (persistBitmap2Disk(bitmap, os)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            L.e("diskcache ---put--exception");
        }
    }

    private boolean persistBitmap2Disk(Bitmap bitmap, OutputStream os) {
        BufferedOutputStream bos = new BufferedOutputStream(os);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtil.closeQuietly(bos);
        }
        return true;
    }


    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        InputStream inputStream = null;
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(bitmapRequest.getMD5ImageURI());
            if (snapshot != null) {
                inputStream = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            L.e("diskcache ---get--exception");
        } finally {
            IOUtil.closeQuietly(inputStream);
        }
        return null;
    }

    @Override
    public boolean remove(BitmapRequest bitmapRequest) {
        try {
            if (mDiskLruCache.remove(bitmapRequest.getMD5ImageURI())) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            L.e("diskcache ---remove--exception");
            return false;
        }
        return false;
    }

    @Override
    public void clear() {
        mDiskCache.clear();
    }
}
