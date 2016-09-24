package org.lion.oschina.tweet.net;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.lion.oschina.tweet.url.TweetUrl;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/16
 * 描述:      ${TODO}
 */
public class HttpApi {
    /**
     *
     * @param pageIndex
     */
    public static void getTweetList(int pageIndex, String uid,Callback callback) {
        OkHttpUtils
                .get()
                .url(TweetUrl.TWEET_URL)
                .addParams("uid", uid)
                .addParams("pageIndex", pageIndex+"")
                .addParams("pageSize", "20")
                .build()
                .execute(callback);
    }

    public static void getTweetComment(int pageIndex, int id,Callback callback) {
        OkHttpUtils
                .get()
                .url(TweetUrl.TWEET_COMMENT)
                .addParams("pageIndex", pageIndex+"")
                .addParams("catalog", "3")
                .addParams("pageSize", "20")
                .addParams("id",id+"")
                .build()
                .execute(callback);
    }


    public static void getOtherUserData(int pageIndex,String name, int id,Callback callback) {
        OkHttpUtils.get()
                .url(TweetUrl.USER_HOME)
                .addParams("pageIndex", pageIndex+"")
                .addParams("hisname", name)
                .addParams("uid", "0")
                .addParams("pageSize", "20")
                .addParams("hisuid", id+"")
                .build()
                .execute(callback);
    }

    public static void toBlog(String url,int id,Callback callback) {
        OkHttpUtils.get()
                .url(TweetUrl.TO_BLOG)
                .addParams("id", id + "")
                .build()
                .execute(callback);
    }

    public static void toNews(String url,int id,Callback callback) {
        OkHttpUtils.get()
                .url(TweetUrl.TO_NEWS)
                .addParams("id", id + "")
                .build()
                .execute(callback);
    }

}
