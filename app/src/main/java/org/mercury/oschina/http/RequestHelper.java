package org.mercury.oschina.http;

import org.mercury.oschina.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mercury on 2017/7/31.
 * 简单封装 Retrofit的配置和使用 单例模式
 */

public class RequestHelper {

    private OkHttpClient mOkHttpClient;
    private Retrofit     mRetrofit;

    private RequestHelper() {

    }

    private RequestHelper(OkHttpClient client) {
        if (client == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = client;
        }

        mRetrofit = new Retrofit.Builder().client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_API_URL)
                .build();
    }

    private static RequestHelper requestHelper;

    public static void init(OkHttpClient client) {
        if (requestHelper == null) {
            synchronized (RequestHelper.class) {
                if (requestHelper == null) {
                    requestHelper = new RequestHelper(client);
                }
            }
        }
    }

    public static RequestHelper getInstance() {
        return requestHelper;
    }

    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            return new Retrofit.Builder().build();
        }
        return mRetrofit;
    }

    public <T> T getRetrofitCall(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }

}
