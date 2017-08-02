package org.mercury.oschina.http;

import org.mercury.oschina.Constant;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.utils.AccessTokenHelper;
import org.mercury.oschina.utils.SpUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mercury on 2017/8/2.
 */

public class HttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        Request newRequest = addParam(oldRequest);
        return chain.proceed(newRequest);
    }

    private Request addParam(Request oldRequest) {
        String userId = SpUtils.get(AppContext.context, Constant.USER_ID, "").toString();
        String accessToken = AccessTokenHelper.getAccessToken(userId);
        HttpUrl.Builder builder = oldRequest.url().newBuilder().setQueryParameter
                ("access_token", accessToken);
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(builder.build())
                .build();
        return newRequest;


    }
}
