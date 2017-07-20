package org.mercury.oschina.synthesis.adapter;

import org.mercury.oschina.synthesis.holder.BasicHolder;
import org.mercury.oschina.synthesis.holder.HotHolder;
import org.mercury.oschina.synthesis.bean.HotListBean;

/**
 * Created by more on 2016-08-15 21:10:29.
 */
public class HotAdapter extends BasicAdapter<HotListBean.ObjListBean> {
    @Override
    protected BasicHolder createHolder(int position) {
        return new HotHolder();
    }
}
