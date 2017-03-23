package com.tiandawu.imageloaderlibrary.displayer;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by tiandawu on 2017/3/17.
 */

public class SimpleBitmapDisplayer implements BitmapDisplayer {
    @Override
    public void display(Bitmap bitmap, ImageView imageView) {
        imageView.setImageBitmap(bitmap);
    }
}
