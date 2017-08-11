package org.mercury.oschina.tweet.fragment;


import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.tweet.bean.TweetResponse;

import java.util.List;

import retrofit2.Call;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      热门动弹
 */
public class HotTweetFragment extends NewTweetFragment {


    @Override
    protected Call<TweetResponse> getCall(HttpApi retrofitCall) {
        Call<TweetResponse> tweetData = retrofitCall.getTweetList("-1", pageIndex);
        return tweetData;
    }

    @Override
    public void loadMore(List list) {
        mRecyclerView.loadMoreEnd();
    }
}
