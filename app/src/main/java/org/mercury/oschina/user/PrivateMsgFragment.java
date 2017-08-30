package org.mercury.oschina.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.base.BaseRecyclerViewFragment;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.tweet.TweetListFragment;
import org.mercury.oschina.user.adapter.MsgListAdapter;
import org.mercury.oschina.user.bean.Message;
import org.mercury.oschina.user.bean.MessageResponse;
import org.mercury.oschina.widget.EmptyLayout;
import org.mercury.oschina.widget.recyclerload.OnLoadMoreListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      私信列表
 */
public class PrivateMsgFragment extends BaseRecyclerViewFragment<MessageResponse> implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {


    public MsgListAdapter mAdapter;

    public boolean isLoadMore;

    public static final String REQUEST_CATALOG = "REQUEST_CATALOG";
    public long requestType;

    @Override
    protected void response(Call<MessageResponse> call, Response<MessageResponse> response) {
        MessageResponse bean = response.body();
        if (bean == null || bean.getMessageList() == null) {
            return;
        }
        if (bean.getMessageList().size() == 0) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            mEmptyLayout.setErrorType(EmptyLayout.NODATA);
        }
        if (isLoadMore) {
            loadMore(bean.getMessageList());
        } else {
            refresh(bean.getMessageList());
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
    protected Call<MessageResponse> getCall(HttpApi retrofitCall) {
        Call<MessageResponse> privateMsg = retrofitCall.getPrivateMsg(pageIndex);
        return privateMsg;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        MsgListAdapter adapter = new MsgListAdapter(getActivity());
        mAdapter = adapter;
        return adapter;
    }

    public void loadMore(List list) {
        if (list.size() == 0) {
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
        //到该私信中两用户的聊天列表
        Message message = mAdapter.getItem(position);
        if (message != null) {
            PrivateMessageActivity.show(getContext(), message.getFriendid());
        }
    }
}
