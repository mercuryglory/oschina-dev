package org.mercury.oschina.tweet.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.mercury.oschina.tweet.bean.FragmentInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名:      TweetPagerAdapter
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      ${TODO}
 */
public class TweetPagerAdapter extends FragmentStatePagerAdapter {

    List<FragmentInfo> mShowItems = new ArrayList<>();

    public TweetPagerAdapter(FragmentManager fm, List<FragmentInfo> showItems) {
        super(fm);
        mShowItems = showItems;
    }


    @Override
    public Fragment getItem(int position) {
        return mShowItems.get(position).fragment;
    }

    @Override
    public int getCount() {
        return mShowItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mShowItems.get(position).title;
    }
}
