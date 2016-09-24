package org.lion.oschina.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.lion.oschina.bean.base.FragmentInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy_heima on 2016/8/2.
 */
public class MainShowAdpter extends FragmentStatePagerAdapter {
    private List<FragmentInfo> mShowItems = new ArrayList<>();
    public MainShowAdpter(FragmentManager fm, List<FragmentInfo> showItems ) {
        super(fm);
        this.mShowItems = showItems;
    }

    @Override
    public Fragment getItem(int position) {
        return mShowItems.get(position).fragment;
    }

    @Override
    public int getCount() {
        return mShowItems.size();
    }

    //指示页签使用
    @Override
    public CharSequence getPageTitle(int position) {
        return mShowItems.get(position).title;
    }
}
