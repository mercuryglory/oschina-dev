package org.mercury.oschina.bean.base;

import android.support.v4.app.Fragment;

/**
 * Created by mercury on 2016/8/2.
 */
public class FragmentInfo {
    //标题
    public String   title;
    //展示的fragment
    public Fragment fragment;

    public FragmentInfo(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }


}
