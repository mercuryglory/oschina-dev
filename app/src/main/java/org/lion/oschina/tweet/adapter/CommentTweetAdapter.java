package org.lion.oschina.tweet.adapter;

import org.lion.oschina.tweet.bean.Comment;
import org.lion.oschina.tweet.holder.BasicHolder;
import org.lion.oschina.tweet.holder.CommentTweetHolder;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/15
 * 描述:      ${TODO}
 */
public class CommentTweetAdapter extends BasicAdapter<Comment>{
    @Override
    public BasicHolder createViewHolder() {
        return new CommentTweetHolder();
    }
}
