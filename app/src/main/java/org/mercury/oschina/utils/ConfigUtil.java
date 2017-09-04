package org.mercury.oschina.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.mercury.oschina.AppContext;

/**
 * Created by wang.zhonghao on 2017/9/4.
 */

public class ConfigUtil {

    public static final String CONFIG_FILE = AccessTokenHelper.getUserId() + "_config";

    private static SharedPreferences getSharedPreferences() {
        return AppContext.context.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);

    }

    public static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }


}
