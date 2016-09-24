package org.lion.oschina.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.ScaleInOutTransformer;
import com.orhanobut.logger.Logger;

import org.lion.oschina.base.AppContext;
import org.lion.oschina.general.adapter.GeneralPagerAdapter;
import org.lion.oschina.tweet.widget.PagerSlidingTabStrip;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by more on 2016-08-14 19:33:46.
 */
public class GeneralViewPagerFragment extends Fragment {
    private static final String TAG = "====_GeneralViewPagerFragment";
    @Bind(R.id.slidingTab)
    PagerSlidingTabStrip mSlidingTab;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    private View mView;
    private GeneralPagerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Logger.t(TAG).i("onCreateView");

        if (mView == null) {
            mView = View.inflate(AppContext.context, R.layout.general_viewpage_fragment, null);
            ButterKnife.bind(this, mView);
            mAdapter = new GeneralPagerAdapter(getActivity().getSupportFragmentManager());
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
