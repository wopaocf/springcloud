package com.itwq.xdvideo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 常用工具类的封装，MD5 uuid
 */
public class CommonUtils {
    /**
     * 生成uuid
     *
     * @return
     */
    public static String generateUUID() {
        String uuid = UUID.randomUUID().toString()
                .replaceAll("-", "").substring(0, 32);
        return uuid;
    }

    /**
     * md5工具类
     * @param data
     * @return
     */
    public static String MD5(String data) {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] array = md5.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
       return null;
    }
}
