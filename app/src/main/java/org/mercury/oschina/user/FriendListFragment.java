package org.mercury.oschina.user;

import android.os.Bundle;
import android.view.View;

import org.mercury.oschina.Constant;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.base.BaseRecyclerViewFragment;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.user.adapter.FriendListAdapter;
import org.mercury.oschina.user.bean.User;
import org.mercury.oschina.user.bean.UserResponse;
import org.mercury.oschina.widget.EmptyLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Mercury on 2016-08-14 19:33:46.
 * 好友列表(我关注的 我的粉丝)
 */
public class FriendListFragment extends BaseRecyclerViewFragment<UserResponse> {

    public FriendListAdapter mAdapter;

    public boolean isLoadMore;



    public static final int RELATION_FANS = 0;
    public static final int RELATION_FOLLOW = 1;
    public int requestType;

    @Override
    protected void response(Call<UserResponse> call, Response<UserResponse> response) {
        UserResponse bean = response.body();
        if (bean == null) {
            return;
        }

        if (isLoadMore) {
            loadMore(bean.getUserList());
        } else {
            if (bean.getUserList() == null) {
                mEmptyLayout.setVisibility(View.VISIBLE);
                mEmptyLayout.setErrorType(EmptyLayout.NODATA);
                return;
            }
            refresh(bean.getUserList());
        }
    }

    /**
     * 重新这个方法,将参数置为false,就是不为recyclrview添加默认的分割线
     * @param add
     */
    @Override
    protected void addItemDivider(boolean add) {
        super.addItemDivider(false);
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        if (bundle != null) {
            requestType = bundle.getInt(Constant.REQUEST_CATALOG);
        }
    }

    @Override
    protected Call<UserResponse> getCall(HttpApi retrofitCall) {
        Call<UserResponse> userList = retrofitCall.getUserList(requestType, pageIndex);
        return userList;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        FriendListAdapter adapter = new FriendListAdapter(getActivity());
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
        User item = mAdapter.getItem(position);
        if (item != null) {
            OtherUserHomeActivity.show(getContext(), new Integer(item.getUserid()).longValue());
        }
    }
}
