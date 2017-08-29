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
import org.mercury.oschina.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        Comment preComment = position != 0 ? getItem(position - 1) : null;
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
                formatTime(preComment, data, senderViewHolder.tvSendTime);
                break;
            case SENDER_PICTURE:
                SenderPictureViewHolder senderPictureViewHolder = (SenderPictureViewHolder) h;
                formatTime(preComment, data, senderPictureViewHolder.tvSendTime);
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
                formatTime(preComment, data, receiverViewHolder.tvSendTime);
                break;
            case RECEIVER_PICTURE:
                ReceiverPictureViewHolder receiverPictureViewHolder = (ReceiverPictureViewHolder) h;
                receiverPictureViewHolder.ivReceiverPic.setImageResource(R.mipmap.quick_option_album_nor);
                formatTime(preComment, data, receiverPictureViewHolder.tvSendTime);
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

    private void formatTime(Comment preComment, Comment currentComment, TextView tvTime) {
        tvTime.setVisibility(View.GONE);
        //第0条一定是要展示格式化时间的
        if (preComment == null) {
            tvTime.setVisibility(View.VISIBLE);
            tvTime.setText(formatTime(currentComment.getPubDate()));

        } else {
            if (checkTime(preComment.getPubDate(), currentComment.getPubDate())) {
                tvTime.setVisibility(View.VISIBLE);
                tvTime.setText(formatTime(currentComment.getPubDate()));
            }
        }
    }

    private String formatTime(String time) {
        if(TextUtils.isEmpty(time))
            return "";
        Date date = StringUtils.toDate(time);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dayFormat = new SimpleDateFormat("MM月dd日");
        SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE", Locale.CHINESE);
        return weekFormat.format(date) + "," + dayFormat.format(date) + "," + timeFormat.format
                (date);
    }

    //如果两个条目之间的发布时间相差超过5分钟
    private boolean checkTime(String preTime, String currentTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long first = format.parse(preTime).getTime();
            long second = format.parse(currentTime).getTime();
            return second - first > 300000;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
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
