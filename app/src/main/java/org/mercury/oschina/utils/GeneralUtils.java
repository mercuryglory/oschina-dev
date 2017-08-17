package org.mercury.oschina.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.mercury.oschina.AppContext;

import java.util.Map;

/**
 * Created by more on 2016-08-17 20:06:53.
 */
public class GeneralUtils {

    public static void writeVisitedItem(int id) {

        getSharedPreferences()
                .edit()
                .putInt(String.valueOf(id), id)
                .apply();
        if (getAllVisitedItem() != null && !getAllVisitedItem().containsKey(id)) {
            getAllVisitedItem().put(String.valueOf(id), id);
        }
    }

    private static SharedPreferences getSharedPreferences() {
        return AppContext.context
                .getSharedPreferences(Constants.SP_VISITED_NEWS, Context.MODE_PRIVATE);
    }

    public static Map<String, Integer> getAllVisitedItem() {
        return (Map<String, Integer>) getSharedPreferences().getAll();
    }

    public static int getColor(int colorResId) {
        return AppContext.context.getResources().getColor(colorResId);
    }

}
