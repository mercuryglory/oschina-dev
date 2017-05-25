package org.mercury.oschina.adapter;

import org.mercury.oschina.bean.Friend;
import org.mercury.oschina.holder.BasicHolder;
import org.mercury.oschina.holder.FansViewHolder;

import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/15 17:32
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class FansAdapter extends BasicAdapter<Friend> {
    public FansAdapter(List<Friend> showItems) {
        super(showItems);
    }

    @Override
    public BasicHolder createViewHolder(int postion) {
        return new FansViewHolder();
    }
}
