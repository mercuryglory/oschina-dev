package org.mercury.oschina.user.bean;

import org.mercury.oschina.synthesis.bean.Blog;

import java.util.List;

/**
 * Created by wang.zhonghao on 2017/8/19.
 */

public class ProjectResponse {

    private int count;
    private List<Blog> projectlist;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Blog> getProjectlist() {
        return projectlist;
    }

    public void setProjectlist(List<Blog> projectlist) {
        this.projectlist = projectlist;
    }
}
