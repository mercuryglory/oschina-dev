package org.mercury.oschina.tweet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class TweetListFragment extends BaseRecyclerViewFragment<TweetResponse> implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    public TweetListAdapter mAdapter;

    int pageIndex = 1;
    public boolean isLoadMore;

    public static final String REQUEST_CATALOG = "REQUEST_CATALOG";
    public static final int    CATALOG_NEW     = 0;
    public static final int    CATALOG_HOT     = -1;
    public long requestType;

    @Override
    protected void response(Call<TweetResponse> call, Response<TweetResponse> response) {
        TweetResponse bean = response.body();
        if (isLoadMore) {
            loadMore(bean.getTweetlist());
        } else {
            refresh(bean.getTweetlist());
        }
    }

    public static Fragment instantiate(int userId) {
        Bundle bundle = new Bundle();
        bundle.putInt(REQUEST_CATALOG, userId);
        TweetListFragment fragment = new TweetListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        if (bundle != null) {
            requestType = bundle.getInt(REQUEST_CATALOG);
        }
    }

    @Override
    protected Call<TweetResponse> getCall(HttpApi retrofitCall) {
        Call<TweetResponse> tweetData = retrofitCall.getTweetList(requestType, pageIndex);
        return tweetData;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        TweetListAdapter adapter = new TweetListAdapter(getActivity());
        mAdapter = adapter;
        return adapter;
    }

    public void loadMore(List list) {
        //如果是热门动弹,有且仅有20条(目前)
        if (requestType == CATALOG_HOT) {
            mRecyclerView.loadMoreEnd();
            return;
        }
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
