package org.mercury.oschina.synthesis.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.synthesis.bean.Blog;
import org.mercury.oschina.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by more on 2016-08-15 21:52:18.
 */
public class BlogListAdapter extends BaseRecyclerAdapter<Blog> {


    public BlogListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, Blog data, int position) {
        ViewHolder holder = (ViewHolder) h;
        holder.tvBlogTitle.setText(data.getTitle());
        holder.tvBlogAuthor.setText(data.getAuthor());
        holder.tvBlogTime.setText(StringUtils.friendly_time(data.getPubDate()));
        holder.tvCommentCount.setText(data.getCommentCount() + "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mInflater.inflate(R.layout.item_fragment_blog, parent, false);
        return new ViewHolder(inflate);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_blog_title)
        TextView tvBlogTitle;
        @Bind(R.id.tv_blog_author)
        TextView tvBlogAuthor;
        @Bind(R.id.tv_blog_time)
        TextView tvBlogTime;
        @Bind(R.id.tv_comment_count)
        TextView tvCommentCount;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
