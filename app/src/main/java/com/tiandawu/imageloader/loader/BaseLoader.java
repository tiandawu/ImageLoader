package com.tiandawu.imageloader.loader;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.tiandawu.imageloader.cache.BitmapCache;
import com.tiandawu.imageloader.config.DisplayConfig;
import com.tiandawu.imageloader.core.ImageLoader;
import com.tiandawu.imageloader.request.BitmapRequest;

/**
 * Created by tiandawu on 2017/3/13.
 */

public abstract class BaseLoader implements Loader {

    private BitmapCache mBitmapCache = ImageLoader.getInstance().getConfig().getBitmapCache();
    private DisplayConfig mDisplayConfig = ImageLoader.getInstance().getConfig().getDisplayConfig();

    @Override
    public void loadImage(BitmapRequest bitmapRequest) {
        Bitmap bitmap = mBitmapCache.get(bitmapRequest);
        if (bitmap == null || bitmap.isRecycled()) {
            //如果imageURI为空，则显示空的资源
            if (TextUtils.isEmpty(bitmapRequest.getImageURI())) {
                showEmptyImageResource(bitmapRequest);
                return;
            }
            //先显示默认加载中图片
            showLoadingImage(bitmapRequest);
            //开始加载图片
            bitmap = loadBitmap(bitmapRequest);
            //缓存图片
            cacheBitmap(bitmapRequest, bitmap);
        }
        //到UI线程去显示图片
        deliveryToUIThread(bitmapRequest, bitmap);
    }

    private void showEmptyImageResource(BitmapRequest bitmapRequest) {
        final ImageView imageView = bitmapRequest.getImageView();
        if (imageView != null) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageDrawable(mDisplayConfig.getImageForEmptyUri());
                }
            });

            if (bitmapRequest.getListener() != null) {
                bitmapRequest.getListener().onLoadComplete(imageView, bitmapRequest.getImageURI(), null);
            }
        }
    }

    protected abstract Bitmap loadBitmap(BitmapRequest bitmapRequest);

    /**
     * 缓存图片
     */
    private void cacheBitmap(BitmapRequest bitmapRequest, Bitmap bitmap) {
        if (bitmapRequest != null && bitmap != null) {
            synchronized (BaseLoader.class) {
                mBitmapCache.put(bitmapRequest, bitmap);
            }
        }
    }

    /**
     * 显示图片
     */
    private void deliveryToUIThread(final BitmapRequest bitmapRequest, final Bitmap bitmap) {
        ImageView imageView = bitmapRequest.getImageView();
        if (imageView != null) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    updateImageView(bitmapRequest, bitmap);
                }
            });
        }
    }

    private void updateImageView(BitmapRequest bitmapRequest, Bitmap bitmap) {
        ImageView imageView = bitmapRequest.getImageView();
        //正常加载
        if (bitmap != null && imageView.getTag().equals(bitmapRequest.getImageURI())) {
            imageView.setImageBitmap(bitmap);
        }
        //加载失败
        if (bitmap == null && mDisplayConfig != null && mDisplayConfig.getImageOnFail() != null) {
            imageView.setImageDrawable(mDisplayConfig.getImageOnFail());
        }

        //回调监听
        if (bitmapRequest.getListener() != null) {
            bitmapRequest.getListener().onLoadComplete(imageView, bitmapRequest.getImageURI(), bitmap);
        }
    }

    private void showLoadingImage(final BitmapRequest bitmapRequest) {
        if (hasLoadingPlaceHolder()) {
            final ImageView imageView = bitmapRequest.getImageView();
            if (imageView != null) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageDrawable(mDisplayConfig.getImageOnLoading());
                    }
                });
            }
        }
    }

    private boolean hasLoadingPlaceHolder() {
        return mDisplayConfig != null && mDisplayConfig.getImageOnLoading() != null;
    }
}
