package org.mercury.oschina.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.tweet.bean.Comment;
import org.mercury.oschina.utils.AccessTokenHelper;

/**
 * Created by wang.zhonghao on 2017/8/25.
 */

public class PrivateMessageAdapter extends BaseRecyclerAdapter<Comment> {

    private static final int SENDER = 1;
    private static final int SENDER_PICTURE = 2;
    private static final int RECEIVER = 3;
    private static final int RECEIVER_PICTURE = 4;


    public PrivateMessageAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        Comment item = getItem(position);
        if (item.getCommentAuthorId() == AccessTokenHelper.getUserId()) {
            //受限于openapi返回的内容，目前并不能获取真实发送的图片url，只能以固定图片代替
            if ("[图片]".equalsIgnoreCase(item.getContent())) {
                return SENDER_PICTURE;
            }
            return SENDER;
        } else {
            if ("[图片]".equalsIgnoreCase(item.getContent())) {
                return RECEIVER_PICTURE;
            }
            return RECEIVER;
        }

    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, Comment data, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SENDER) {
            return new SenderViewHolder(mInflater.inflate(R.layout.item_list_receive_text,
                    parent, false));
        }
        return null;
    }

    private static class SenderViewHolder extends RecyclerView.ViewHolder {

        public SenderViewHolder(View itemView) {
            super(itemView);
        }
    }

}
