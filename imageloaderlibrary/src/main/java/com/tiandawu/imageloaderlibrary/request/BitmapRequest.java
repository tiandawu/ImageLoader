package com.tiandawu.imageloaderlibrary.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.tiandawu.imageloaderlibrary.ImageLoader;
import com.tiandawu.imageloaderlibrary.config.DisplayImageConfig;
import com.tiandawu.imageloaderlibrary.listener.ImageLoadListener;
import com.tiandawu.imageloaderlibrary.policy.LoadPolicy;
import com.tiandawu.imageloaderlibrary.utils.MD5Utils;

import java.lang.ref.SoftReference;

/**
 * Created by tiandawu on 2017/3/17.
 */

public class BitmapRequest implements Comparable<BitmapRequest> {
    private int mRequestID;
    private String mImageURI;
    private String mMD5ImageURI;
    private SoftReference<ImageView> mImageViewSoft;
    private DisplayImageConfig mDisplayImageConfig;
    private ImageLoadListener mImageLoadListener;
    private LoadPolicy mLoadPolicy = ImageLoader.getInstance().getConfig().getLoadPolicy();

    public BitmapRequest(String imageURI, ImageView imageView, DisplayImageConfig displayImageConfig, ImageLoadListener listener) {
        imageView.setTag(imageURI);
        mImageViewSoft = new SoftReference<ImageView>(imageView);
        this.mImageURI = imageURI;
        this.mMD5ImageURI = MD5Utils.toMD5(imageURI);
        this.mDisplayImageConfig = displayImageConfig;
        this.mImageLoadListener = listener;
    }

    public int getRequestID() {
        return mRequestID;
    }

    public void setRequestID(int requestID) {
        mRequestID = requestID;
    }

    public String getImageURI() {
        return mImageURI;
    }

    public DisplayImageConfig getDisplayImageConfig() {
        return mDisplayImageConfig;
    }

    public ImageLoadListener getImageLoadListener() {
        return mImageLoadListener;
    }


    public ImageView getImageView() {
        return mImageViewSoft.get();
    }

    public String getMD5ImageURI() {
        return mMD5ImageURI;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BitmapRequest that = (BitmapRequest) o;

        if (mRequestID != that.mRequestID) return false;
        return mLoadPolicy != null ? mLoadPolicy.equals(that.mLoadPolicy) : that.mLoadPolicy == null;

    }

    @Override
    public int hashCode() {
        int result = mRequestID;
        result = 31 * result + (mLoadPolicy != null ? mLoadPolicy.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(@NonNull BitmapRequest o) {
        return mLoadPolicy.compareTo(this, o);
    }
}
