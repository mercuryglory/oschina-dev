package org.mercury.oschina.explorer.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseFragment;
import org.mercury.oschina.explorer.adapter.SoftwareCatalogAdapter;
import org.mercury.oschina.explorer.adapter.SoftwareListAdapter;
import org.mercury.oschina.explorer.bean.SoftwareCatalog;
import org.mercury.oschina.explorer.bean.SoftwareCatalogResponse;
import org.mercury.oschina.explorer.bean.SoftwareResponse;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.widget.EmptyLayout;
import org.mercury.oschina.widget.ScrollLayout;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mercury on 2017/9/15.
 * 软件分类列表，一级二级三级列表
 */

public class SoftwareCatalogFragment extends BaseFragment {

    @BindView(R.id.lv_catalog)
    ListView     lvCatalog;
    @BindView(R.id.lv_tag)
    ListView     lvTag;
    @BindView(R.id.rv_software)
    RecyclerView rvSoftware;
    @BindView(R.id.scrolllayout)
    ScrollLayout scrolllayout;
    @BindView(R.id.error_layout)
    EmptyLayout  errorLayout;

    public static final int SCREEN_CATALOG = 0;
    public static final int SCREEN_TAG = 1;
    public static final int SCREEN_SOFTWARE = 2;

    private static int currentScreen = SCREEN_CATALOG;
    private static int currentTag;

    private SoftwareCatalogAdapter mAdapter, mSecondAdapter;

    SoftwareListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_software_catalog;
    }

    private AdapterView.OnItemClickListener mCatalogListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //加载二级分类
            SoftwareCatalog item = mAdapter.getItem(position);
            if (item != null && item.getTag() > 0) {
                currentScreen = SCREEN_TAG;
                scrolllayout.scrollToScreen(currentScreen);
                currentTag = item.getTag();
                refreshTagList();
            }

        }
    };

    private AdapterView.OnItemClickListener mSecondListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //加载三级列表，软件列表
            SoftwareCatalog item = mSecondAdapter.getItem(position);
            if (item != null && item.getTag() > 0) {
                currentScreen = SCREEN_SOFTWARE;
                scrolllayout.scrollToScreen(currentScreen);
                currentTag = item.getTag();
                refreshSoftwareList();
            }
        }
    };



    @Override
    protected void initWidget(View root) {
        Log.e("onBackPressed", this.toString());
        lvCatalog.setOnItemClickListener(mCatalogListener);
        lvTag.setOnItemClickListener(mSecondListener);
        mAdapter = new SoftwareCatalogAdapter(getContext());
        mSecondAdapter = new SoftwareCatalogAdapter(getContext());
        lvCatalog.setAdapter(mAdapter);
        lvTag.setAdapter(mSecondAdapter);

        adapter = new SoftwareListAdapter(getContext());
        rvSoftware.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSoftware.setAdapter(adapter);

    }

    @Override
    protected void initData() {

        refreshTagList();
    }

    private void refreshTagList() {
        Log.e("onBackPressed", this.toString());
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<SoftwareCatalogResponse> softwareTypes = retrofitCall.getSoftwareTypes(currentTag);
        softwareTypes.enqueue(new Callback<SoftwareCatalogResponse>() {
            @Override
            public void onResponse(Call<SoftwareCatalogResponse> call, Response<SoftwareCatalogResponse> response) {

                SoftwareCatalogResponse body = response.body();
                if (body != null) {
                    if (currentScreen == SCREEN_CATALOG) {
                        mAdapter.setData(body.getSoftwareTypes());
                    } else if (currentScreen == SCREEN_TAG) {
                        mSecondAdapter.setData(body.getSoftwareTypes());
                    }
                }
            }

            @Override
            public void onFailure(Call<SoftwareCatalogResponse> call, Throwable t) {

            }
        });
    }

    private void refreshSoftwareList() {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<SoftwareResponse> softwareTypes = retrofitCall.getSoftTagList(currentTag);
        softwareTypes.enqueue(new Callback<SoftwareResponse>() {
            @Override
            public void onResponse(Call<SoftwareResponse> call, Response<SoftwareResponse> response) {
                SoftwareResponse body = response.body();
                if (body != null) {
                    adapter.setData(body.getProjectlist());
                }
            }

            @Override
            public void onFailure(Call<SoftwareResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onBackPressed() {
        Log.e("onBackPressed", this.toString());
        switch (currentScreen) {
            case SCREEN_CATALOG:
                currentTag = 0;
                return true;
            case SCREEN_TAG:
                scrolllayout.scrollToScreen(SCREEN_CATALOG);
                currentScreen = SCREEN_CATALOG;
                break;
            case SCREEN_SOFTWARE:
                scrolllayout.scrollToScreen(SCREEN_TAG);
                currentScreen = SCREEN_TAG;
                break;
            default:
                break;

        }
        return super.onBackPressed();
    }


}
