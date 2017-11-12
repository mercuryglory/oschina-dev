package org.mercury.oschina.tweet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.tweet.bean.Comment;
import org.mercury.oschina.tweet.util.GlideUtils;
import org.mercury.oschina.tweet.util.TweetParser;
import org.mercury.oschina.tweet.widget.TweetTextView;
import org.mercury.oschina.user.OtherUserHomeActivity;
import org.mercury.oschina.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/15
 * 描述:      ${TODO}
 */
public class CommentTweetAdapter extends BaseRecyclerAdapter<Comment> {

    public CommentTweetAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, final Comment comment, int
            position) {
        ViewHolder holder = (ViewHolder) h;
        GlideUtils.loadCircleImage(mContext, comment.getCommentPortrait(), holder.ivTweetFace);
        //头像跳转
        holder.ivTweetFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherUserHomeActivity.show(mContext, comment.getCommentAuthorId());
            }
        });


        holder.tvTweetName.setText(comment.getCommentAuthor());

        //设置富文本
        if (!TextUtils.isEmpty(comment.getContent())) {
            String content = comment.getContent().replaceAll("[\n\\s]+", " ");
            holder.tvTweetBody.setText(TweetParser.getInstance().parse(mContext, content));
            holder.tvTweetBody.setMovementMethod(LinkMovementMethod.getInstance());
            holder.tvTweetBody.setFocusable(false);
            holder.tvTweetBody.setDispatchToParent(true);
            holder.tvTweetBody.setLongClickable(false);
        }

        switch (comment.getClient_type()) {
            case 3:
                holder.tvTweetPlatform.setText("Android");
                break;
            case 4:
                holder.tvTweetPlatform.setText("iPhone");
                break;
            case 5:
                holder.tvTweetPlatform.setText("WP");
                break;
            default:
                break;

        }
        holder.tvTweetTime.setText(StringUtils.friendly_time(comment.getPubDate()));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_tweet_comment, parent, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_tweet_face)
        ImageView     ivTweetFace;
        @BindView(R.id.tv_tweet_name)
        TextView      tvTweetName;
        @BindView(R.id.tv_tweet_time)
        TextView      tvTweetTime;
        @BindView(R.id.tv_tweet_body)
        TweetTextView tvTweetBody;
        @BindView(R.id.tv_tweet_platform)
        TextView      tvTweetPlatform;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
