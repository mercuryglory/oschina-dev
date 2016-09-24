package org.lion.oschina.tweet.bean;


import android.support.v4.app.Fragment;

/**
 * 类名:      FragmentInfo
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      ${TODO}
 */
public class FragmentInfo {
    public String   title;
    public Fragment fragment;

    public FragmentInfo(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }
}
