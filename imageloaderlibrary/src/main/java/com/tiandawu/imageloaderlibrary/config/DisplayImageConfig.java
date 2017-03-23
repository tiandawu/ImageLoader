package com.tiandawu.imageloaderlibrary.config;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.tiandawu.imageloaderlibrary.displayer.BitmapDisplayer;
import com.tiandawu.imageloaderlibrary.displayer.SimpleBitmapDisplayer;

/**
 * Created by tiandawu on 2017/3/17.
 */

public class DisplayImageConfig {
    private Resources mResources;
    private int imageResOnLoading;
    private int imageResForEmptyRUI;
    private int imageResOnFail;
    private Drawable imageOnLoading;
    private Drawable imageForEmptyURI;
    private Drawable imageOnFail;
    private BitmapDisplayer mBitmapDisplayer;

    public DisplayImageConfig(Resources mResources) {
        if (mResources == null) {
            throw new IllegalArgumentException("mResources can not be null");
        }
        this.mResources = mResources;
    }

    public boolean shouldShowImageOnLoading() {
        return imageOnLoading != null || imageResOnLoading != 0;
    }

    public boolean shouldShowImageOnFail() {
        return imageOnFail != null || imageResOnFail != 0;
    }

    public boolean shouldShowImageForEmptyURI() {
        return imageForEmptyURI != null || imageResForEmptyRUI != 0;
    }

    public void setImageResOnLoading(int imageResOnLoading) {
        this.imageResOnLoading = imageResOnLoading;
    }


    public Drawable getImageOnLoading() {
        return imageResOnLoading != 0 ? mResources.getDrawable(imageResOnLoading) : imageOnLoading;
    }

    public Drawable getImageOnFail() {
        return imageResOnFail != 0 ? mResources.getDrawable(imageResOnFail) : imageOnFail;
    }

    public Drawable getImageForEmptyURI() {
        return imageResForEmptyRUI != 0 ? mResources.getDrawable(imageResForEmptyRUI) : imageForEmptyURI;
    }

    public void setImageResForEmptyRUI(int imageResForEmptyRUI) {
        this.imageResForEmptyRUI = imageResForEmptyRUI;
    }

    public void setImageResOnFail(int imageResOnFail) {
        this.imageResOnFail = imageResOnFail;
    }

    public void setImageOnLoading(Drawable iamgeOnLoading) {
        this.imageOnLoading = iamgeOnLoading;
    }

    public void setImageForEmptyURI(Drawable imageForEmptyURI) {
        this.imageForEmptyURI = imageForEmptyURI;
    }

    public void setImageOnFail(Drawable imageOnFail) {
        this.imageOnFail = imageOnFail;
    }

    public BitmapDisplayer getBitmapDisplayer() {
        return mBitmapDisplayer == null ? new SimpleBitmapDisplayer() : mBitmapDisplayer;
    }

    public void setBitmapDisplayer(BitmapDisplayer bitmapDisplayer) {
        mBitmapDisplayer = bitmapDisplayer;
    }
}
