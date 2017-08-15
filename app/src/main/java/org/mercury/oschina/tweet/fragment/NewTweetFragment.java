package org.mercury.oschina.tweet.fragment;

import android.support.v4.widget.SwipeRefreshLayout;

import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.base.BaseRecyclerViewFragment;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.tweet.activity.TweetDetailActivity;
import org.mercury.oschina.tweet.adapter.TweetListAdapter;
import org.mercury.oschina.tweet.bean.Tweet;
import org.mercury.oschina.tweet.bean.TweetResponse;
import org.mercury.oschina.widget.recyclerload.OnLoadMoreListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      最新动弹
 */
public class NewTweetFragment extends BaseRecyclerViewFragment<TweetResponse> implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    public TweetListAdapter mAdapter;

    int pageIndex = 1;
    public boolean isLoadMore;

    @Override
    protected void response(Call<TweetResponse> call, Response<TweetResponse> response) {
        TweetResponse bean = response.body();
        if (isLoadMore) {
            loadMore(bean.getTweetlist());
        } else {
            refresh(bean.getTweetlist());
        }
    }


    @Override
    protected Call<TweetResponse> getCall(HttpApi retrofitCall) {
        Call<TweetResponse> tweetData = retrofitCall.getTweetList("0", pageIndex);
        return tweetData;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        TweetListAdapter adapter = new TweetListAdapter(getActivity());
        mAdapter = adapter;
        return adapter;
    }

    public void loadMore(List list) {
        mAdapter.addAll(list);
        mRecyclerView.loadMoreComplete();
    }

    public void refresh(List list) {
        if (mAdapter == null) {
            mAdapter.addAll(list);
        } else {
            mAdapter.setData(list);
        }
        mRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete();
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        mRecyclerView.setCanloadMore(false);
        pageIndex = 1;
        requestData();

    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        pageIndex++;
        requestData();

    }

    @Override
    public void onItemClick(int position, long itemId) {
        Tweet tweet = mAdapter.getItem(position);
        if (tweet != null) {
            TweetDetailActivity.show(getContext(), tweet);
        }
    }
}
