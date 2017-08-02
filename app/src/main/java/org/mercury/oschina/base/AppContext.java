package org.mercury.oschina.base;

import android.content.Context;
import android.os.Handler;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.Logger;

import org.mercury.oschina.http.HttpInterceptor;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.synthesis.utils.GeneralUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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
        mAllVisitedItem = GeneralUtils.getAllVisitedItem();
        Logger.init("====_");

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpInterceptor())
                .build();
        RequestHelper.init(client);

    }

}
