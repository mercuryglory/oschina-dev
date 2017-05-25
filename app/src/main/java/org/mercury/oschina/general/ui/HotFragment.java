package org.mercury.oschina.general.ui;

import com.orhanobut.logger.Logger;

import org.mercury.oschina.general.adapter.BasicAdapter;
import org.mercury.oschina.general.adapter.HotAdapter;
import org.mercury.oschina.general.api.HttpApi;
import org.mercury.oschina.general.bean.HotListBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by more on 2016-08-15 15:03:38.
 */
public class HotFragment extends BaseListFragment<HotListBean.ObjListBean> {
    private static final String TAG = "====_NewsFragment";
    private Callback<HotListBean> mCallback = new Callback<HotListBean>() {
        @Override
        public void onResponse(Call<HotListBean> call, Response<HotListBean> response) {
            Logger.t(TAG).i("onResponse");
            List<HotListBean.ObjListBean> obj_list = response.body().getObj_list();
            if (isRefresh){
                mAdapter.updateItem(obj_list);
            }else {
                mAdapter.appendItem(obj_list);
            }
            stopRefresh();
        }

        @Override
        public void onFailure(Call<HotListBean> call, Throwable t) {
            Logger.t(TAG).i("onFailure");
        }
    };
    private int mCatalog = 2;
    private int mPageIndex = 0;
    private int mPageSize = 20;

    @Override
    protected BasicAdapter<HotListBean.ObjListBean> onCreateAdapter() {
        return new HotAdapter();
    }

    @Override
    protected void onLoadData() {
//int catalog,int pageIndex,int pageSize
        if (isRefresh) {
            mPageIndex = 0;
        } else {
            mPageIndex++;
        }
        HttpApi.getHotList(mCatalog, mPageIndex, mPageSize, mCallback);
    }
}
