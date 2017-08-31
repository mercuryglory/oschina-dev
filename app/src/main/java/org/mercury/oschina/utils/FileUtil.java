package org.mercury.oschina.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

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

    /**
     * 保存图片到SD卡下的指定目录
     * @param context
     * @param filePath
     * @param bitmap
     * @param quality
     * @throws IOException
     */
    public static void saveImgToSD(Context context, String filePath, Bitmap bitmap, int quality) throws IOException {
        if (bitmap != null) {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, bos);
            bos.flush();
            bos.close();
            if (context != null) {
                //保存到本地成功后,给系统发送广播通知扫描媒体文件,这样相册中能马上看到更新后的文件,而不是重启后才看到
                scanPhoto(context, filePath);
            }
        }
    }

    private static void scanPhoto(Context context, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
        }
    }
}
