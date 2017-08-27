package org.mercury.oschina.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.tweet.bean.Comment;
import org.mercury.oschina.tweet.util.TweetParser;
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
        switch (getItemViewType(position)) {
            case SENDER:
                SenderViewHolder senderViewHolder = (SenderViewHolder) h;
                if (!TextUtils.isEmpty(data.getContent())) {
                    String content = data.getContent().replaceAll("[\n\\s]+", " ");
                    senderViewHolder.tvSendText.setText(TweetParser.getInstance().parse(mContext, content));
                    senderViewHolder.tvSendText.setMovementMethod(LinkMovementMethod.getInstance());
                    senderViewHolder.tvSendText.setFocusable(false);
                    senderViewHolder.tvSendText.setLongClickable(false);
                }
                break;
            case SENDER_PICTURE:
                SenderPictureViewHolder senderPictureViewHolder = (SenderPictureViewHolder) h;
                break;
            case RECEIVER:
                ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder) h;
                if (!TextUtils.isEmpty(data.getContent())) {
                    String content = data.getContent().replaceAll("[\n\\s]+", " ");
                    receiverViewHolder.tvReceiveText.setText(TweetParser.getInstance().parse(mContext, content));
                    receiverViewHolder.tvReceiveText.setMovementMethod(LinkMovementMethod.getInstance());
                    receiverViewHolder.tvReceiveText.setFocusable(false);
                    receiverViewHolder.tvReceiveText.setLongClickable(false);
                }
                break;
            case RECEIVER_PICTURE:
                ReceiverPictureViewHolder receiverPictureViewHolder = (ReceiverPictureViewHolder) h;
                receiverPictureViewHolder.ivReceiverPic.setImageResource(R.mipmap.quick_option_album_nor);
                break;

            default:
                break;

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SENDER) {
            return new SenderViewHolder(mInflater.inflate(R.layout.item_list_send_text,
                    parent, false));
        } else if (viewType == SENDER_PICTURE) {
            return new SenderPictureViewHolder(mInflater.inflate(R.layout.item_list_send_picture,
                    parent, false));
        } else if (viewType == RECEIVER) {
            return new ReceiverViewHolder(mInflater.inflate(R.layout.item_list_receive_text,
                    parent, false));
        } else {
            return new ReceiverPictureViewHolder(mInflater.inflate(R.layout.item_list_receive_picture,
                    parent, false));
        }
    }

    private static class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView tvSendTime;
        TextView tvSendText;

        public SenderViewHolder(View itemView) {
            super(itemView);
            tvSendTime = (TextView) itemView.findViewById(R.id.tv_send_time);
            tvSendText = (TextView) itemView.findViewById(R.id.tv_send_text);
        }
    }

    private static class SenderPictureViewHolder extends RecyclerView.ViewHolder {
        TextView  tvSendTime;
        ImageView ivSenderPic;


        public SenderPictureViewHolder(View itemView) {
            super(itemView);
            tvSendTime = (TextView) itemView.findViewById(R.id.tv_send_time);
            ivSenderPic = (ImageView) itemView.findViewById(R.id.iv_sender_picture);
        }
    }

    private static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView tvSendTime;
        TextView tvReceiveText;

        public ReceiverViewHolder(View itemView) {
            super(itemView);
            tvSendTime = (TextView) itemView.findViewById(R.id.tv_send_time);
            tvReceiveText = (TextView) itemView.findViewById(R.id.tv_receive_text);
        }
    }

    private static class ReceiverPictureViewHolder extends RecyclerView.ViewHolder {
        TextView  tvSendTime;
        ImageView ivReceiverPic;

        public ReceiverPictureViewHolder(View itemView) {
            super(itemView);
            tvSendTime = (TextView) itemView.findViewById(R.id.tv_send_time);
            ivReceiverPic = (ImageView) itemView.findViewById(R.id.iv_receiver_picture);
        }
    }
}
