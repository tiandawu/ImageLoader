package com.tiandawu.imageloaderlibrary.request;

import com.tiandawu.imageloaderlibrary.utils.L;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by tiandawu on 2017/3/17.
 */

public class ImageLoaderEngine {

    private AtomicBoolean pause = new AtomicBoolean(false);
    private Executor mExecutor;
    private BitmapRequestQueue mRequestQueue;

    public ImageLoaderEngine(int threadPoolSize, BitmapRequestQueue requestQueue) {
        if (threadPoolSize <= 0) {
            threadPoolSize = Runtime.getRuntime().availableProcessors();
        }
        mRequestQueue = requestQueue;
        mExecutor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void start() {

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        BitmapRequest request = mRequestQueue.take();
                        BitmapRequestTask task = new BitmapRequestTask(request, ImageLoaderEngine.this);
                        mExecutor.execute(task);
                        L.e("--------------looping--------------");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        L.e("ImageLoaderEngine---------InterruptedException");
                    }
                }
            }
        }.start();
    }


    public void pause() {
        pause.set(true);
    }

    public void resume() {
        pause.set(false);
    }

    public boolean isPause() {
        return pause.get();
    }
}
