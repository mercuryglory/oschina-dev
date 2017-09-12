package org.mercury.oschina.user.contractor;

import org.mercury.oschina.bean.ResultBean;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.tweet.bean.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wang.zhonghao on 2017/9/12.
 */

public class OtherUserPresenter implements OtherUserContract.Presenter {

    private OtherUserContract.View mView;

    public OtherUserPresenter(OtherUserContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void refreshing(long userId) {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<User> otherUserInfo = retrofitCall.getOtherUserInfo(userId);
        otherUserInfo.enqueue(new Callback<User>() {
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

    @Override
    public void addRelation(long userId, int relation) {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<ResultBean> resultBeanCall = retrofitCall.addRelation(userId, relation);
        resultBeanCall.enqueue(new Callback<ResultBean>() {
            @Override
            public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
                ResultBean body = response.body();
                if (body != null) {
                    if ("200".equals(body.getError())) {
                        //2-已关注，3-未关注
                        mView.updateIcon(body.getRelation());
                    } else if ("500".equals(body.getError())) {
                        if (body.getRelation() == 2) {
                            //已经添加关注过
                            mView.showError("已经添加过关注");
                        } else if (body.getRelation() == 3) {
                            //取消关注失败,还没有添加关注
                            mView.showError("还没有添加过关注");
                        }
                    }
                } else {
                    mView.showError("数据错误");
                }
            }

            @Override
            public void onFailure(Call<ResultBean> call, Throwable t) {
                mView.errorNetWork(t);
            }
        });
    }



    @Override
    public void attachView(OtherUserContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
