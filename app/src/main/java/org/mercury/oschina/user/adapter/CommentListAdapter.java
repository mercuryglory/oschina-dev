package org.mercury.oschina.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.tweet.activity.OtherUserHomeActivity;
import org.mercury.oschina.tweet.util.GlideUtils;
import org.mercury.oschina.tweet.util.TweetParser;
import org.mercury.oschina.user.bean.Active;
import org.mercury.oschina.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wang.zhonghao on 2017/8/22.
 * 我的消息 评论列表 适配器
 */

public class CommentListAdapter extends BaseRecyclerAdapter<Active> {

    public CommentListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, final Active data, int position) {
        ViewHolder holder = (ViewHolder) h;
        GlideUtils.loadCircleImage(mContext, data.getPortrait(), holder.ivUserFace);
        holder.ivUserFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherUserHomeActivity.show(mContext, new Integer(data.getAuthorid()).longValue());
            }
        });

        holder.tvAuthor.setText(data.getAuthor());

        if (TextUtils.isEmpty(data.getMessage())) {
            holder.tvMessage.setVisibility(View.GONE);
        } else {
            holder.tvMessage.setVisibility(View.VISIBLE);
            String content = data.getMessage().replaceAll("[\n\\s]+", " ");
            holder.tvMessage.setMovementMethod(LinkMovementMethod.getInstance());
            holder.tvMessage.setText(TweetParser.getInstance().parse(mContext, content));
            holder.tvMessage.setFocusable(false);
            holder.tvMessage.setLongClickable(false);
        }
        if (TextUtils.isEmpty(data.getObjectBody())) {
            holder.tvBody.setVisibility(View.GONE);
        } else {
            //设置富文本
            holder.tvBody.setVisibility(View.VISIBLE);
            String content = data.getObjectBody().replaceAll("[\n\\s]+", " ");
            holder.tvBody.setMovementMethod(LinkMovementMethod.getInstance());
            holder.tvBody.setText(TweetParser.getInstance().parse(mContext, content));
            holder.tvBody.setFocusable(false);
            holder.tvBody.setLongClickable(false);
        }

        switch (data.getAppClient()) {
            case 3:
                holder.tvPubPlatform.setText("Android");
                break;
            case 4:
                holder.tvPubPlatform.setText("IOS");
                break;
            case 5:
                holder.tvPubPlatform.setText("WP");
                break;

            default:
                holder.tvPubPlatform.setText("");
                break;

        }

        holder.tvPubTime.setText(StringUtils.friendly_time(data.getPubDate()));
        holder.tvCommentCount.setText(data.getCommentCount() + "");


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_my_comment, parent, false));
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_user_face)
        ImageView      ivUserFace;
        @Bind(R.id.tv_author)
        TextView       tvAuthor;
        @Bind(R.id.tv_message)
        TextView       tvMessage;
        @Bind(R.id.tv_body)
        TextView       tvBody;
        @Bind(R.id.tv_pub_time)
        TextView       tvPubTime;
        @Bind(R.id.tv_pub_platform)
        TextView       tvPubPlatform;
        @Bind(R.id.tv_comment_count)
        TextView       tvCommentCount;
        @Bind(R.id.rl_info)
        RelativeLayout rlInfo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
