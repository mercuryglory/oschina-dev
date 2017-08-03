package org.mercury.oschina.bean.base;


import java.io.Serializable;

/**
 * Created by Mercury on 2017/8/3.
 */

public class BaseBean implements Serializable {

    private Notice notice;

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
