package org.lion.oschina.tweet.adapter;

import org.lion.oschina.bean.Active;
import org.lion.oschina.tweet.holder.BasicHolder;
import org.lion.oschina.tweet.holder.UserHomeHolder;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/18
 * 描述:      ${TODO}
 */
public class UserHomeAdapter extends BasicAdapter<Active> {
    @Override
    public BasicHolder createViewHolder() {
        return new UserHomeHolder();
    }

}
