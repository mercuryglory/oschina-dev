package org.mercury.oschina.synthesis.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.base.BaseRecyclerViewFragment;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.synthesis.adapter.PostListAdapter;
import org.mercury.oschina.synthesis.bean.Post;
import org.mercury.oschina.synthesis.bean.PostResponse;
import org.mercury.oschina.tweet.TweetListFragment;
import org.mercury.oschina.utils.GeneralUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Mercury on 2016-08-15 15:03:38.
 * 帖子列表 ID 1-问答 2-分享 3-IT杂烩(综合) 4-站务 100-职业生涯 0-所有
 */
public class PostFragment extends BaseRecyclerViewFragment<PostResponse> {

    public PostListAdapter mAdapter;

    int pageIndex = 1;
    public boolean isLoadMore;

    public static final String REQUEST_CATALOG = "REQUEST_CATALOG";
    public static final int    CATALOG_ANSWER     = 1;
    public static final int    CATALOG_SHARE     = 2;
    public int requestType;

    @Override
    protected void response(Call<PostResponse> call, Response<PostResponse> response) {
        PostResponse bean = response.body();
        if (bean == null || bean.getPost_list() == null) {
            return;
        }
        if (isLoadMore) {
            loadMore(bean.getPost_list());
        } else {
            refresh(bean.getPost_list());
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
    protected Call<PostResponse> getCall(HttpApi retrofitCall) {
        Call<PostResponse> postList = null;
        if (requestType == CATALOG_ANSWER) {
            postList = retrofitCall.getPostList(CATALOG_ANSWER, pageIndex);
        } else if (requestType == CATALOG_SHARE) {
            postList = retrofitCall.getPostList(CATALOG_ANSWER, pageIndex);
        }
        return postList;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        PostListAdapter adapter = new PostListAdapter(getActivity());
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
        Post post = mAdapter.getItem(position);
        if (post != null) {
            GeneralUtils.writeVisitedItem(post.getId());
        }
    }
}
