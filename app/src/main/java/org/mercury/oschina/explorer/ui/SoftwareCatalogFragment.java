package org.mercury.oschina.explorer.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseFragment;
import org.mercury.oschina.explorer.adapter.SoftwareCatalogAdapter;
import org.mercury.oschina.explorer.bean.SoftwareCatalog;
import org.mercury.oschina.explorer.bean.SoftwareCatalogResponse;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.widget.EmptyLayout;
import org.mercury.oschina.widget.ScrollLayout;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mercury on 2017/9/15.
 * 软件分类列表，一级二级三级列表
 */

public class SoftwareCatalogFragment extends BaseFragment {

    @Bind(R.id.lv_catalog)
    ListView     lvCatalog;
    @Bind(R.id.lv_tag)
    ListView     lvTag;
    @Bind(R.id.lv_software)
    ListView     lvSoftware;
    @Bind(R.id.scrolllayout)
    ScrollLayout scrolllayout;
    @Bind(R.id.error_layout)
    EmptyLayout  errorLayout;

    public static final int SCREEN_CATALOG = 0;
    public static final int SCREEN_TAG = 1;
    public static final int SCREEN_SOFTWARE = 2;

    private static int currentScreen = SCREEN_CATALOG;
    private static int currentTag;

    private SoftwareCatalogAdapter mAdapter;

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
            }

        }
    };



    @Override
    protected void initWidget(View root) {
        lvCatalog.setOnItemClickListener(mCatalogListener);
        mAdapter = new SoftwareCatalogAdapter(getContext());
        lvCatalog.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<SoftwareCatalogResponse> softwareTypes = retrofitCall.getSoftwareTypes(currentTag);
        softwareTypes.enqueue(new Callback<SoftwareCatalogResponse>() {
            @Override
            public void onResponse(Call<SoftwareCatalogResponse> call, Response<SoftwareCatalogResponse> response) {

                SoftwareCatalogResponse body = response.body();
                if (body != null) {
                    mAdapter.setData(body.getSoftwareTypes());
                }
            }

            @Override
            public void onFailure(Call<SoftwareCatalogResponse> call, Throwable t) {

            }
        });
    }
}
