package org.mercury.oschina.tweet.bean;


import android.support.v4.app.Fragment;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      初始化Fragment
 */
public class FragmentInfo {
    private String   title;
    private Fragment fragment;

    public FragmentInfo(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
