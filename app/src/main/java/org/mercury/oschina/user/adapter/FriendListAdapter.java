package org.mercury.oschina.user.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.tweet.util.GlideUtils;
import org.mercury.oschina.user.bean.User;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wang.zhonghao on 2017/8/22.
 * 我的消息 评论列表 适配器
 */

public class FriendListAdapter extends BaseRecyclerAdapter<User> {

    public FriendListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, final User data, int position) {
        ViewHolder holder = (ViewHolder) h;
        GlideUtils.loadCircleImage(mContext, data.getPortrait(), holder.ivUserFace);

        holder.tvAuthor.setText(data.getName());
        holder.tvExpertise.setText(data.getExpertise());
        // TODO: 2017/8/30 用代码设置textview的drawableRight无效
        if (data.getGender() == 1) {
            holder.tvAuthor.setCompoundDrawables(null, null, ContextCompat.getDrawable(mContext,
                    R.mipmap.userinfo_icon_male), null);
        } else if (data.getGender() == 2) {
            holder.tvAuthor.setCompoundDrawables(null, null, ContextCompat.getDrawable(mContext,
                    R.mipmap.userinfo_icon_female), null);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_friend, parent, false));
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_user_face)
        ImageView      ivUserFace;
        @Bind(R.id.tv_author)
        TextView       tvAuthor;
        @Bind(R.id.tv_expertise)
        TextView       tvExpertise;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
