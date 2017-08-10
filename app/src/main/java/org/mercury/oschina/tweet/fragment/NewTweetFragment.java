package org.mercury.oschina.tweet.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import org.mercury.oschina.R;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.widget.recyclerload.HaoRecyclerView;
import org.mercury.oschina.widget.recyclerload.OnLoadMoreListener;
import org.mercury.oschina.tweet.adapter.NewTweetAdapter;
import org.mercury.oschina.tweet.bean.TweetResponse;

import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      最新动弹
 */
public class NewTweetFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    public NewTweetAdapter mAdapter;

    int pageIndex = 1;
    public boolean isLoadMore;

    @Bind(R.id.rv)
    HaoRecyclerView    rv;
    @Bind(R.id.swr)
    SwipeRefreshLayout swr;

    @Override
    protected void initData() {
        swr.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R
                .color.swiperefresh_color3, R.color.swiperefresh_color4);
        swr.setOnRefreshListener(this);
        rv.setOnLoadMoreListener(this);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new NewTweetAdapter(getActivity());
        rv.setAdapter(mAdapter);
        requestData();
    }


    protected Call<TweetResponse> getCall() {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<TweetResponse> tweetData = retrofitCall.getTweetList("0", pageIndex);
        return tweetData;
    }

    public void requestData() {

        Call<TweetResponse> tweetData = getCall();
        tweetData.enqueue(new Callback<TweetResponse>() {
            @Override
            public void onResponse(Call<TweetResponse> call, Response<TweetResponse> response) {
                TweetResponse bean = response.body();
                if (isLoadMore) {
                    loadMore(bean.getTweetlist());
                } else {
                    refresh(bean.getTweetlist());
                }

            }

            @Override
            public void onFailure(Call<TweetResponse> call, Throwable t) {

            }
        });

    }

    public void loadMore(List list) {
        mAdapter.addAll(list);
        rv.loadMoreComplete();
    }

    public void refresh(List list) {
        if (mAdapter == null) {
            mAdapter.addAll(list);
        } else {
            mAdapter.setData(list);
        }
        swr.setRefreshing(false);
        rv.refreshComplete();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tweet_refresh;
    }


    @Override
    public void onRefresh() {
        isLoadMore = false;
        rv.setCanloadMore(false);
        pageIndex = 1;
        requestData();

    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        pageIndex++;
        requestData();

    }
}
