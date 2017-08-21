package org.mercury.oschina.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.mercury.oschina.AppContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 类名:      SpUtils
 * 创建者:    Mercury
 * 创建时间:  2016/8/12.
 * 描述:
 */
public class SpUtils {

    /*splash界面用的*/
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }


    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
    //1. 保存String类型数据
    public static void saveString(String spName, String key, String value)
    {
        SharedPreferences preferences = AppContext.context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, value);
        edit.apply();

    }

    //获取String数据
    public static String getString(String spName,String key){
        SharedPreferences preferences = AppContext.context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    //保存Integer类型数据
    public static void saveInt(String spName, String key, int value) {
        SharedPreferences preferences = AppContext.context.getSharedPreferences(spName, Context
                .MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    //获取Integer数据
    public static int getInt(String spName, String key) {
        SharedPreferences preferences = AppContext.context.getSharedPreferences(spName, Context
                .MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    //1. 保存Boolean类型数据
    public static void saveBoolean(String spName, String key, boolean value)
    {
        SharedPreferences preferences = AppContext.context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(key, value);
        edit.commit();

    }


    //获取Boolean数据
    public static boolean getBoolean(String spName,String key){
        SharedPreferences preferences = AppContext.context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }
}
