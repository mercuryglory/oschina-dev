package org.mercury.oschina.synthesis.adapter;

import org.mercury.oschina.synthesis.bean.NewsListBean.ResultBean.NewsItemsBean;
import org.mercury.oschina.synthesis.holder.BasicHolder;
import org.mercury.oschina.synthesis.holder.NewHolder;

/**
 * Created by more on 2016-08-15 18:57:32.
 */
public class NewsAdapter extends BasicAdapter<NewsItemsBean> {
    @Override
    protected BasicHolder createHolder(int position) {
        return new NewHolder();
    }
}
