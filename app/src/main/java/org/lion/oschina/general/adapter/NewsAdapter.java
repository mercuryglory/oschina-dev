package org.lion.oschina.general.adapter;

import org.lion.oschina.general.bean.NewsListBean.ResultBean.NewsItemsBean;
import org.lion.oschina.general.holder.BasicHolder;
import org.lion.oschina.general.holder.NewHolder;

/**
 * Created by more on 2016-08-15 18:57:32.
 */
public class NewsAdapter extends BasicAdapter<NewsItemsBean> {
    @Override
    protected BasicHolder createHolder(int position) {
        return new NewHolder();
    }
}
