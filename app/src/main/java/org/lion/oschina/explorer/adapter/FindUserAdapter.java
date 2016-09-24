package org.lion.oschina.explorer.adapter;

import org.lion.oschina.explorer.bean.FindUserBean;
import org.lion.oschina.explorer.holder.FindUserHolder;
import org.lion.oschina.general.adapter.BasicAdapter;
import org.lion.oschina.general.holder.BasicHolder;

/**
 * Created by more on 2016-08-19 12:55:43.
 */
public class FindUserAdapter extends BasicAdapter<FindUserBean.ObjListBean> {
    @Override
    protected BasicHolder createHolder(int position) {
        return new FindUserHolder();
    }
}
