package org.lion.oschina.tweet.adapter;

import org.lion.oschina.tweet.bean.Tweet;
import org.lion.oschina.tweet.holder.BasicHolder;
import org.lion.oschina.tweet.holder.NewTweetHolder;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/15
 * 描述:      ${TODO}
 */
public class NewTweetAdapter extends BasicAdapter<Tweet> {
    @Override
    public BasicHolder createViewHolder() {
        return new NewTweetHolder();

    }

}
