package org.mercury.oschina.explorer.ui;

import android.os.Bundle;
import android.view.View;

import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.base.BaseRecyclerViewFragment;
import org.mercury.oschina.explorer.adapter.SoftwareListAdapter;
import org.mercury.oschina.explorer.bean.Project;
import org.mercury.oschina.explorer.bean.SoftwareResponse;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.widget.EmptyLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Mercury on 2017/9/15.
 * 软件分类列表:推荐，最新，热门，国产
 */

public class SoftwareListFragment extends BaseRecyclerViewFragment<SoftwareResponse> {

    public static final String CATALOG_RECOMMEND = "recommend";//推荐
    public static final String CATALOG_TIME = "time";//最新
    public static final String CATALOG_VIEW = "view";//热门
    public static final String CATALOG_CN = "cn";//国产

    public static final String CATALOG_TYPE = "type";
    private String requestType;

    private SoftwareListAdapter mAdapter;
    private boolean isLoadMore;

    @Override
    protected void response(Call<SoftwareResponse> call, Response<SoftwareResponse> response) {
        SoftwareResponse body = response.body();
        if (body == null) {
            return;
        }
        if (body.getCount() == 0) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            mEmptyLayout.setErrorType(EmptyLayout.NODATA);
        }
        if (isLoadMore) {
            loadMore(body.getProjectlist());
        } else {
            refresh(body.getProjectlist());
        }
    }

    private void refresh(List<Project> projectlist) {
        if (mAdapter == null) {
            mAdapter.addAll(projectlist);
        } else {
            mAdapter.setData(projectlist);
        }
        mRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete();
    }

    private void loadMore(List<Project> projectlist) {
        if (projectlist.size() > 0) {
            mAdapter.addAll(projectlist);
            mRecyclerView.loadMoreComplete();
        } else {
            mRecyclerView.loadMoreEnd();
        }

    }

    @Override
    protected Call<SoftwareResponse> getCall(HttpApi retrofitCall) {
        HttpApi httpApi = RequestHelper.getInstance().getRetrofit().create(HttpApi.class);
        Call<SoftwareResponse> recommend = httpApi.getSoftwareList(requestType, pageIndex);
        return recommend;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        mAdapter = new SoftwareListAdapter(getContext());
        return mAdapter;
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
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        if (bundle != null) {
            requestType = bundle.getString(CATALOG_TYPE, "recommend");
        }
    }
}
