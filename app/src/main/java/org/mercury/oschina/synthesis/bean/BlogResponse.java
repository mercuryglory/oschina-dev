package org.mercury.oschina.synthesis.bean;

import org.mercury.oschina.bean.base.BaseBean;

import java.util.List;

/**
 * Created by more on 2016-08-15 21:48:46.
 */
public class BlogResponse extends BaseBean{

    private List<Blog> bloglist;

    public List<Blog> getBloglist() {
        return bloglist;
    }

    public void setBloglist(List<Blog> bloglist) {
        this.bloglist = bloglist;
    }
}
