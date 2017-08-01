package org.mercury.oschina.utils;

import org.mercury.oschina.BuildConfig;
import org.mercury.oschina.Constant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by Mercury on 2017/7/30.
 * 从SP文件中读取加密过的access_token
 */

public class AccessTokenHelper {

    public static void saveAccessToken(String userId,String accessToken) {
        SpUtils.saveString(userId, Constant.ACCESS_TOKEN, encryptBaseDes(accessToken));
    }

    public static String getAccessToken(String userId) {
        String encryptToken = SpUtils.getString(userId, Constant.ACCESS_TOKEN);
        return decryptBaseDes(encryptToken);
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
        String temp;
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

    public static String encryptBaseDes(String data) {
        String encryptData = null;
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec desKeySpec = new DESKeySpec(BuildConfig.DES_KEY.getBytes());
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            SecretKey key = factory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);
            encryptData = new BASE64Encoder().encode(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptData;
    }

    public static String decryptBaseDes(String cryptData) {
        String decryptData = null;
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec desKeySpec = new DESKeySpec(BuildConfig.DES_KEY.getBytes());
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            SecretKey key = factory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            decryptData = new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(cryptData)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptData;
    }

}
