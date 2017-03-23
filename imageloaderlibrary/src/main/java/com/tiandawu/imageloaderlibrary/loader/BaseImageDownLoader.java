package com.tiandawu.imageloaderlibrary.loader;


import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tiandawu on 2017/3/19.
 */

public class BaseImageDownLoader implements ImageDownLoader {

    protected static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    protected static final int DEFAULT_HTTP_CONNECT_TIMEOUT = 5 * 1000; // milliseconds
    protected static final int DEFAULT_HTTP_READ_TIMEOUT = 20 * 1000; // milliseconds
    protected static final int MAX_REDIRECT_COUNT = 5;
    protected final int connectTimeout;
    protected final int readTimeout;
    protected Context mContext;


    public BaseImageDownLoader(Context context) {
        this(context, DEFAULT_HTTP_CONNECT_TIMEOUT, DEFAULT_HTTP_READ_TIMEOUT);
    }

    public BaseImageDownLoader(Context context, int connectTimeout, int readTimeout) {
        this.mContext = context;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }


    @Override
    public InputStream getStream(String imageUri) throws IOException {
        switch (Scheme.ofUri(imageUri)) {
            case HTTP:
            case HTTPS:
                return getStreamFromURL(imageUri);
            case FILE:
                return getStreamFromFile(imageUri);
            case UNKNOWN:
            default:
                return getStreamFromOtherSource(imageUri);

        }
    }

    private InputStream getStreamFromURL(String imageUri) throws IOException {
        HttpURLConnection conn = createConnection(imageUri);
        int redirectCount = 0;
        while (conn.getResponseCode() / 100 == 3 && redirectCount < MAX_REDIRECT_COUNT) {
            conn = createConnection(conn.getHeaderField("Location"));
            redirectCount++;
        }
        return conn.getInputStream();
    }

    private HttpURLConnection createConnection(String imageUri) throws IOException {
        String encodeURL = Uri.encode(imageUri, ALLOWED_URI_CHARS);
        HttpURLConnection conn = (HttpURLConnection) new URL(encodeURL).openConnection();
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        return conn;
    }

    private InputStream getStreamFromFile(String imageUri) throws FileNotFoundException {
        String filePath = Scheme.FILE.crop(imageUri);
        if (isVideoFileURI(imageUri)) {
            return getVideoThumbnailStream(filePath);
        } else {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
            return bis;
        }
    }

    private boolean isVideoFileURI(String imageUri) {
        String extention = MimeTypeMap.getFileExtensionFromUrl(imageUri);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extention);
        return mimeType != null && mimeType.startsWith("video/");
    }

    private InputStream getVideoThumbnailStream(String filePath) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
            if (bitmap != null) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                return new ByteArrayInputStream(bos.toByteArray());
            }
        }
        return null;
    }

    private InputStream getStreamFromOtherSource(String imageUri) {
        throw new UnsupportedOperationException("获取输入流失败");
    }

}
