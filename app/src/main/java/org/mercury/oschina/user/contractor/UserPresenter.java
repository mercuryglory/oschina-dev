package org.mercury.oschina.user.contractor;

import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.tweet.bean.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wang.zhonghao on 2017/9/12.
 */

public class UserPresenter implements UserContract.Presenter {

    private UserContract.View mView;

    public UserPresenter(UserContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void onRefreshing() {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<User> myInfo = retrofitCall.getMyInfo();
        myInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User body = response.body();
                if (body != null) {
                    mView.refreshSuccess(body);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mView.errorNetWork(t);
            }
        });
    }

    //更新头像
    @Override
    public void portraitUpdate() {

    }
}
