package org.mercury.oschina.tweet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mercury.oschina.Constant;
import org.mercury.oschina.R;
import org.mercury.oschina.AppContext;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.tweet.activity.UserHomeActivity;
import org.mercury.oschina.tweet.bean.Tweet;
import org.mercury.oschina.tweet.bean.User;
import org.mercury.oschina.tweet.util.GlideUtils;
import org.mercury.oschina.tweet.util.TweetParser;
import org.mercury.oschina.tweet.widget.TweetTextView;
import org.mercury.oschina.utils.StringUtils;
import org.mercury.oschina.widget.TweetPicturesLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/15
 * 描述:      最新动弹 列表 适配器
 */
public class NewTweetAdapter extends BaseRecyclerAdapter<Tweet> {

    public NewTweetAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_tweet_new, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, final Tweet data, int
            position) {
        ViewHolder holder = (ViewHolder) h;
        GlideUtils.loadCircleImage(mContext, data.getPortrait(),
                holder.ivTweetFace);

        //点击头像图片后跳转到该用户中心界面
        holder.ivTweetFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setId(data.getAuthorid());
                user.setName(data.getAuthor());

                Intent intent = new Intent(AppContext.context, UserHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Constant.USER_ID, user);
                AppContext.context().startActivity(intent);
            }
        });

        holder.tvTweetName.setText(data.getAuthor());

        //设置内容中的富文本
        if (!TextUtils.isEmpty(data.getBody())) {
            String content = data.getBody().replaceAll("[\n\\s]+", " ");
            holder.tvTweetBody.setText(TweetParser.getInstance().parse(mContext, content));
            holder.tvTweetBody.setMovementMethod(LinkMovementMethod.getInstance());
            holder.tvTweetBody.setFocusable(false);
            holder.tvTweetBody.setDispatchToParent(true);
            holder.tvTweetBody.setLongClickable(false);
        }

        //赞.直接调用bean里面已经封装好的方法
        //        tweet.setLikeUsers(parent.getContext(), mTvTweetLike, true);

        if (TextUtils.isEmpty(data.getImgBig()) && TextUtils.isEmpty(data.getImgSmall())) {
            holder.layoutTweetImage.setVisibility(View.GONE);
        } else {
            holder.layoutTweetImage.setVisibility(View.VISIBLE);
            //加载内容图片,可能一张或多张
            holder.layoutTweetImage.setImage(data.getImgSmall(),data.getImgBig());

        }


        holder.tvTweetTime.setText(StringUtils.friendly_time(data.getPubDate()));
        holder.tvTweetCommentCount.setText(data.getCommentCount() + "");

    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_tweet_face)
        ImageView           ivTweetFace;
        @Bind(R.id.tv_tweet_name)
        TextView            tvTweetName;
        @Bind(R.id.tv_tweet_body)
        TweetTextView       tvTweetBody;
        @Bind(R.id.layout_tweet_picture)
        TweetPicturesLayout layoutTweetImage;
        @Bind(R.id.tv_tweet_time)
        TextView            tvTweetTime;
        @Bind(R.id.tv_tweet_comment_count)
        TextView            tvTweetCommentCount;
        @Bind(R.id.rl_info)
        RelativeLayout      rlInfo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

