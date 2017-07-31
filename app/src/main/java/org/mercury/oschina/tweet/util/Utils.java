package org.mercury.oschina.tweet.util;

import android.content.res.Resources;

import org.mercury.oschina.base.AppContext;

import java.util.Random;

/**
 * 类名:      Utils
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      ${TODO}
 */
public class Utils {

    public static void runOnUIThread(Runnable runnable){
        AppContext.mHandler.post(runnable);
    }

    public static String[] getStringArray(int resId){
        return getResources().getStringArray(resId);
    }

    public static Resources getResources(){
        return AppContext.context.getResources();
    }

    public static int getDimens(int resId){
        return getResources().getDimensionPixelSize(resId);
    }


    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public static int createRandomColor() {
        Random random = new Random();
        return random.nextInt(100) + 80;
    }

}
