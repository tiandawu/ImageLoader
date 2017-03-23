package com.tiandawu.imageloaderlibrary.config;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.tiandawu.imageloaderlibrary.cache.BitmapCache;
import com.tiandawu.imageloaderlibrary.cache.DoubleCache;
import com.tiandawu.imageloaderlibrary.displayer.BitmapDisplayer;
import com.tiandawu.imageloaderlibrary.loader.BaseImageDownLoader;
import com.tiandawu.imageloaderlibrary.loader.ImageDownLoader;
import com.tiandawu.imageloaderlibrary.policy.FIFOPolicy;
import com.tiandawu.imageloaderlibrary.policy.LoadPolicy;

/**
 * Created by tiandawu on 2017/3/17.
 */

public class ImageLoaderConfig {
    private final Context mContext;
    private final int mThreadPoolSize;
    private final BitmapCache mBitmapCache;
    private final LoadPolicy mLoadPolicy;
    private final DisplayImageConfig mDisplayImageConfig;
    private final ImageDownLoader mImageDownLoader;


    private ImageLoaderConfig(Builder builder) {

        this.mContext = builder.mContext;

        if (builder.mThreadPoolSize <= 0) {
            this.mThreadPoolSize = Runtime.getRuntime().availableProcessors();
        } else {
            this.mThreadPoolSize = builder.mThreadPoolSize;
        }

        if (builder.mBitmapCache == null) {
            this.mBitmapCache = new DoubleCache(mContext);
        } else {
            this.mBitmapCache = builder.mBitmapCache;
        }

        if (builder.mLoadPolicy == null) {
            this.mLoadPolicy = new FIFOPolicy();
        } else {
            this.mLoadPolicy = builder.mLoadPolicy;
        }

        if (builder.mDisplayImageConfig == null) {
            this.mDisplayImageConfig = new DisplayImageConfig(mContext.getResources());
        } else {
            this.mDisplayImageConfig = builder.mDisplayImageConfig;
        }

        if (builder.mImageDownLoader == null) {
            this.mImageDownLoader = new BaseImageDownLoader(mContext);
        } else {
            this.mImageDownLoader = builder.mImageDownLoader;
        }
    }

    public static class Builder {
        private Context mContext;
        private int mThreadPoolSize;
        private BitmapCache mBitmapCache;
        private BitmapDisplayer mBitmapDisplayer;
        private LoadPolicy mLoadPolicy;
        private DisplayImageConfig mDisplayImageConfig;
        private ImageDownLoader mImageDownLoader;

        public Builder(Context context) {
            mContext = context.getApplicationContext();
            mDisplayImageConfig = new DisplayImageConfig(mContext.getResources());
        }

        public Builder setThreadCount(int threadPoolSize) {
            this.mThreadPoolSize = threadPoolSize;
            return this;
        }

        public Builder setBitmapCache(BitmapCache bitmapCache) {
            this.mBitmapCache = bitmapCache;
            return this;
        }

        public Builder setImageDownLoader(ImageDownLoader imageDownLoader) {
            this.mImageDownLoader = imageDownLoader;
            return this;
        }

        public Builder setBitmapDisplayer(BitmapDisplayer displayer) {
            this.mDisplayImageConfig.setBitmapDisplayer(displayer);
            return this;
        }

        public Builder setLoadPolicy(LoadPolicy policy) {
            this.mLoadPolicy = policy;
            return this;
        }

        public Builder setImageOnLoading(int resID) {
            this.mDisplayImageConfig.setImageResOnLoading(resID);
            return this;
        }

        public Builder setImageOnFail(int resID) {
            this.mDisplayImageConfig.setImageResOnFail(resID);
            return this;
        }

        public Builder setImageForEmptyURI(int resID) {
            this.mDisplayImageConfig.setImageResForEmptyRUI(resID);
            return this;
        }

        public Builder setImageOnLoading(Drawable drawable) {
            this.mDisplayImageConfig.setImageOnLoading(drawable);
            return this;
        }

        public Builder setImageOnFail(Drawable drawable) {
            this.mDisplayImageConfig.setImageOnFail(drawable);
            return this;
        }

        public Builder setImageForEmptyURI(Drawable drawable) {
            this.mDisplayImageConfig.setImageForEmptyURI(drawable);
            return this;
        }

        public ImageLoaderConfig build() {
            return new ImageLoaderConfig(this);
        }
    }

    public Context getContext() {
        return mContext;
    }

    public int getThreadPoolSize() {
        return mThreadPoolSize;
    }

    public BitmapCache getBitmapCache() {
        return mBitmapCache;
    }


    public LoadPolicy getLoadPolicy() {
        return mLoadPolicy;
    }

    public DisplayImageConfig getDisplayImageConfig() {
        return mDisplayImageConfig;
    }

    public ImageDownLoader getImageDownLoader() {
        return mImageDownLoader;
    }
}
