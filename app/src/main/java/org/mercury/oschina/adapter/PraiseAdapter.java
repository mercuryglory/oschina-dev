package org.mercury.oschina.adapter;

import org.mercury.oschina.bean.TweetLike;
import org.mercury.oschina.holder.BasicHolder;
import org.mercury.oschina.holder.PraiseViewHolder;

import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/17 16:22
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class PraiseAdapter extends BasicAdapter<TweetLike> {

    public PraiseAdapter(List<TweetLike> showItems) {
        super(showItems);
    }

    @Override
    public BasicHolder createViewHolder(int postion) {
        return new PraiseViewHolder();
    }
}
