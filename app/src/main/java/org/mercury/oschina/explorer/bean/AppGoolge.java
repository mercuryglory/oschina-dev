package org.mercury.oschina.explorer.bean;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * @创建者 LY
 * @创建时间 2016/8/15 16:57
 * @描述 ${TODO}
 */
public class AppGoolge extends Application {
    public Context mContext;
    public Handler mHandler;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mHandler = new Handler();

    }

}
