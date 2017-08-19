package org.mercury.oschina.user.bean;

import org.mercury.oschina.bean.base.BaseBean;

import java.util.List;

/**
 * Created by wang.zhonghao on 2017/8/19.
 */

public class MessageResponse extends BaseBean {

    private List<Message> messageList;

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
