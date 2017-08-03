package org.mercury.oschina.bean.base;

import java.io.Serializable;

/**
 * Created by Mercury on 2017/8/3.
 */

public class Notice implements Serializable{

    /**
     * replyCount : 0
     * msgCount : 0
     * fansCount : 0
     * referCount : 0
     */

    private int replyCount;
    private int msgCount;
    private int fansCount;
    private int referCount;

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getReferCount() {
        return referCount;
    }

    public void setReferCount(int referCount) {
        this.referCount = referCount;
    }
}
