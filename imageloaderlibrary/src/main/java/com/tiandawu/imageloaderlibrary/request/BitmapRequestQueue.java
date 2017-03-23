package com.tiandawu.imageloaderlibrary.request;

import com.tiandawu.imageloaderlibrary.utils.L;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tiandawu on 2017/3/18.
 */

public class BitmapRequestQueue {
    private AtomicInteger mRequestID = new AtomicInteger(0);
    private BlockingQueue<BitmapRequest> mQueue = new PriorityBlockingQueue<BitmapRequest>();

    public BitmapRequest take() throws InterruptedException {
        return mQueue.take();
    }

    public void addBitmapRequest(BitmapRequest bitmapRequest) {
        if (!mQueue.contains(bitmapRequest)) {
            bitmapRequest.setRequestID(mRequestID.incrementAndGet());
            mQueue.add(bitmapRequest);
        } else {
            L.e("请求的经存在id = " + bitmapRequest.getRequestID());
        }
    }


    public boolean removeBitmapRequest(BitmapRequest bitmapRequest) {
        if (mQueue.contains(bitmapRequest)) {
            mQueue.remove(bitmapRequest);
            return true;
        } else {
            return false;
        }
    }

    public void clearBitmapRequest() {
        mQueue.clear();
    }

}
