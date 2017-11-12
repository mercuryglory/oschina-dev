package org.mercury.oschina.user.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

import butterknife.BindView;
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
        Drawable male = ContextCompat.getDrawable(mContext,
                R.mipmap.userinfo_icon_male);
        int width = male.getIntrinsicHeight();
        male.setBounds(0, 0, width, width);
        Drawable female = ContextCompat.getDrawable(mContext,
                R.mipmap.userinfo_icon_female);
        female.setBounds(0, 0, width, width);
        if (data.getGender() == 1) {
            holder.tvAuthor.setCompoundDrawables(null, null, male, null);
        } else if (data.getGender() == 2) {
            holder.tvAuthor.setCompoundDrawables(null, null, female, null);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_friend, parent, false));
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_user_face)
        ImageView      ivUserFace;
        @BindView(R.id.tv_author)
        TextView       tvAuthor;
        @BindView(R.id.tv_expertise)
        TextView       tvExpertise;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
