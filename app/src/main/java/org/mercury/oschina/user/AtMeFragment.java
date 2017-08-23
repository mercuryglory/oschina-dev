package org.mercury.oschina.user;

import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.base.BaseRecyclerViewFragment;
import org.mercury.oschina.http.HttpApi;

import retrofit2.Call;
import retrofit2.Response;


/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      @我 消息列表
 */
public class AtMeFragment extends BaseRecyclerViewFragment {


    @Override
    protected void response(Call call, Response response) {

    }

    @Override
    protected Call getCall(HttpApi retrofitCall) {
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
