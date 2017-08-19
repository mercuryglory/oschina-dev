package org.mercury.oschina.explorer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.mercury.oschina.bean.base.FragmentInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 Mercury
 * @创建时间 2016/8/15 19:26
 * @描述 ${TODO}
 */
public class ActivityViewPagerAdapter extends FragmentPagerAdapter {
    List<FragmentInfo> mBeanList = new ArrayList<>();

    public ActivityViewPagerAdapter(FragmentManager fm, List<FragmentInfo> list) {
        super(fm);
        this.mBeanList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mBeanList.get(position).fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mBeanList.get(position).title;
    }

    @Override
    public int getCount() {
        return mBeanList.size();
    }
}
