package org.mercury.oschina.explorer.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * @创建者 LY
 * @创建时间 2016/8/15 13:03
 * @描述 ${TODO} 实体基类 实现序列化
 */

@SuppressWarnings("serial")
public abstract class Base implements Serializable {
    @XStreamAlias("notice")
    protected Notice notice;

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
