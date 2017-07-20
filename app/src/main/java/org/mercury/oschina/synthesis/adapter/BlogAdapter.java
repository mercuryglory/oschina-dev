package org.mercury.oschina.synthesis.adapter;

import org.mercury.oschina.synthesis.holder.BasicHolder;
import org.mercury.oschina.synthesis.holder.BlogHolder;
import org.mercury.oschina.synthesis.bean.BlogListBean;

/**
 * Created by more on 2016-08-15 21:52:18.
 */
public class BlogAdapter extends BasicAdapter<BlogListBean.ResultBean.ItemsBean> {

    @Override
    protected BasicHolder createHolder(int position) {
        return new BlogHolder();
    }
}
