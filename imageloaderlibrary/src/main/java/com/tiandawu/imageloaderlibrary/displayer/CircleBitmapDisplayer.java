package com.tiandawu.imageloaderlibrary.displayer;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Created by tiandawu on 2017/3/17.
 */

public class CircleBitmapDisplayer implements BitmapDisplayer {
    protected final Integer strokeColor;
    protected final float strokeWidth;


    public CircleBitmapDisplayer() {
        this(null);
    }

    public CircleBitmapDisplayer(Integer strokeColor) {
        this(strokeColor, 0);
    }

    public CircleBitmapDisplayer(Integer strokeColor, float strokeWidth) {
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }


    @Override
    public void display(Bitmap bitmap, ImageView imageView) {
        if (!(imageView instanceof ImageView)) {
            throw new IllegalArgumentException("imageView illegal");
        }

        imageView.setImageDrawable(new CircleDrawable(bitmap, strokeColor, strokeWidth));
    }


    public static class CircleDrawable extends Drawable {

        protected float radius;
        protected final Paint mPaint;
        protected final Paint strokePaint;
        protected final BitmapShader mBitmapShader;
        protected final RectF mBitmapRect;
        protected final float strokeWidth;
        protected float strokeRadius;
        protected final RectF mRect = new RectF();

        public CircleDrawable(Bitmap bitmap, Integer strokeColor, float strokeWidth) {
            radius = Math.min(bitmap.getWidth(), bitmap.getHeight()) / 2;
            mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mBitmapRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setShader(mBitmapShader);
            mPaint.setFilterBitmap(true);
            mPaint.setDither(true);

            if (strokeColor == null) {
                strokePaint = null;
            } else {
                strokePaint = new Paint();
                strokePaint.setStyle(Paint.Style.STROKE);
                strokePaint.setColor(strokeColor);
                strokePaint.setStrokeWidth(strokeWidth);
                strokePaint.setAntiAlias(true);
            }
            this.strokeWidth = strokeWidth;
            strokeRadius = radius - strokeWidth / 2;
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            mRect.set(0, 0, bounds.width(), bounds.height());
            radius = Math.min(bounds.width(), bounds.height()) / 2;
            strokeRadius = radius - strokeWidth / 2;

            // Resize the original bitmap to fit the new bound
            Matrix shaderMatrix = new Matrix();
            shaderMatrix.setRectToRect(mBitmapRect, mRect, Matrix.ScaleToFit.FILL);
            mBitmapShader.setLocalMatrix(shaderMatrix);
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            canvas.drawCircle(radius, radius, radius, mPaint);
            if (strokePaint != null) {
                canvas.drawCircle(radius, radius, strokeRadius, strokePaint);
            }
        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
            mPaint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
            mPaint.setColorFilter(colorFilter);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
}

