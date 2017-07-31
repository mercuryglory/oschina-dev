package org.mercury.oschina.utils;

import org.mercury.oschina.Constant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Mercury on 2017/7/30.
 * 从SP文件中读取加密过的access_token
 */

public class AccessTokenHelper {

    public static void saveAccessToken(String userId,String accessToken) {
        SpUtils.saveString(userId, Constant.ACCESS_TOKEN, encryptToSHA(accessToken));
    }

    public static String getAccessToken(String userId) {
        return SpUtils.getString(userId, "access_token");
    }

    //将string字符串转为sha-1
    public static String encryptToSHA(String info) {
        byte[] digest = null;
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(info.getBytes());
            digest = instance.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return byte2hex(digest);
    }

    private static String byte2hex(byte[] digest) {
        String hs = "";
        String temp = "";
        for (int i = 0; i < digest.length; i++) {
            temp = Integer.toHexString(digest[i] & 0XFF);
            if (temp.length() == 1) {
                hs = hs + "0" + temp;
            } else {
                hs = hs + temp;
            }
        }
        return hs;
    }
}
