package org.mercury.oschina.explorer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * sp的工具类
 * @Description: TODO
 * @author Mercury
 * @date 2016-6-21 下午4:04:22
 */
public class SPUtils {
    //存入boolean
    /**
     * 在sp中存入boolean值
     * @param context 上下文
     * @param toggle boolean值
     */
    public static void saveBoolean(Context context,String key,boolean toggle)
    {
        SharedPreferences preferences = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        Editor edit = preferences.edit();
        edit.putBoolean(key, toggle);
        edit.commit();
    }
    
    
    public static void saveInt(Context context,String key,int value)
    {
        SharedPreferences preferences = context.getSharedPreferences(Fileds.SPNAME, Context.MODE_PRIVATE);
        Editor edit = preferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }
    
    public static int getInt(Context context,String key,int defValue){
    	SharedPreferences preferences = context.getSharedPreferences(Fileds.SPNAME, Context.MODE_PRIVATE);
    	return preferences.getInt(key, defValue);
    };
    
    //读取boolean状态
    public static boolean getBoolean_true(Context context,String key)
    {
        
        SharedPreferences preferences = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        return preferences.getBoolean(key, true);
        
    }
    
    //读取boolean状态
    public static boolean getBoolean_false(Context context,String key)
    {
        
        SharedPreferences preferences = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
        
    }
    
    //1. 保存String类型数据
    public static void saveString(Context context,String key,String value)
    {
        SharedPreferences preferences = context.getSharedPreferences(Fileds.SPNAME, Context.MODE_PRIVATE);
        Editor edit = preferences.edit();
        edit.putString(key, value);
        edit.commit();
    }
    
    
    //获取String数据
    public static String getString(Context context,String key){
        SharedPreferences preferences = context.getSharedPreferences(Fileds.SPNAME, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
}
