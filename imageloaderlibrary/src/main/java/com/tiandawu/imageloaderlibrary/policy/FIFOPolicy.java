package com.tiandawu.imageloaderlibrary.policy;

import com.tiandawu.imageloaderlibrary.request.BitmapRequest;

/**
 * Created by tiandawu on 2017/3/17.
 */

public class FIFOPolicy implements LoadPolicy {
    @Override
    public int compareTo(BitmapRequest thisRequest, BitmapRequest anotherRequest) {
        return thisRequest.getRequestID() - anotherRequest.getRequestID();
    }
}
