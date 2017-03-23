package com.tiandawu.imageloader.request;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tiandawu on 2017/3/13.
 */

public class RequestQueue {

    private final String TAG = RequestQueue.class.getSimpleName();
    private int mThreadCount;
    private AtomicInteger mThreadId = new AtomicInteger(0);
    private BitmapRequestTask[] mTasks;
    private BlockingQueue<BitmapRequest> mRequestQueue = new PriorityBlockingQueue<>();

    public RequestQueue(int threadCount) {
        this.mThreadCount = threadCount;
    }

    /**
     * 启动线程循环请求队列
     */
    public void start() {
//        //先停止请求任务
        stopRequestTask();
        //再开启请求任务
        startRequestTask();
    }

    private void stopRequestTask() {
        if (mTasks != null && mTasks.length > 0) {
            for (int i = 0; i < mTasks.length; i++) {
                mTasks[i].interrupt();
            }
        }
    }

    private void startRequestTask() {
        mTasks = new BitmapRequestTask[mThreadCount];
        for (int i = 0; i < mThreadCount; i++) {
            BitmapRequestTask mRequestTask = new BitmapRequestTask(mRequestQueue);
            mTasks[i] = mRequestTask;
            mTasks[i].start();
        }
    }

    /**
     * 向队列中添加请求
     */
    public void addRequest(BitmapRequest bitmapRequest) {
        if (!mRequestQueue.contains(bitmapRequest)) {
            bitmapRequest.setPriorityNumber(mThreadId.incrementAndGet());
            mRequestQueue.add(bitmapRequest);
        } else {
            Log.e(TAG, "当前请求已经存在：" + bitmapRequest.getPriorityNumber());
        }
    }
}
