package org.mercury.oschina.general.adapter;

import org.mercury.oschina.general.holder.BasicHolder;
import org.mercury.oschina.general.holder.HotHolder;
import org.mercury.oschina.general.bean.HotListBean;

/**
 * Created by more on 2016-08-15 21:10:29.
 */
public class HotAdapter extends BasicAdapter<HotListBean.ObjListBean> {
    @Override
    protected BasicHolder createHolder(int position) {
        return new HotHolder();
    }
}
