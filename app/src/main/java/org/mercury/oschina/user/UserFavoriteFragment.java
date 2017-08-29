package org.mercury.oschina.user;

import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.base.BaseRecyclerViewFragment;
import org.mercury.oschina.bean.Favorite;
import org.mercury.oschina.http.HttpApi;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Mercury on 2016-08-14 19:33:46.
 * 收藏列表
 */
public class UserFavoriteFragment extends BaseRecyclerViewFragment<Favorite> {


    @Override
    protected void response(Call<Favorite> call, Response<Favorite> response) {

    }

    @Override
    protected Call<Favorite> getCall(HttpApi retrofitCall) {
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
