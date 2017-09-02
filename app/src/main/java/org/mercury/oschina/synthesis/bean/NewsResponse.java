package org.mercury.oschina.synthesis.bean;

import org.mercury.oschina.bean.base.BaseBean;

import java.util.List;

/**
 * Created by more on 2016-08-15 18:29:04.
 */
public class NewsResponse extends BaseBean {

    private List<News> newslist;

    public List<News> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<News> newslist) {
        this.newslist = newslist;
    }
}
