package org.mercury.oschina.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import org.mercury.oschina.Constant;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.base.BaseRecyclerViewFragment;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.synthesis.adapter.BlogListAdapter;
import org.mercury.oschina.synthesis.bean.Blog;
import org.mercury.oschina.tweet.TweetListFragment;
import org.mercury.oschina.user.bean.ProjectResponse;
import org.mercury.oschina.utils.GeneralUtils;
import org.mercury.oschina.utils.SpUtils;
import org.mercury.oschina.widget.EmptyLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      用户博客列表
 */
public class UserBlogFragment extends BaseRecyclerViewFragment<ProjectResponse> {

    public BlogListAdapter mAdapter;

    public boolean isLoadMore;

    public static final String REQUEST_CATALOG = "REQUEST_CATALOG";
    public static final int    CATALOG_NEW     = 1;
    public static final int    CATALOG_HOT     = 2;
    public int requestType;

    @Override
    protected void response(Call<ProjectResponse> call, Response<ProjectResponse> response) {
        ProjectResponse bean = response.body();
        if (bean == null || bean.getProjectlist() == null) {
            return;
        }
        if (bean.getCount() == 0) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            mEmptyLayout.setErrorType(EmptyLayout.NODATA);

        }
        if (isLoadMore) {
            loadMore(bean.getProjectlist());
        } else {
            refresh(bean.getProjectlist());
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
    protected Call<ProjectResponse> getCall(HttpApi retrofitCall) {
        int userId = Integer.parseInt(SpUtils.get(getContext(), Constant.USER_ID, "").toString());
        Call<ProjectResponse> userBlogList = retrofitCall.getUserBlogList(userId, pageIndex);
        return userBlogList;
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
            GeneralUtils.writeVisitedItem(blog.getId());
        }
    }
}
