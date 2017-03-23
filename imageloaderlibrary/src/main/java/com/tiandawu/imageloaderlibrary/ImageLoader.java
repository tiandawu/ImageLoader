package com.tiandawu.imageloaderlibrary;

import android.widget.ImageView;

import com.tiandawu.imageloaderlibrary.config.DisplayImageConfig;
import com.tiandawu.imageloaderlibrary.config.ImageLoaderConfig;
import com.tiandawu.imageloaderlibrary.listener.ImageLoadListener;
import com.tiandawu.imageloaderlibrary.request.BitmapRequest;
import com.tiandawu.imageloaderlibrary.request.BitmapRequestQueue;
import com.tiandawu.imageloaderlibrary.request.ImageLoaderEngine;

/**
 * Created by tiandawu on 2017/3/16.
 */

public class ImageLoader {
    public static final String TAG = ImageLoader.class.getSimpleName();
    private volatile static ImageLoader mImageLoader;
    private ImageLoaderConfig mConfig;
    private BitmapRequestQueue mRequestQueue;
    private static ImageLoaderEngine mImageLoaderEngine;

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (mImageLoader == null) {
            synchronized (ImageLoader.class) {
                if (mImageLoader == null) {
                    mImageLoader = new ImageLoader();
                }
            }
        }
        return mImageLoader;
    }

    public void init(ImageLoaderConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("config can not be null");
        }
        this.mConfig = config;
        if (mRequestQueue == null) {
            mRequestQueue = new BitmapRequestQueue();
        }

        if (mImageLoaderEngine == null) {
            mImageLoaderEngine = new ImageLoaderEngine(mConfig.getThreadPoolSize(), mRequestQueue);
        }
        mImageLoaderEngine.start();
    }

    public ImageLoaderConfig getConfig() {
        return mConfig;
    }

    public void displayImage(String uri, ImageView imageView) {
        displayImage(uri, imageView, null, null);
    }

    public void displayImage(String uri, ImageView imageView, DisplayImageConfig config, ImageLoadListener listener) {
        BitmapRequest bitmapRequest = new BitmapRequest(uri, imageView, config, listener);
        mRequestQueue.addBitmapRequest(bitmapRequest);
    }

    public void resume() {
        mImageLoaderEngine.resume();
    }

    public void pause() {
        mImageLoaderEngine.pause();
    }
}
