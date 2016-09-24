package org.lion.oschina.datamanager;

import android.os.Environment;

import org.lion.oschina.global.OsChinaApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sy_heima on 2016/8/3.
 */
public class CacheManager
{
    
    private String dirFile;
    
    private CacheManager()
    {
        // sd/包名/cache
        // 多级目录是不能生成的
        dirFile = Environment.getExternalStorageDirectory().getPath() + File.separator
            + OsChinaApp.context.getPackageName() + File.separator + "cache";

        File dir = new File(dirFile);
        if (!dir.exists()) {
            //不存在创建
            dir.mkdirs();
        }
    }
    
    private static CacheManager sCacheManager = new CacheManager();
    
    public static CacheManager getInstance()
    {
        return sCacheManager;
    }
    
    // 保存数据
    public void saveData(String content,String url)
    {
        // 当前的目录
        
        // 当前的文件名
        String fileName = getFileName(url);

        try {
            System.out.println("当前的文件路径:"+dirFile+File.separator+fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(dirFile+File.separator+fileName);

            fileOutputStream.write(content.getBytes());

            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String getFileName(String url) {
        System.out.println("当前的文件名");
        StringBuffer stringBuffer = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            byte[] digest = messageDigest.digest();
            for (byte b : digest) {
                //FFFFFEE
                String hexString = Integer.toHexString(b&0xFF);
                stringBuffer.append(hexString);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return  stringBuffer.toString();
    }

    public String getData(String url) {

        File file = new File(dirFile, getFileName(url));
        if (!file.exists()) {
            return null;
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            try {
                FileInputStream fileInputStream = new FileInputStream(file);

                int len = -1;
                byte[] buffer = new byte[1024];

                while ((len = fileInputStream.read(buffer)) != -1) {
                    stringBuffer.append(new String(buffer,0,len));
                }

                fileInputStream.close();
                return stringBuffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return  null;
            }
        }
    }

    // 获取数据
    
}
