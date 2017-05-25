package org.mercury.oschina.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.bean.Blog;

import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/18 17:58
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class MyBlogAdapter extends BaseAdapter {

    private final Context    mContext;
    private final List<Blog> mDatas;

    public MyBlogAdapter(Context context, List<Blog> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Blog getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
          convertView=  View.inflate(parent.getContext(), R.layout.item_blog, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
             holder = (ViewHolder) convertView.getTag();
        }
        Blog blog = getItem(position);
        holder.mTvBlogCommentcount.setText(blog.getCommentCount()+"");
        holder.mTvBlogCompany.setText(blog.getAuthor());
        holder.mTvBlogTitle.setText(blog.getTitle());
        holder.mTvBlogTime.setText(blog.getPubDate());
        return convertView;
    }

     class ViewHolder {

        TextView mTvBlogTitle;

        TextView mTvBlogCompany;

        TextView mTvBlogTime;

        TextView mTvBlogCommentcount;

      public  ViewHolder(View view) {

            mTvBlogTitle= (TextView) view.findViewById(R.id.tv_blog_title);
            mTvBlogCompany = (TextView) view.findViewById(R.id.tv_blog_company);
            mTvBlogTime = (TextView) view.findViewById(R.id.tv_blog_time);
            mTvBlogCommentcount = (TextView) view.findViewById(R.id.tv_blog_commentcount);

        }
    }
}
