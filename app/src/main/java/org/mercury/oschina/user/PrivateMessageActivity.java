package org.mercury.oschina.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.mercury.oschina.base.BaseChatViewActivity;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.tweet.bean.Comment;
import org.mercury.oschina.tweet.bean.CommentResponse;
import org.mercury.oschina.user.adapter.PrivateMessageAdapter;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by wang.zhonghao on 2017/8/25.
 * 私信当中跟用户往来的信息列表
 * 类似QQ,不是一般的到底部自动加载更多,而是列表拉到上面以后再去拉取之前的消息内容
 */

public class PrivateMessageActivity extends BaseChatViewActivity<CommentResponse> {

    private PrivateMessageAdapter mAdapter;
    public static final int CATALOG_MSG = 4;
    public static final String USER_ID = "user_id";

    private int userId;

    //自定义一个RecyclerView控件,只有下拉刷新的

    @Override
    protected void response(Call<CommentResponse> call, Response<CommentResponse> response) {
        CommentResponse body = response.body();
        if (body != null && body.getCommentList() != null) {
            Collections.reverse(body.getCommentList());
            if (isRefreshing) {
                refresh(body.getCommentList());
            } else {
                mAdapter.setData(body.getCommentList());
                mRecyclerView.scrollToPosition(mAdapter.getItemCount());
            }
        } else {
            mRecyclerView.refreshComplete();
        }

    }

    private void scrollToBottom() {
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void refresh(List<Comment> commentList) {
        mRecyclerView.refreshComplete();
        isRefreshing = false;
        if (commentList != null) {
            mAdapter.addAllInPos(commentList, 0);
        }

    }

    @Override
    protected Call<CommentResponse> getCall(HttpApi retrofitCall) {
        Call<CommentResponse> commentList = retrofitCall.getCommentList(userId, CATALOG_MSG, pageIndex);
        return commentList;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        PrivateMessageAdapter adapter = new PrivateMessageAdapter(this);
        mAdapter = adapter;
        return mAdapter;
    }

    public static void show(Context context, int userId) {
        Intent intent = new Intent(context, PrivateMessageActivity.class);
        intent.putExtra(USER_ID, userId);
        context.startActivity(intent);

    }

    @Override
    protected void initBundle(Bundle bundle) {
        userId = bundle.getInt(USER_ID, 0);
    }

    @Override
    public void onRefresh() {
        //这里进行加载更多的操作
        isRefreshing = true;
        pageIndex++;
        requestData();
    }
}
