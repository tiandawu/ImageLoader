package com.tiandawu.imageloader.config;

import android.graphics.drawable.Drawable;

/**
 * Created by tiandawu on 2017/3/13.
 */

public class DisplayConfig {

    private Drawable imageOnLoading;
    private Drawable imageOnFail;
    private Drawable imageForEmptyUri;


    public Drawable getImageOnLoading() {
        return imageOnLoading;
    }

    public void setImageOnLoading(Drawable imageOnLoading) {
        this.imageOnLoading = imageOnLoading;
    }

    public Drawable getImageOnFail() {
        return imageOnFail;
    }

    public void setImageOnFail(Drawable imageOnFail) {
        this.imageOnFail = imageOnFail;
    }

    public Drawable getImageForEmptyUri() {
        return imageForEmptyUri;
    }

    public void setImageForEmptyUri(Drawable imageForEmptyUri) {
        this.imageForEmptyUri = imageForEmptyUri;
    }
}
