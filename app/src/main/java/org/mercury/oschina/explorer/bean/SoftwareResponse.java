package org.mercury.oschina.explorer.bean;

import java.util.List;

/**
 * Created by Mercury on 2017/9/18.
 */

public class SoftwareResponse {

    private int count;
    private List<Project> projectlist;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Project> getProjectlist() {
        return projectlist;
    }

    public void setProjectlist(List<Project> projectlist) {
        this.projectlist = projectlist;
    }

}
