package org.mercury.oschina.explorer.util;

import android.content.res.Resources;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.mercury.oschina.AppContext;

import java.util.Random;

/**
 * Created by Mercury on 2016/5/19.
 */
public class Utils {
    public static void setImage(String url, ImageView iv){
        //显示图片
//        Uri uri = UriUtil.parseUriOrNull(url);
        iv.setMaxHeight(200);
        Glide.with(AppContext.context()) // 指定Context
                .load(url)// 指定图片的URL
                .fitCenter()
                .into(iv);
    }
    public static int createTextSize() {
        Random random = new Random();
        return random.nextInt(10)+14;
    }

    public static int createColor() {
        Random random = new Random();
        return random.nextInt(100) + 90;
    }
    public static void runOnUIThread(Runnable runnable){
        AppContext.mHandler.post(runnable);
    }

    public static String[] getStringArray(int resId){
        return getResources().getStringArray(resId);
    }

    public static Resources getResources(){
        return AppContext.context().getResources();
    }

    public static int getDimens(int resId){
        return getResources().getDimensionPixelSize(resId);
    }


    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }
}
