package org.mercury.oschina.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.mercury.oschina.R;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.widget.EmptyLayout;
import org.mercury.oschina.widget.recyclerload.HaoRecyclerView;
import org.mercury.oschina.widget.recyclerload.OnLoadMoreListener;
import org.mercury.oschina.widget.recyclerload.WrapAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wang.zhonghao on 2017/8/11.
 * 布局仅是简单的展示数据,swiperefreshlayout和recyclerview的列表类型的基类
 */

public abstract class BaseRecyclerViewActivity<T> extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener, WrapAdapter
        .OnItemClickListener, View.OnClickListener {

    protected SwipeRefreshLayout mRefreshLayout;
    protected HaoRecyclerView    mRecyclerView;
    protected EmptyLayout        mEmptyLayout;
    protected Toolbar mToolbar;

    protected int pageIndex = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_base_recyclerview;
    }

    @Override
    protected void initWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swr);
        mRecyclerView = (HaoRecyclerView) findViewById(R.id.recyclerview);
        mEmptyLayout = (EmptyLayout) findViewById(R.id.error_layout);
        mEmptyLayout.setOnLayoutClickListener(this);

        mToolbar.setTitle(getMainTitle());
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });
    }

    @Override
    protected void initData() {
        mRefreshLayout.setColorSchemeResources(R.color.swiperefresh_color1, R.color
                .swiperefresh_color2, R
                .color.swiperefresh_color3, R.color.swiperefresh_color4);
        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setOnLoadMoreListener(this);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
                .VERTICAL));

        mRecyclerView.setAdapter(getRecyclerAdapter());
        mRecyclerView.setOnItemClickListener(this);

        requestData();

    }

    protected void requestData() {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<T> callData = getCall(retrofitCall);
        if (callData == null) {
            return;
        }
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
                showToast(getResources().getString(R.string.state_network_error));
                failure(call, t);
            }
        });
    }

    protected void failure(Call<T> call, Throwable t) {

    }

    protected abstract void response(Call<T> call, Response<T> response);

    protected abstract Call<T> getCall(HttpApi retrofitCall);

    protected abstract BaseRecyclerAdapter getRecyclerAdapter();

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }


    @Override
    public void onItemClick(int position, long itemId) {

    }

    @Override
    public void onClick(View v) {
        mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        pageIndex = 1;
        requestData();

    }

    public String getMainTitle() {
        return "oschina开发版";
    }
}
