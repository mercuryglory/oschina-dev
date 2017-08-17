package org.mercury.oschina.synthesis.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.base.BaseRecyclerViewFragment;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.synthesis.adapter.NewsListAdapter;
import org.mercury.oschina.synthesis.bean.Blog;
import org.mercury.oschina.synthesis.bean.NewsResponse;
import org.mercury.oschina.tweet.TweetListFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Mercury on 2016-08-15 15:03:38.
 * 新闻资讯 列表
 */
public class NewsFragment extends BaseRecyclerViewFragment<NewsResponse> {

    public NewsListAdapter mAdapter;

    int pageIndex = 1;
    public boolean isLoadMore;

    public static final String REQUEST_CATALOG = "REQUEST_CATALOG";
    public static final int    CATALOG_NEW     = 1;
    public static final int    CATALOG_HOT     = 2;
    public int requestType;

    @Override
    protected void response(Call<NewsResponse> call, Response<NewsResponse> response) {
        NewsResponse bean = response.body();
        if (bean == null || bean.getNewslist() == null) {
            return;
        }
        if (isLoadMore) {
            loadMore(bean.getNewslist());
        } else {
            refresh(bean.getNewslist());
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
    protected Call<NewsResponse> getCall(HttpApi retrofitCall) {
        Call<NewsResponse> newsList = retrofitCall.getNewsList(1, pageIndex);
        return newsList;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        NewsListAdapter adapter = new NewsListAdapter(getActivity());
        mAdapter = adapter;
        return adapter;
    }

    public void loadMore(List list) {
        if (list == null || list.size() == 0) {
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
        Blog blog = mAdapter.getItem(position);
        if (blog != null) {

        }
    }
}
