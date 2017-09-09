package org.mercury.oschina.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mercury.xrecyclerview.CRecyclerView;

import org.mercury.oschina.R;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.widget.EmptyLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wang.zhonghao on 2017/8/11.
 * 布局仅是简单的展示数据,swiperefreshlayout和recyclerview的列表类型的基类
 */

public abstract class BaseChatViewActivity<T> extends BaseActivity implements
        CRecyclerView.LoadingListener, View.OnClickListener {

    protected CRecyclerView      mRecyclerView;
    protected EmptyLayout        mEmptyLayout;
    protected Toolbar            mToolbar;

    protected int pageIndex = 1;

    protected boolean isRefreshing;

    @Override
    protected int getContentView() {
        return R.layout.activity_base_chatview;
    }

    @Override
    protected void initWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (CRecyclerView) findViewById(R.id.recyclerview);
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

        mRecyclerView.setOnLoadingListener(this);
        mRecyclerView.setLayoutManager(getLayoutManager());

        mRecyclerView.setAdapter(getRecyclerAdapter());
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
    public void onClick(View v) {
        mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        pageIndex = 1;
        requestData();

    }

    public String getMainTitle() {
        return "oschina开发版";
    }
}
