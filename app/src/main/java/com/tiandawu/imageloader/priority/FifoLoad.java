package com.tiandawu.imageloader.priority;

import com.tiandawu.imageloader.request.BitmapRequest;

/**
 * Created by tiandawu on 2017/3/13.
 */

public class FifoLoad implements LoadPriority {
    @Override
    public int compareTo(BitmapRequest thisRequest, BitmapRequest thatRequest) {
        return thisRequest.getPriorityNumber() - thatRequest.getPriorityNumber();
    }
}
