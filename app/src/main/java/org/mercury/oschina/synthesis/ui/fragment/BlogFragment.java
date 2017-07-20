package org.mercury.oschina.synthesis.ui.fragment;

import com.orhanobut.logger.Logger;

import org.mercury.oschina.synthesis.adapter.BasicAdapter;
import org.mercury.oschina.synthesis.adapter.BlogAdapter;
import org.mercury.oschina.synthesis.bean.BlogListBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by more on 2016-08-15 15:03:38.
 */
public abstract class BlogFragment extends BaseListFragment<BlogListBean.ResultBean.ItemsBean> {
    private static final String TAG = "====_NewsFragment";
    Callback<BlogListBean> mCallback = new Callback<BlogListBean>() {
        @Override
        public void onResponse(Call<BlogListBean> call, Response<BlogListBean> response) {
            Logger.t(TAG).i("onResponse");
            BlogListBean.ResultBean resultBean = response.body().getResult();
            List<BlogListBean.ResultBean.ItemsBean> items = resultBean.getItems();
            preToken = resultBean.getPrevPageToken();
            nextToken=resultBean.getNextPageToken();
            if (isRefresh){
                mAdapter.updateItem(items);
            }else {
                mAdapter.appendItem(items);
            }
            stopRefresh();
        }

        @Override
        public void onFailure(Call<BlogListBean> call, Throwable t) {
            Logger.t(TAG).i("onFailure");
        }
    };
    @Override
    protected BasicAdapter<BlogListBean.ResultBean.ItemsBean> onCreateAdapter() {
        return new BlogAdapter();
    }
}
