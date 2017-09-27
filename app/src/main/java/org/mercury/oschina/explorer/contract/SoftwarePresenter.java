package org.mercury.oschina.explorer.contract;

import org.mercury.oschina.bean.ResultBean;
import org.mercury.oschina.explorer.bean.SoftwareDetail;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mercury on 2017/9/26.
 */

public class SoftwarePresenter implements SoftwareDetailContract.Presenter {

    private SoftwareDetailContract.View mView;

    public SoftwarePresenter(SoftwareDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void attachView(SoftwareDetailContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void refreshData(String ident, long userId) {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<SoftwareDetail> software = retrofitCall.getSoftware(ident, userId);
        software.enqueue(new Callback<SoftwareDetail>() {
            @Override
            public void onResponse(Call<SoftwareDetail> call, Response<SoftwareDetail> response) {
                SoftwareDetail body = response.body();
                if (body != null) {
                    mView.refreshSuccess(body);
                }
            }

            @Override
            public void onFailure(Call<SoftwareDetail> call, Throwable t) {
                mView.errorNetWork(t);
            }
        });
    }

    @Override
    public void addFavorite(int id, int type) {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<ResultBean> resultBeanCall = retrofitCall.addFavorite(id, type);
        resultBeanCall.enqueue(new Callback<ResultBean>() {
            @Override
            public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
                // TODO: 2017/9/27 这里拦截器请求实际进行了两次,相当于连续点击,造成回调信息错误 
//                if (response.body().getError().equals("200")) {
//                    mView.favoriteSuccess();
//                } else if (response.body().getError().equals("500")) {
//                    mView.showError("收藏失败");
//                }
                mView.favoriteSuccess();
            }

            @Override
            public void onFailure(Call<ResultBean> call, Throwable t) {
                mView.errorNetWork(t);
            }
        });
    }

    @Override
    public void deFavorite(int id, int type) {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<ResultBean> resultBeanCall = retrofitCall.cancleFavorite(id, type);
        resultBeanCall.enqueue(new Callback<ResultBean>() {
            @Override
            public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
//                if (response.body().getError().equals("200")) {
//                    mView.defavoriteSuccess();
//                } else if (response.body().getError().equals("500")) {
//                    mView.showError("取消收藏失败");
//                }
                mView.defavoriteSuccess();
            }

            @Override
            public void onFailure(Call<ResultBean> call, Throwable t) {
                mView.errorNetWork(t);
            }
        });
    }
}
