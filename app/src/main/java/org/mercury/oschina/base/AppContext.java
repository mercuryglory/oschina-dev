package org.mercury.oschina.base;

import android.content.Context;
import android.os.Handler;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.Logger;

import org.mercury.oschina.general.utils.GeneralUtils;

import java.util.Map;

/**
 * Created by more on 2016-08-14 23:03:47.
 */
public class AppContext extends BaseApplication {
    public static AppContext          context;
    public static AppContext          mContext;
    public static Handler             myHandler;
    public static Handler             mHandler;
    public static Map<String, String> mAllVisitedItem;

    public static Context getContext() {
        return context;
    }

    public static Handler mainHandle = null;

    @Override
    public void onCreate() {
        super.onCreate();
        myHandler = new Handler();
        context = this;
        mContext = context;
        mHandler = myHandler;
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        Fresco.initialize(this);
        mainHandle = new Handler();
        mAllVisitedItem = GeneralUtils.getAllVisitedItem();
        Logger.init("====_");
    }

    public static Gson createGson() {
        com.google.gson.GsonBuilder gsonBuilder = new com.google.gson.GsonBuilder();
        //gsonBuilder.setExclusionStrategies(new SpecificClassExclusionStrategy(null, Model.class));
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        return gsonBuilder.create();
    }
}
