package org.mercury.oschina.explorer.adapter;

import org.mercury.oschina.explorer.bean.FindUserBean;
import org.mercury.oschina.explorer.holder.FindUserHolder;

import java.util.List;

/**
 * Created by Mercury on 2016-08-19 12:55:43.
 */
public class FindUserAdapter extends BasicAdapter<FindUserBean.ObjListBean> {

    public FindUserAdapter(List<FindUserBean.ObjListBean> list) {
        super(list);
    }

    @Override
    public BasicHolder createHolder(int position) {
        return new FindUserHolder();
    }
}
