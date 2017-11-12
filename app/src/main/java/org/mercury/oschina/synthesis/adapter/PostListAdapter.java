package org.mercury.oschina.synthesis.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.synthesis.bean.Post;
import org.mercury.oschina.tweet.util.GlideUtils;
import org.mercury.oschina.utils.GeneralUtils;
import org.mercury.oschina.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mercury on 2016-08-15 21:52:18.
 * 帖子列表 适配器
 */
public class PostListAdapter extends BaseRecyclerAdapter<Post> {


    public PostListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, Post data, int position) {
        ViewHolder holder = (ViewHolder) h;

        GlideUtils.loadCircleImage(mContext, data.getPortrait(), holder.ivPortrait);
        holder.tvBlogTitle.setText(data.getTitle());
        holder.tvBlogAuthor.setText("@" + data.getAuthor());
        holder.tvBlogTime.setText(StringUtils.friendly_time(data.getPubDate()));
        holder.tvInfoView.setText(data.getViewCount() + "");
        holder.tvInfoComment.setText(data.getAnswerCount() + "");

        if (GeneralUtils.getAllVisitedItem().containsValue(data.getId())) {
            holder.tvBlogTitle.setTextColor(ContextCompat.getColor(mContext, R.color.grey_500));
        } else {
            holder.tvBlogTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mInflater.inflate(R.layout.item_fragment_post, parent, false);
        return new ViewHolder(inflate);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_portrait)
        ImageView ivPortrait;
        @BindView(R.id.tv_blog_title)
        TextView  tvBlogTitle;
        @BindView(R.id.tv_blog_author)
        TextView  tvBlogAuthor;
        @BindView(R.id.tv_blog_time)
        TextView  tvBlogTime;
        @BindView(R.id.iv_info_view)
        ImageView ivInfoView;
        @BindView(R.id.tv_info_view)
        TextView  tvInfoView;
        @BindView(R.id.iv_info_comment)
        ImageView ivInfoComment;
        @BindView(R.id.tv_info_comment)
        TextView  tvInfoComment;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
