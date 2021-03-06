package org.mercury.oschina;

import android.content.Context;
import android.os.Handler;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.Logger;

import org.mercury.oschina.base.BaseApplication;
import org.mercury.oschina.http.HttpInterceptor;
import org.mercury.oschina.http.RequestHelper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Mercury on 2016-08-14 23:03:47.
 */
public class AppContext extends BaseApplication {
    public static AppContext          context;
    public static Handler             mHandler;

    public static Context getContext() {
        return context;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        context = this;
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        Logger.init("====_");

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpInterceptor())
                .build();
        RequestHelper.init(client);

    }

}
