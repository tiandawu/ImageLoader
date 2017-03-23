package com.tiandawu.imageloaderlibrary.policy;

import com.tiandawu.imageloaderlibrary.request.BitmapRequest;

/**
 * Created by tiandawu on 2017/3/17.
 */

public interface LoadPolicy {
    int compareTo(BitmapRequest thisRequest,BitmapRequest anotherRequest);
}
