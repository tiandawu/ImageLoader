package com.tiandawu.imageloaderlibrary.request;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.tiandawu.imageloaderlibrary.ImageLoader;
import com.tiandawu.imageloaderlibrary.cache.BitmapCache;
import com.tiandawu.imageloaderlibrary.config.DisplayImageConfig;
import com.tiandawu.imageloaderlibrary.decode.BitmapDecoder;
import com.tiandawu.imageloaderlibrary.loader.ImageDownLoader;
import com.tiandawu.imageloaderlibrary.utils.ImageViewHelper;
import com.tiandawu.imageloaderlibrary.utils.L;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tiandawu on 2017/3/17.
 */

public class BitmapRequestTask implements Runnable {

    private BitmapRequest mRequest;
    private ImageLoaderEngine mEngine;
    private ImageDownLoader mImageDownLoader;
    private BitmapCache mBitmapCache;
    private DisplayImageConfig mDisplayImageConfig;

    public BitmapRequestTask(BitmapRequest request, ImageLoaderEngine imageLoaderEngine) {
        this.mRequest = request;
        this.mEngine = imageLoaderEngine;
        mImageDownLoader = ImageLoader.getInstance().getConfig().getImageDownLoader();
        mBitmapCache = ImageLoader.getInstance().getConfig().getBitmapCache();
        mDisplayImageConfig = ImageLoader.getInstance().getConfig().getDisplayImageConfig();
    }

    @Override
    public void run() {
        if (mEngine.isPause()) {
            return;
        }
        try {
            showLoadingImage(mRequest);
            Bitmap bitmap = mBitmapCache.get(mRequest);
            if (bitmap != null) {
                displayImage(bitmap, mRequest.getImageView());
                return;
            }
            InputStream inputStream = mImageDownLoader.getStream(mRequest.getImageURI());
            bitmap = BitmapDecoder.decodeStreamForBitmap(inputStream,
                    ImageViewHelper.getImageViewWidth(mRequest.getImageView()),
                    ImageViewHelper.getImageViewHeight(mRequest.getImageView()));
            if (bitmap == null) {
                showLoadingFailImage(mRequest);
            } else {
                displayImage(bitmap, mRequest.getImageView());
                mBitmapCache.put(mRequest, bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
            L.e("获取图片输入流异常" + e.getStackTrace());
        }

    }


    private void displayImage(final Bitmap bitmap, ImageView imageView) {
        if (imageView != null) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    mDisplayImageConfig.getBitmapDisplayer().display(bitmap, mRequest.getImageView());
                }
            });
        } else {
            L.e("imageView is null");
        }
    }

    private void showLoadingImage(final BitmapRequest request) {
        if (hasLoadingPlaceHolder()) {
            request.getImageView().post(new Runnable() {
                @Override
                public void run() {
                    request.getImageView().setImageDrawable(mDisplayImageConfig.getImageOnLoading());
                }
            });
        }
    }

    private void showLoadingFailImage(final BitmapRequest request) {
        if (hasLoadFailPlaceHolder()) {
            request.getImageView().post(new Runnable() {
                @Override
                public void run() {
                    request.getImageView().setImageDrawable(mDisplayImageConfig.getImageOnFail());
                }
            });
        }
    }

    private boolean hasLoadingPlaceHolder() {
        return mDisplayImageConfig != null && mDisplayImageConfig.getImageOnLoading() != null;
    }

    private boolean hasLoadFailPlaceHolder() {
        return mDisplayImageConfig != null && mDisplayImageConfig.getImageOnFail() != null;
    }
}
