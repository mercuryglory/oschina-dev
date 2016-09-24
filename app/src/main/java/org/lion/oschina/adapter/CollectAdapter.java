package org.lion.oschina.adapter;

import org.lion.oschina.bean.Favorite;
import org.lion.oschina.holder.BasicHolder;
import org.lion.oschina.holder.CollectViewHolder;

import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/16 16:53
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class CollectAdapter extends BasicAdapter<Favorite> {



    public CollectAdapter(List<Favorite> showItems) {
        super(showItems);

    }

    @Override
    public BasicHolder createViewHolder(int postion) {
        return new CollectViewHolder();
    }
}
