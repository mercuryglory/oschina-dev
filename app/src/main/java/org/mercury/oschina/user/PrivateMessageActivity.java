package org.mercury.oschina.user;

import android.content.Context;
import android.content.Intent;

import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.base.BaseRecyclerViewActivity;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.tweet.bean.CommentResponse;
import org.mercury.oschina.user.adapter.PrivateMessageAdapter;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by wang.zhonghao on 2017/8/25.
 * 私信当中跟用户往来的信息列表
 * 类似QQ,不是一般的到底部自动加载更多,而是列表拉倒上面以后再去拉取之前的消息内容
 */

public class PrivateMessageActivity extends BaseRecyclerViewActivity<CommentResponse> {

    private PrivateMessageAdapter mAdapter;
    public static final int CATALOG_MSG = 4;
    public static final String USER_ID = "user_id";

    @Override
    protected void response(Call<CommentResponse> call, Response<CommentResponse> response) {
        CommentResponse body = response.body();
        if (body != null) {
            mAdapter.setData(body.getCommentList());

        }
    }

    @Override
    protected Call<CommentResponse> getCall(HttpApi retrofitCall) {
        Call<CommentResponse> commentList = retrofitCall.getCommentList(1, CATALOG_MSG, pageIndex);
        return commentList;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        PrivateMessageAdapter adapter = new PrivateMessageAdapter(this);
        mAdapter = adapter;
        return mAdapter;
    }

    @Override
    public void onRefresh() {
        //这里进行加载更多的操作
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected void initData() {
        super.initData();
        mRecyclerView.setCanloadMore(false);

    }

    public static void show(Context context, int userId) {
        Intent intent = new Intent(context, PrivateMessageActivity.class);
        intent.putExtra(USER_ID, userId);
        context.startActivity(intent);

    }
}
