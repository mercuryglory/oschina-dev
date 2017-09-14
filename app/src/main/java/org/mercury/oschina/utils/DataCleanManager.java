package org.mercury.oschina.utils;

import android.content.Context;

import java.io.File;

/**
 * Created by wang.zhonghao on 2017/9/14.
 */

public class DataCleanManager {

    public static void clearAppCache(Context context) {
        clearFilesCache(context);
        clearInternalCache(context);
        clearExternalCache(context);
        clearDatabases(context);
        clearSharedPreference(context);

    }

    /**
     * 删除应用缓存目录下的所有文件
     * data/data/包名/files
     *
     * @param context
     */
    public static void clearInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }


    /**
     * 删除应用文件目录下的所有文件
     * data/data/包名/cache
     *
     * @param context
     */
    public static void clearFilesCache(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    public static void clearDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/user/0/" + context.getPackageName() + "/databases"));
    }

    // TODO: 2017/9/14 shared_prefs删除失败
    public static void clearSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/user/0/" + context.getPackageName() + "/shared_prefs"));
    }

    public static void clearExternalCache(Context context) {
        deleteFilesByDirectory(context.getExternalCacheDir());
    }

    public static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    deleteFilesByDirectory(file);
                }
            }
        }
    }
}
