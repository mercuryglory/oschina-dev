package org.mercury.oschina.explorer.contract;

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
    public void refreshData(String ident) {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<SoftwareDetail> software = retrofitCall.getSoftware(ident);
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
}
