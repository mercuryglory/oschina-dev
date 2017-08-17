package org.mercury.oschina.synthesis.bean;

import org.mercury.oschina.bean.base.BaseBean;

import java.util.List;

/**
 * Created by wang.zhonghao on 2017/8/17.
 */

public class PostResponse extends BaseBean {

    private List<Post> post_list;

    public List<Post> getPost_list() {
        return post_list;
    }

    public void setPost_list(List<Post> post_list) {
        this.post_list = post_list;
    }
}
