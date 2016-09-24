package org.lion.oschina.explorer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.lion.oschina.explorer.bean.ActivitysBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 LY
 * @创建时间 2016/8/15 19:26
 * @描述 ${TODO}
 */
public class ActivityViewPagerAdapter extends FragmentPagerAdapter {
    List<ActivitysBean> mBeanList = new ArrayList<>();
    public ActivityViewPagerAdapter(FragmentManager fm , List<ActivitysBean> list) {
        super(fm);
        this.mBeanList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mBeanList.get(position).mFragment;
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
