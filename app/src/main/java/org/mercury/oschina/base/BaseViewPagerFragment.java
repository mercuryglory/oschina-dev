package org.mercury.oschina.base;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import org.mercury.oschina.R;
import org.mercury.oschina.main.BaseTitleFragment;

import butterknife.Bind;

/**
 * Created by wang.zhonghao on 2017/8/9.
 * 内部包含可滑动的子fragment模块的父fragment
 */

public abstract class BaseViewPagerFragment extends BaseTitleFragment {

    @Bind(R.id.tab_nav)
    public TabLayout tabNav;
    @Bind(R.id.base_viewpager)
    public ViewPager baseViewpager;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_base_viewpager;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        BaseViewPagerAdapter adapter = new BaseViewPagerAdapter(getChildFragmentManager(),
                getPagerInfo());
        baseViewpager.setAdapter(adapter);
        tabNav.setupWithViewPager(baseViewpager);
        baseViewpager.setCurrentItem(0, true);
    }

    protected abstract PagerInfo[] getPagerInfo();

    public static class PagerInfo {
        private String title;
        private Class<?> clazz;
        private Bundle args;

        public PagerInfo(String title, Class<?> clazz, Bundle args) {
            this.title = title;
            this.clazz = clazz;
            this.args = args;
        }
    }

    public class BaseViewPagerAdapter extends FragmentPagerAdapter {

        private PagerInfo[] mPagerInfo;
        private Fragment currentFragment;

        public BaseViewPagerAdapter(FragmentManager fm, PagerInfo[] pagerInfo) {
            super(fm);
            mPagerInfo = pagerInfo;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (object instanceof Fragment) {
                currentFragment = (Fragment) object;
            }
        }

        public Fragment getCurrentFragment() {
            return currentFragment;
        }

        @Override
        public Fragment getItem(int position) {
            PagerInfo pagerInfo = mPagerInfo[position];
            return Fragment.instantiate(getContext(), pagerInfo.clazz.getName(), pagerInfo.args);
        }

        @Override
        public int getCount() {
            return mPagerInfo.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mPagerInfo[position].title;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }
}
