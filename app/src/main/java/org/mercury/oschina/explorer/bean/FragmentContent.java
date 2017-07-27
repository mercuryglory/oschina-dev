package org.mercury.oschina.explorer.bean;

import android.support.v4.app.Fragment;

/**
 * @创建者 Mercury
 * @创建时间 2016/8/15 19:18
 * @描述 ${TODO}
 */
public class FragmentContent {

    public String   title;
    public Fragment mFragment;

    public FragmentContent(String title, Fragment fragment) {
        this.title = title;
        mFragment = fragment;
    }
}
