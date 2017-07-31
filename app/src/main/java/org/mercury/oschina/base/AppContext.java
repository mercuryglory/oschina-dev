package org.mercury.oschina.base;

import android.content.Context;
import android.os.Handler;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.Logger;

import org.mercury.oschina.synthesis.utils.GeneralUtils;

import java.util.Map;

/**
 * Created by Mercury on 2016-08-14 23:03:47.
 */
public class AppContext extends BaseApplication {
    public static AppContext          context;
    public static Handler             mHandler;
    public static Map<String, String> mAllVisitedItem;

    public static Context getContext() {
        return context;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        context = this;
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        Fresco.initialize(this);
        mAllVisitedItem = GeneralUtils.getAllVisitedItem();
        Logger.init("====_");
    }

}
