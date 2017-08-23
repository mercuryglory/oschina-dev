package org.mercury.oschina.user;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by wang.zhonghao on 2017/8/23.
 */

public class MyMessageFragment extends BaseFragment {

    @Bind(R.id.tab_nav)
    TabLayout tabNav;
    @Bind(R.id.base_viewpager)
    ViewPager baseViewpager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_viewpager;
    }

    @Override
    protected void initData() {
        PageInfo[] infos = new PageInfo[]{
                new PageInfo("@我", AtMeFragment.class),
                new PageInfo("评论", CommentFragment.class),
                new PageInfo("私信", PrivateMsgFragment.class)
        };
        baseViewpager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), infos));
        tabNav.setupWithViewPager(baseViewpager);
        baseViewpager.setCurrentItem(0,true);

    }

    private static class PageInfo {
        private String title;
        private Class<?> clazz;

        public PageInfo(String title, Class<?> clazz) {
            this.title = title;
            this.clazz = clazz;
        }
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private PageInfo[] mPageInfos;
        private Fragment currentFragment;

        public ViewPagerAdapter(FragmentManager fm,PageInfo[] infos) {
            super(fm);
            mPageInfos = infos;
        }

        @Override
        public Fragment getItem(int position) {
            PageInfo info = mPageInfos[position];
            return Fragment.instantiate(getContext(), info.clazz.getName(), null);
        }

        @Override
        public int getCount() {
            return mPageInfos.length;
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
        public CharSequence getPageTitle(int position) {
            return mPageInfos[position].title;
        }
    }
}
