package org.mercury.oschina.http;

import android.content.Intent;
import android.util.Log;

import org.mercury.oschina.AuthActivity;
import org.mercury.oschina.Constant;
import org.mercury.oschina.AppContext;
import org.mercury.oschina.utils.AccessTokenHelper;
import org.mercury.oschina.utils.SpUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mercury on 2017/8/2.
 * OKHttp 网络请求的拦截器
 */

public class HttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        Request newRequest = addParam(oldRequest);
        int code = chain.proceed(newRequest).code();
        if (code == 401) {
            Intent intent = new Intent(AppContext.context, AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AppContext.context.startActivity(intent);

        }
        return chain.proceed(newRequest);
    }

    private Request addParam(Request oldRequest) {
        String userId = SpUtils.get(AppContext.context, Constant.USER_ID, "").toString();
        String accessToken = AccessTokenHelper.getAccessToken(userId);
        Log.i("access_token", accessToken);
        Log.i("userId", SpUtils.get(AppContext.context, Constant.USER_ID, "").toString());
        HttpUrl.Builder builder = oldRequest.url().newBuilder().setQueryParameter
                ("access_token", accessToken);
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(builder.build())
                .build();
        return newRequest;


    }
}
