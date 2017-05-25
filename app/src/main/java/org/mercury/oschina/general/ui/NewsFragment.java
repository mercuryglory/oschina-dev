package org.mercury.oschina.general.ui;

import com.orhanobut.logger.Logger;

import org.mercury.oschina.general.adapter.BasicAdapter;
import org.mercury.oschina.general.adapter.NewsAdapter;
import org.mercury.oschina.general.api.HttpApi;
import org.mercury.oschina.general.bean.NewsListBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by more on 2016-08-15 15:03:38.
 */
public class NewsFragment extends BaseListFragment<NewsListBean.ResultBean.NewsItemsBean> {
    private static final String TAG = "====_NewsFragment";
    private Callback<NewsListBean> mCallback = new Callback<NewsListBean>() {
        @Override
        public void onResponse(Call<NewsListBean> call, Response<NewsListBean> response) {
            Logger.t(TAG).i("onResponse");
            NewsListBean.ResultBean resultBean = response.body().getResult();
            preToken = resultBean.getPrevPageToken();
            nextToken = resultBean.getNextPageToken();
            if (isRefresh) {
                mAdapter.updateItem(resultBean.getItems());
            } else {
                mAdapter.appendItem(resultBean.getItems());
            }
            stopRefresh();
        }

        @Override
        public void onFailure(Call<NewsListBean> call, Throwable t) {
            Logger.t(TAG).i("onFailure");
        }
    };



    @Override
    protected BasicAdapter<NewsListBean.ResultBean.NewsItemsBean> onCreateAdapter() {
        return new NewsAdapter();
    }


    @Override
    protected void onLoadData() {
        HttpApi.getNewsList(token, mCallback);
    }


}
