package org.mercury.oschina.user.bean;

import org.mercury.oschina.bean.base.BaseBean;

import java.util.List;

/**
 * Created by wang.zhonghao on 2017/8/30.
 */

public class UserResponse extends BaseBean {

    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
