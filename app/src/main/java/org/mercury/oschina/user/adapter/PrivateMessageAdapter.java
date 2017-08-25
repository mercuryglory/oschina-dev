package org.mercury.oschina.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.tweet.bean.Comment;

/**
 * Created by wang.zhonghao on 2017/8/25.
 */

public class PrivateMessageAdapter extends BaseRecyclerAdapter<Comment> {

    public PrivateMessageAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, Comment data, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}
