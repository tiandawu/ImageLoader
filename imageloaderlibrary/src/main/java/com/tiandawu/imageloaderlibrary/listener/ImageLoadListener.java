package com.tiandawu.imageloaderlibrary.listener;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by tiandawu on 2017/3/17.
 */

public interface ImageLoadListener {
    void onLoadStart(ImageView imageView);

    void onLoadComplete(ImageView imageView, Bitmap bitmap);
}
