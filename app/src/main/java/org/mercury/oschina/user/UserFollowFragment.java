package org.mercury.oschina.user;

import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.base.BaseRecyclerViewFragment;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.tweet.bean.User;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Mercury on 2016-08-14 19:33:46.
 * 关注列表
 */
public class UserFollowFragment extends BaseRecyclerViewFragment<User> {

    @Override
    protected void response(Call<User> call, Response<User> response) {

    }

    @Override
    protected Call<User> getCall(HttpApi retrofitCall) {
        return null;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        return null;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
