package org.lion.oschina.general.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.lion.oschina.base.AppContext;
import org.lion.oschina.utils.Constants;

import java.util.Map;

/**
 * Created by more on 2016-08-17 20:06:53.
 */
public class GeneralUtils {
    public static void writeVisitedItem(String href) {
        getSharedPreferences()
                .edit()
                .putString(href, href)
                .commit();
        if (AppContext.mAllVisitedItem != null) {
            AppContext.mAllVisitedItem.put(href, href);
        }
    }

    private static SharedPreferences getSharedPreferences() {
        return AppContext.mContext
                .getSharedPreferences(Constants.SP_VISITED_NEWS, Context.MODE_PRIVATE);
    }

    public static Map<String, String> getAllVisitedItem() {
        return (Map<String, String>) getSharedPreferences().getAll();
    }

    public static int getColor(int colorResId) {
        return AppContext.mContext.getResources().getColor(colorResId);
    }

}
