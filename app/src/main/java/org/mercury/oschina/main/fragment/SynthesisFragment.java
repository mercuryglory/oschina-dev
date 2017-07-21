package org.mercury.oschina.main.fragment;

import android.support.v4.view.ViewPager;

import com.ToxicBakery.viewpager.transforms.ScaleInOutTransformer;
import com.orhanobut.logger.Logger;

import org.mercury.oschina.R;
import org.mercury.oschina.main.BaseTitleFragment;
import org.mercury.oschina.synthesis.adapter.SynthesisPagerAdapter;
import org.mercury.oschina.tweet.widget.PagerSlidingTabStrip;

import butterknife.Bind;

/**
 * Created by mercury on 2016-08-14 19:33:46.
 * 综合模块
 * 子模块：资讯  热点  博客  推荐
 */
public class SynthesisFragment extends BaseTitleFragment {
    private static final String TAG = "SynthesisFragment";

    @Bind(R.id.slidingTab)
    PagerSlidingTabStrip mSlidingTab;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    private SynthesisPagerAdapter mAdapter;

    @Override
    protected void initData() {
        Logger.t(TAG).i("onCreateView");
        mAdapter = new SynthesisPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true,new ScaleInOutTransformer());
        mSlidingTab.setViewPager(mViewPager);
    }


    @Override
    protected int getTitleRes() {
        return R.string.main_tab_name_news;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_synthesis;
    }


}
