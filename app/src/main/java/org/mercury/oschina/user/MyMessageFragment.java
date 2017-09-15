package org.mercury.oschina.user;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseViewPagerFragment;
import org.mercury.oschina.bean.PageInfo;

import butterknife.Bind;

/**
 * Created by wang.zhonghao on 2017/8/23.
 */

public class MyMessageFragment extends BaseViewPagerFragment {

    @Bind(R.id.tab_nav)
    TabLayout tabNav;
    @Bind(R.id.base_viewpager)
    ViewPager baseViewpager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_viewpager;
    }

    @Override
    public PageInfo[] initPageInfos() {
        PageInfo[] infos = new PageInfo[]{
                new PageInfo("@我", AtMeFragment.class, null),
                new PageInfo("评论", CommentFragment.class, null),
                new PageInfo("私信", PrivateMsgFragment.class, null)
        };
        return infos;

    }

}
