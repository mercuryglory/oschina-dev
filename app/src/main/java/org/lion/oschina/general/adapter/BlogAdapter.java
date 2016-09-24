package org.lion.oschina.general.adapter;

import org.lion.oschina.general.holder.BasicHolder;
import org.lion.oschina.general.holder.BlogHolder;
import org.lion.oschina.general.bean.BlogListBean;

/**
 * Created by more on 2016-08-15 21:52:18.
 */
public class BlogAdapter extends BasicAdapter<BlogListBean.ResultBean.ItemsBean> {

    @Override
    protected BasicHolder createHolder(int position) {
        return new BlogHolder();
    }
}
