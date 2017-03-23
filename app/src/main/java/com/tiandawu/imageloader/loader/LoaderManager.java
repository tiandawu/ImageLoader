package com.tiandawu.imageloader.loader;

import java.util.HashMap;

/**
 * Created by tiandawu on 2017/3/13.
 */

public class LoaderManager {

    private volatile static LoaderManager mLoaderManager;
    private HashMap<String, Loader> mLoaderHashMap = new HashMap<>();
    private static final String HTTP_LOADER = "http";
    private static final String HTTPS_LOADER = "https";
    private static final String FILE_LOADER = "file";

    private LoaderManager() {
        registerLoader(HTTP_LOADER, new HttpUrlLoader());
        registerLoader(HTTPS_LOADER, new HttpUrlLoader());
        registerLoader(FILE_LOADER, new LocalLoader());
    }

    private void registerLoader(String schema, Loader loader) {
        mLoaderHashMap.put(schema, loader);
    }

    public static LoaderManager getInstance() {
        if (mLoaderManager == null) {
            synchronized (LoaderManager.class) {
                if (mLoaderManager == null) {
                    mLoaderManager = new LoaderManager();
                }
            }
        }
        return mLoaderManager;
    }


    public Loader getLoader(String schema) {
        if (mLoaderHashMap.containsKey(schema)) {
            return mLoaderHashMap.get(schema);
        } else {
            return new NullLoader();
        }
    }

}
