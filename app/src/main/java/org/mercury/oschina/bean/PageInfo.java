package org.mercury.oschina.bean;

import android.os.Bundle;

/**
 * Created by wang.zhonghao on 2017/9/5.
 */

public class PageInfo {

    public String   title;
    public Class<?> clazz;
    public Bundle   args;

    public PageInfo(String title, Class<?> clazz, Bundle args) {
        this.title = title;
        this.clazz = clazz;
        this.args = args;
    }


}
