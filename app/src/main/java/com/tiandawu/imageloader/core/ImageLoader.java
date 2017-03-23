package com.tiandawu.imageloader.core;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.tiandawu.imageloader.config.DisplayConfig;
import com.tiandawu.imageloader.config.ImageLoaderConfig;
import com.tiandawu.imageloader.request.BitmapRequest;
import com.tiandawu.imageloader.request.RequestQueue;

/**
 * Created by tiandawu on 2017/3/13.
 */

public class ImageLoader {

    private ImageLoaderConfig mConfig;
    private volatile static ImageLoader mImageLoader;
    private static RequestQueue mRequestQueue;
    private static final String ERROR_INIT_CONFIG_WITH_NULL = "ImageLoader configuration can not be initialized with null";

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
            throw new IllegalArgumentException(ERROR_INIT_CONFIG_WITH_NULL);
        }
        this.mConfig = config;
        mRequestQueue = new RequestQueue(mConfig.getThreadCount());
        mRequestQueue.start();
    }


    public static void displayImage(String uri, ImageView imageView) {
        displayImage(uri, imageView, null, null);
    }

    public static void displayImage(String uri, ImageView imageView, DisplayConfig config, ImageLoadListener listener) {
        BitmapRequest bitmapRequest = new BitmapRequest(uri, imageView, config, listener);
        mRequestQueue.addRequest(bitmapRequest);
    }

    public interface ImageLoadListener {
        void onLoadComplete(ImageView imageView, String imageUrl, Bitmap bitmap);
    }

    public ImageLoaderConfig getConfig() {
        return mConfig;
    }
}

