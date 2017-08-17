package org.mercury.oschina.synthesis.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.synthesis.bean.Blog;
import org.mercury.oschina.utils.GeneralUtils;
import org.mercury.oschina.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mercury on 2016-08-15 21:52:18.
 * 博客列表 适配器
 */
public class BlogListAdapter extends BaseRecyclerAdapter<Blog> {


    public BlogListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, Blog data, int position) {
        ViewHolder holder = (ViewHolder) h;
        SpannableStringBuilder span = new SpannableStringBuilder();
        boolean isToday = StringUtils.isSameDay(StringUtils.getCurTimeStr(), data.getPubDate());
        if (isToday) {
            span.append("[ ");
            Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_label_today);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(drawable);
            span.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (data.getType() == 1) {
            //原创
            span.append("[ ");
            Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_label_originate);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(drawable);
            span.setSpan(imageSpan, isToday ? 2 : 0, isToday ? 3 : 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else if (data.getType() == 4) {
            //转载
            span.append("[ ");
            Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_label_reprint);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(drawable);
            span.setSpan(imageSpan, isToday ? 2 : 0, isToday ? 3 : 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        holder.tvBlogTitle.setText(span.append(data.getTitle()));
        holder.tvBlogAuthor.setText("@" + data.getAuthor());
        holder.tvBlogTime.setText(StringUtils.friendly_time(data.getPubDate()));
        holder.tvCommentCount.setText(data.getCommentCount() + "");

        if (GeneralUtils.getAllVisitedItem().containsValue(data.getId())) {
            holder.tvBlogTitle.setTextColor(ContextCompat.getColor(mContext, R.color.grey_500));
        } else {
            holder.tvBlogTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }
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
