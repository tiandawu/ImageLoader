package com.tiandawu.imageloader.config;

import android.graphics.drawable.Drawable;

import com.tiandawu.imageloader.cache.BitmapCache;
import com.tiandawu.imageloader.cache.MemoryCache;
import com.tiandawu.imageloader.priority.FifoLoad;
import com.tiandawu.imageloader.priority.LoadPriority;

/**
 * Created by tiandawu on 2017/3/13.
 */

public class ImageLoaderConfig {
    //线程数
    private final int mThreadCount;
    //加载显示图片的配置
    private final DisplayConfig mDisplayConfig;
    //加载顺序：FIFO和LIFO
    private final LoadPriority mLoadPriority;
    //图片的缓存策略
    private final BitmapCache mBitmapCache;

    public ImageLoaderConfig(Builder builder) {
        this.mDisplayConfig = builder.mDisplayConfig;
        if (builder.mBitmapCache == null) {
            this.mBitmapCache = new MemoryCache();
        } else {
            this.mBitmapCache = builder.mBitmapCache;
        }

        if (builder.mLoadPriority == null) {
            this.mLoadPriority = new FifoLoad();
        } else {
            this.mLoadPriority = builder.mLoadPriority;
        }

        if (builder.mThreadCount <= 0) {
            this.mThreadCount = Runtime.getRuntime().availableProcessors();
        } else {
            this.mThreadCount = builder.mThreadCount;
        }
    }


    public static class Builder {
        private int mThreadCount;
        private LoadPriority mLoadPriority;
        private BitmapCache mBitmapCache;
        private DisplayConfig mDisplayConfig;

        public Builder() {
            this.mDisplayConfig = new DisplayConfig();
        }

        public Builder setThreadCount(int threadCount) {
            this.mThreadCount = threadCount;
            return this;
        }

        public Builder setLoadPriority(LoadPriority loadPriority) {
            this.mLoadPriority = loadPriority;
            return this;
        }

        public Builder setBitmapCache(BitmapCache bitmapCache) {
            this.mBitmapCache = bitmapCache;
            return this;
        }

        public Builder setImageOnLoading(Drawable drawable) {
            this.mDisplayConfig.setImageOnLoading(drawable);
            return this;
        }

        public Builder setImageOnFail(Drawable drawable) {
            this.mDisplayConfig.setImageOnFail(drawable);
            return this;
        }

        public Builder setImageForEmpty(Drawable drawable) {
            this.mDisplayConfig.setImageForEmptyUri(drawable);
            return this;
        }

        public ImageLoaderConfig build() {
            return new ImageLoaderConfig(this);
        }
    }

    public int getThreadCount() {
        return mThreadCount;
    }

    public DisplayConfig getDisplayConfig() {
        return mDisplayConfig;
    }

    public LoadPriority getLoadPriority() {
        return mLoadPriority;
    }

    public BitmapCache getBitmapCache() {
        return mBitmapCache;
    }
}

