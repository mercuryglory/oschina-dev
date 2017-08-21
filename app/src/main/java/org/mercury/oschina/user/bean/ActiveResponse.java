package org.mercury.oschina.user.bean;

import org.mercury.oschina.bean.base.BaseBean;

import java.util.List;

/**
 * Created by wang.zhonghao on 2017/8/21.
 */

public class ActiveResponse extends BaseBean {

    private List<Active> activelist;

    public List<Active> getActivelist() {
        return activelist;
    }

    public void setActivelist(List<Active> activelist) {
        this.activelist = activelist;
    }
}
