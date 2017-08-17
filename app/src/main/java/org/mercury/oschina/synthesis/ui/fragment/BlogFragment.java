package org.mercury.oschina.synthesis.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.base.BaseRecyclerViewFragment;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.synthesis.adapter.BlogListAdapter;
import org.mercury.oschina.synthesis.bean.Blog;
import org.mercury.oschina.synthesis.bean.BlogResponse;
import org.mercury.oschina.tweet.TweetListFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Mercury on 2016-08-15 15:03:38.
 * 博客列表
 */
public class BlogFragment extends BaseRecyclerViewFragment<BlogResponse> {

    public BlogListAdapter mAdapter;

    int pageIndex = 1;
    public boolean isLoadMore;

    public static final String REQUEST_CATALOG = "REQUEST_CATALOG";
    public static final int    CATALOG_NEW     = 1;
    public static final int    CATALOG_HOT     = 2;
    public int requestType;

    @Override
    protected void response(Call<BlogResponse> call, Response<BlogResponse> response) {
        BlogResponse bean = response.body();
        if (bean == null || bean.getBloglist() == null) {
            return;
        }
        if (isLoadMore) {
            loadMore(bean.getBloglist());
        } else {
            refresh(bean.getBloglist());
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
    protected Call<BlogResponse> getCall(HttpApi retrofitCall) {
        Call<BlogResponse> blogList = null;
        if (requestType == CATALOG_NEW) {
            blogList = retrofitCall.getBlogNewList(pageIndex);
        } else if (requestType == CATALOG_HOT) {
            blogList = retrofitCall.getBlogHotList(pageIndex);
        }
        return blogList;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        BlogListAdapter adapter = new BlogListAdapter(getActivity());
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
