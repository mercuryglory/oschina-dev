package org.mercury.oschina.general.adapter;

import org.mercury.oschina.general.bean.NewsListBean.ResultBean.NewsItemsBean;
import org.mercury.oschina.general.holder.BasicHolder;
import org.mercury.oschina.general.holder.NewHolder;

/**
 * Created by more on 2016-08-15 18:57:32.
 */
public class NewsAdapter extends BasicAdapter<NewsItemsBean> {
    @Override
    protected BasicHolder createHolder(int position) {
        return new NewHolder();
    }
}
