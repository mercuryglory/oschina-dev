package org.mercury.oschina.synthesis.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ToxicBakery.viewpager.transforms.TabletTransformer;

import org.mercury.oschina.R;
import org.mercury.oschina.bean.PageInfo;
import org.mercury.oschina.base.BaseTitleFragment;

import butterknife.BindView;

/**
 * Created by mercury on 2016-08-14 19:33:46.
 * 综合模块
 * 子模块：新闻资讯  推荐博客  每日博客
 */
public class SynthesisFragment extends BaseTitleFragment {

    @BindView(R.id.tab_nav)
    TabLayout    tabNav;
    @BindView(R.id.iv_arrow)
    ImageView    ivArrow;
    @BindView(R.id.viewpager)
    ViewPager    viewpager;
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    private PageInfo[] mPageInfos;

    @Override
    protected void initData() {
        //选择了一个fragment切换时的动画效果
        viewpager.setPageTransformer(true, new TabletTransformer());
        mPageInfos = getPageInfos();

        viewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getItemPosition(Object object) {
                return FragmentPagerAdapter.POSITION_NONE;
            }

            @Override
            public Fragment getItem(int position) {
                PageInfo pageInfo = mPageInfos[position];
                return Fragment.instantiate(getContext(), pageInfo.clazz.getName(), pageInfo.args);
            }

            @Override
            public int getCount() {
                return mPageInfos.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mPageInfos[position].title;
            }
        });
        tabNav.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(0, true);
    }

    @Override
    protected int getTitleRes() {
        return R.string.main_tab_name_news;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_synthesis;
    }

    protected PageInfo[] getPageInfos() {
        return new PageInfo[]{
                new PageInfo("新闻资讯", NewsFragment.class, null),
                new PageInfo("最新博客", BlogFragment.class, getBundle(BlogFragment
                        .CATALOG_NEW)),
                new PageInfo("热门博客", BlogFragment.class, getBundle(BlogFragment
                        .CATALOG_HOT)),
                new PageInfo("技术问答", PostFragment.class, getBundle(PostFragment
                        .CATALOG_ANSWER))
        };
    }

    public Bundle getBundle(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(BlogFragment.REQUEST_CATALOG, type);
        return bundle;
    }


}
