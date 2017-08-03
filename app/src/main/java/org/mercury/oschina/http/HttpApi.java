package org.mercury.oschina.http;

import org.mercury.oschina.bean.AccessToken;
import org.mercury.oschina.tweet.bean.TweetResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Mercury on 2017/7/30.
 */

public interface HttpApi {

    @GET("token")
    Call<AccessToken> getAccessToken(@QueryMap Map<String,String> params);

    @GET("tweet_list")
    Call<TweetResponse> getTweetData(@Query("user") String user, @Query("page") int page);

}
