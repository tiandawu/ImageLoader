package com.tiandawu.imageloader.priority;

import com.tiandawu.imageloader.request.BitmapRequest;

/**
 * Created by tiandawu on 2017/3/13.
 */

public interface LoadPriority {
    int compareTo(BitmapRequest thisRequest, BitmapRequest thatRequest);
}
