package com.tiandawu.imageloader.utils;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tiandawu on 2017/3/13.
 */

public class MD5Utils {

    private static MessageDigest mMessageDigest;

    private static final String TAG = MD5Utils.class.getSimpleName();

    static {
        try {
            mMessageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(TAG, "不支持MD5算法！");
        }
    }


    /**
     * MD5加密
     */
    public static String toMD5(String key) {
        if (mMessageDigest == null) {
            return String.valueOf(key.hashCode());
        }

        mMessageDigest.update(key.getBytes());

        return convert2HexString(mMessageDigest.digest());
    }

    /**
     * 转为16进制字符串
     */
    private static String convert2HexString(byte[] digest) {
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
