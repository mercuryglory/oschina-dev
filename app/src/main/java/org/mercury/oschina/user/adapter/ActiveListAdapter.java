package org.mercury.oschina.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.tweet.util.GlideUtils;
import org.mercury.oschina.tweet.util.TweetParser;
import org.mercury.oschina.user.bean.Active;
import org.mercury.oschina.utils.StringUtils;
import org.mercury.oschina.widget.TweetPicturesLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wang.zhonghao on 2017/8/22.
 */

public class ActiveListAdapter extends BaseRecyclerAdapter<Active> {

    public ActiveListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, Active data, int position) {
        ViewHolder holder = (ViewHolder) h;
        GlideUtils.loadCircleImage(mContext, data.getPortrait(), holder.ivUserFace);

        switch (data.getCatalog()) {
            case 1:
                holder.tvActiveCatalog.setText("新闻");
                break;
            case 2:
                holder.tvActiveCatalog.setText("问答");
                break;
            case 3:
                holder.tvActiveCatalog.setText("动弹");
                break;
            case 4:
                holder.tvActiveCatalog.setText("博客");
                break;
            case 0:
                holder.tvActiveCatalog.setText("其它");
                break;
            default:
                break;
        
        }
        if (TextUtils.isEmpty(data.getObjectTitle())) {
            holder.tvActiveTitle.setVisibility(View.GONE);
        } else {
            holder.tvActiveTitle.setVisibility(View.VISIBLE);
            holder.tvActiveTitle.setText(data.getObjectTitle());
        }
        if (TextUtils.isEmpty(data.getMessage())) {
            holder.tvActiveMsg.setVisibility(View.GONE);
        } else {
            //设置富文本
            holder.tvActiveMsg.setVisibility(View.VISIBLE);
            String content = data.getMessage().replaceAll("[\n\\s]+", " ");
            holder.tvActiveMsg.setText(TweetParser.getInstance().parse(mContext, content));
            holder.tvActiveMsg.setFocusable(false);
            holder.tvActiveMsg.setLongClickable(false);
        }

        if (TextUtils.isEmpty(data.getTweetImage())) {
            holder.layoutTweetPicture.setVisibility(View.GONE);
        } else {
            holder.layoutTweetPicture.setVisibility(View.VISIBLE);
            //加载内容图片,可能一张或多张
            holder.layoutTweetPicture.setImage(data.getTweetImage(), null);

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
                break;
        
        }

        holder.tvPubTime.setText(StringUtils.friendly_time(data.getPubDate()));
        holder.tvCommentCount.setText(data.getCommentCount() + "");
        

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_acitve, parent, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_user_face)
        CircleImageView     ivUserFace;
        @Bind(R.id.tv_active_catalog)
        TextView tvActiveCatalog;
        @Bind(R.id.tv_active_title)
        TextView            tvActiveTitle;
        @Bind(R.id.tv_active_msg)
        TextView       tvActiveMsg;
        @Bind(R.id.layout_tweet_picture)
        TweetPicturesLayout layoutTweetPicture;
        @Bind(R.id.tv_pub_time)
        TextView            tvPubTime;
        @Bind(R.id.tv_pub_platform)
        TextView            tvPubPlatform;
        @Bind(R.id.tv_comment_count)
        TextView            tvCommentCount;
        @Bind(R.id.rl_info)
        RelativeLayout      rlInfo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
