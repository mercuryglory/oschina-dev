package org.mercury.oschina.synthesis.bean;

import org.mercury.oschina.bean.base.BaseBean;

import java.util.List;

/**
 * Created by more on 2016-08-15 18:29:04.
 */
public class NewsResponse extends BaseBean {

    private List<Blog> newslist;

    public List<Blog> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<Blog> newslist) {
        this.newslist = newslist;
    }
}
