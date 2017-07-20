package org.mercury.oschina.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.ScaleInOutTransformer;
import com.orhanobut.logger.Logger;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.synthesis.adapter.SynthesisPagerAdapter;
import org.mercury.oschina.tweet.widget.PagerSlidingTabStrip;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mercury on 2016-08-14 19:33:46.
 * 综合模块
 * 子模块：资讯  热点  博客  推荐
 */
public class SynthesisFragment extends Fragment {
    private static final String TAG = "SynthesisFragment";

    @Bind(R.id.slidingTab)
    PagerSlidingTabStrip mSlidingTab;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    private View                  mView;
    private SynthesisPagerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Logger.t(TAG).i("onCreateView");

        if (mView == null) {
            mView = View.inflate(AppContext.context, R.layout.fragment_synthesis, null);
            ButterKnife.bind(this, mView);
            mAdapter = new SynthesisPagerAdapter(getActivity().getSupportFragmentManager());
            mViewPager.setAdapter(mAdapter);
            mViewPager.setPageTransformer(true,new ScaleInOutTransformer());
            mSlidingTab.setViewPager(mViewPager);
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.t(TAG).i("onViewCreated");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.t(TAG).i("onDestroyView");
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.t(TAG).i("onResume");
    }
}
