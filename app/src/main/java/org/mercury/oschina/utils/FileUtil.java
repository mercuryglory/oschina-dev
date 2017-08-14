package org.mercury.oschina.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wang.zhonghao on 2017/8/14.
 */

public class FileUtil {

    /**
     * 拷贝文件,如果目标文件不存在将会自动创建
     * @param srcFile
     * @param tarFile
     * @return
     */
    public static boolean copyFile(File srcFile, File tarFile) {
        File parentFile = tarFile.getParentFile();
        if (!parentFile.exists()) {
            if (!parentFile.mkdirs()) {
                return false;
            }
        }

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(srcFile));
            bos = new BufferedOutputStream(new FileOutputStream(tarFile));
            byte[] buffer = new byte[1024 * 4];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            close(bis, bos);
        }
        return true;

    }

    public static void close(Closeable... closeables) {
        if (closeables == null | closeables.length == 0) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
