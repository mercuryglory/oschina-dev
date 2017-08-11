package org.mercury.oschina.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.mercury.oschina.R;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.widget.EmptyLayout;
import org.mercury.oschina.widget.recyclerload.HaoRecyclerView;
import org.mercury.oschina.widget.recyclerload.OnLoadMoreListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wang.zhonghao on 2017/8/11.
 * 布局仅是简单的展示数据,swiperefreshlayout和recyclerview的列表类型的基类
 */

public abstract class BaseRecyclerViewFragment<T> extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    protected SwipeRefreshLayout mRefreshLayout;
    protected HaoRecyclerView mRecyclerView;
    protected EmptyLayout mEmptyLayout;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_recyclerview;
    }

    @Override
    protected void initWidget(View root) {
        mRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swr);
        mRecyclerView = (HaoRecyclerView) root.findViewById(R.id.recyclerview);
        mEmptyLayout = (EmptyLayout) root.findViewById(R.id.error_layout);
    }

    @Override
    protected void initData() {
        mRefreshLayout.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R
                .color.swiperefresh_color3, R.color.swiperefresh_color4);
        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setOnLoadMoreListener(this);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration
                .VERTICAL));

        mRecyclerView.setAdapter(getRecyclerAdapter());

        requestData();


    }

    protected void requestData() {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<T> callData = getCall(retrofitCall);
        callData.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                mRefreshLayout.setVisibility(View.VISIBLE);
                mEmptyLayout.dismiss();
                response(call, response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                failure(call, t);
            }
        });
    }

    protected abstract void failure(Call<T> call, Throwable t);

    protected abstract void response(Call<T> call, Response<T> response);

    protected abstract Call<T> getCall(HttpApi retrofitCall);

    protected abstract BaseRecyclerAdapter getRecyclerAdapter();

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }


}
