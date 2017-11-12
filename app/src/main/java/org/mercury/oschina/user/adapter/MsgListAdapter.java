package org.mercury.oschina.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.tweet.util.GlideUtils;
import org.mercury.oschina.tweet.util.TweetParser;
import org.mercury.oschina.user.bean.Message;
import org.mercury.oschina.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wang.zhonghao on 2017/8/19.
 * 私信列表  适配器
 */

public class MsgListAdapter extends BaseRecyclerAdapter<Message> {

    public MsgListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, Message data, int position) {
        ViewHolder holder = (ViewHolder) h;
        GlideUtils.loadCircleImage(mContext, data.getPortrait(), holder.ivPortrait);
        holder.tvFriend.setText(data.getFriendname());

        String content = data.getContent().replaceAll("[\n\\s]+", " ");
        holder.tvContent.setText(TweetParser.getInstance().parse(mContext, content));
        holder.tvContent.setFocusable(false);
        holder.tvContent.setLongClickable(false);

        holder.tvTime.setText(StringUtils.friendly_time(data.getPubDate()));

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_message_private, parent, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_portrait)
        ImageView ivPortrait;
        @BindView(R.id.tv_friend)
        TextView  tvFriend;
        @BindView(R.id.tv_content)
        TextView  tvContent;
        @BindView(R.id.tv_time)
        TextView  tvTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
