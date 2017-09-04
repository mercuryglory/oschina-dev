package org.mercury.oschina.synthesis.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.synthesis.bean.NewsDetail;

/**
 * Created by wang.zhonghao on 2017/9/4.
 */

public class RelativeRecommendAdapter extends BaseRecyclerAdapter<NewsDetail.RelativiesBean> {

    public RelativeRecommendAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, NewsDetail.RelativiesBean
            data, int position) {
        ViewHolder holder = (ViewHolder) h;
        holder.tvTitle.setText(data.getTitle());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mInflater.inflate(R.layout.item_relative_recommend, parent, false);
        return new ViewHolder(inflate);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

}
