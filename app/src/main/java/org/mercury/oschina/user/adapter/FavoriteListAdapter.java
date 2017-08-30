package org.mercury.oschina.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.user.bean.Favorite;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wang.zhonghao on 2017/8/19.
 * 收藏列表  适配器
 */

public class FavoriteListAdapter extends BaseRecyclerAdapter<Favorite> {

    public FavoriteListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, Favorite data, int position) {
        ViewHolder holder = (ViewHolder) h;
        String tag = "";
        switch (data.getType()) {
            case 1:
                tag = "软件";
                break;
            case 2:
                tag = "问答";
                break;
            case 3:
                tag = "博客";
                break;
            case 4:
                tag = "新闻";
                break;
            case 5:
                tag = "代码";
                break;
            case 7:
                tag = "翻译";
                break;
            default:
                break;
        }
        holder.tvType.setText(tag);
        holder.tvTitle.setText(data.getTitle());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_favorite, parent, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_type)
        TextView tvType;
        @Bind(R.id.tv_title)
        TextView  tvTitle;
        
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
