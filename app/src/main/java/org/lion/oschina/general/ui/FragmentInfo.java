package org.lion.oschina.general.ui;

import android.support.v4.app.Fragment;

/**
 * Created by more on 2016-08-02 19:50:52.
 */
public class FragmentInfo {
    public String title;
    public Fragment fragment;

    public FragmentInfo(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }
}
