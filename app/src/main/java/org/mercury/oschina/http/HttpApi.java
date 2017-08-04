package org.mercury.oschina.http;

import org.mercury.oschina.bean.AccessToken;
import org.mercury.oschina.tweet.bean.CommentList;
import org.mercury.oschina.tweet.bean.Tweet;
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
    Call<TweetResponse> getTweetList(@Query("user") String user, @Query("page") int page);

    @GET("tweet_detail")
    Call<Tweet> getTweetDetail(@Query("id") int id);

    @GET("comment_list")
    Call<CommentList> getCommentList(@Query("id") int id, @Query("catalog") int catalog, @Query
            ("page") int page);
}
