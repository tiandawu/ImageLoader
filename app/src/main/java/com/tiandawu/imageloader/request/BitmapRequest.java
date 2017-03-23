package com.tiandawu.imageloader.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.tiandawu.imageloader.config.DisplayConfig;
import com.tiandawu.imageloader.core.ImageLoader;
import com.tiandawu.imageloader.priority.LoadPriority;
import com.tiandawu.imageloader.utils.MD5Utils;

import java.lang.ref.SoftReference;

/**
 * Created by tiandawu on 2017/3/13.
 */

public class BitmapRequest implements Comparable<BitmapRequest> {
    private int mPriorityNumber;
    private String mImageURI;
    private String mImageURIMd5;
    private DisplayConfig mDisplayConfig;
    private SoftReference<ImageView> mImageViewSoft;
    private ImageLoader.ImageLoadListener mListener;
    private LoadPriority loadPriority = ImageLoader.getInstance().getConfig().getLoadPriority();

    public BitmapRequest(String uri, ImageView imageView, DisplayConfig config, ImageLoader.ImageLoadListener listener) {
        imageView.setTag(uri);
        mImageViewSoft = new SoftReference<ImageView>(imageView);
        this.mImageURI = uri;
        this.mImageURIMd5 = MD5Utils.toMD5(mImageURI);
        this.mListener = listener;
        if (config != null) {
            this.mDisplayConfig = config;
        }
    }

    public void setPriorityNumber(int priorityNumber) {
        mPriorityNumber = priorityNumber;
    }

    public int getPriorityNumber() {
        return mPriorityNumber;
    }

    public String getImageURI() {
        return mImageURI;
    }

    public DisplayConfig getDisplayConfig() {
        return mDisplayConfig;
    }

    public ImageView getImageView() {
        return mImageViewSoft.get();
    }

    public ImageLoader.ImageLoadListener getListener() {
        return mListener;
    }

    public LoadPriority getLoadPriority() {
        return loadPriority;
    }

    public String getImageURIMd5() {
        return mImageURIMd5;
    }

    @Override
    public int compareTo(@NonNull BitmapRequest o) {
        return loadPriority.compareTo(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BitmapRequest that = (BitmapRequest) o;

        if (mPriorityNumber != that.mPriorityNumber) return false;
        return loadPriority != null ? loadPriority.equals(that.loadPriority) : that.loadPriority == null;

    }

    @Override
    public int hashCode() {
        int result = mPriorityNumber;
        result = 31 * result + (loadPriority != null ? loadPriority.hashCode() : 0);
        return result;
    }
}
