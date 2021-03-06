package org.mercury.oschina.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.base.BaseRecyclerViewFragment;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.tweet.TweetListFragment;
import org.mercury.oschina.tweet.activity.TweetDetailActivity;
import org.mercury.oschina.user.adapter.CommentListAdapter;
import org.mercury.oschina.user.bean.Active;
import org.mercury.oschina.user.bean.ActiveResponse;
import org.mercury.oschina.utils.AccessTokenHelper;
import org.mercury.oschina.widget.EmptyLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      评论 我的 消息列表
 */
public class CommentFragment extends BaseRecyclerViewFragment<ActiveResponse> {

    public CommentListAdapter mAdapter;

    public boolean isLoadMore;

    public static final String REQUEST_CATALOG = "REQUEST_CATALOG";
    public static final int    CATALOG_COMMENT     = 3;
    public static final int    CATALOG_HOT     = 2;
    public int requestType;

    @Override
    protected void response(Call<ActiveResponse> call, Response<ActiveResponse> response) {
        ActiveResponse bean = response.body();
        if (bean == null || bean.getActivelist() == null) {
            return;
        }
        if (bean.getActivelist().size() == 0 && !isLoadMore) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            mEmptyLayout.setErrorType(EmptyLayout.NODATA);
            return;
        }
        if (isLoadMore) {
            loadMore(bean.getActivelist());
        } else {
            refresh(bean.getActivelist());
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
    protected Call<ActiveResponse> getCall(HttpApi retrofitCall) {
        Call<ActiveResponse> activeList = retrofitCall.getActiveList(CATALOG_COMMENT,
                AccessTokenHelper.getUserId(), pageIndex);
        return activeList;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        CommentListAdapter adapter = new CommentListAdapter(getActivity());
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
        Active item = mAdapter.getItem(position);
        if (item != null) {
            TweetDetailActivity.show(getContext(), item.getObjectId());
        }
    }
}