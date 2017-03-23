package com.tiandawu.imageloader.request;

import android.util.Log;

import com.tiandawu.imageloader.loader.Loader;
import com.tiandawu.imageloader.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

/**
 * Created by tiandawu on 2017/3/13.
 */

public class BitmapRequestTask extends Thread {
    private static final String TAG = BitmapRequestTask.class.getSimpleName();
    private BlockingQueue<BitmapRequest> mRequestQueue;

    public BitmapRequestTask(BlockingQueue<BitmapRequest> requestQueue) {
        this.mRequestQueue = requestQueue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                BitmapRequest bitmapRequest = mRequestQueue.take();
                String schema = parseSchema(bitmapRequest.getImageURI());
                Loader loader = LoaderManager.getInstance().getLoader(schema);
                loader.loadImage(bitmapRequest);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String parseSchema(String imageURI) {
        if (imageURI.contains("://")) {
            return imageURI.split("://")[0];
        } else {
            Log.e(TAG, "暂不支持该类型");
            return null;
        }
    }
}
