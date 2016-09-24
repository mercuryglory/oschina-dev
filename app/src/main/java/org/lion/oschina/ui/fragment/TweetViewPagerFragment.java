package org.lion.oschina.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;

import org.lion.oschina.R;
import org.lion.oschina.tweet.adapter.TweetPagerAdapter;
import org.lion.oschina.tweet.bean.FragmentInfo;
import org.lion.oschina.tweet.fragment.HotTweetFragment;
import org.lion.oschina.tweet.fragment.MyTweetFragment;
import org.lion.oschina.tweet.fragment.NewTweetFragment;
import org.lion.oschina.tweet.util.Utils;
import org.lion.oschina.tweet.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by more on 2016-08-14 19:33:46.
 */
public class TweetViewPagerFragment extends Fragment {
    @Bind(R.id.pst_title_layout)
    PagerSlidingTabStrip mPstTitleLayout;
    @Bind(R.id.vp_show_layout)
    ViewPager            mVpShowLayout;

    public List<FragmentInfo> mShowItems = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        System.out.println("onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet_list, null);
        ButterKnife.bind(this, view);
//        System.out.println("onCreateView");
        mVpShowLayout.setPageTransformer(true,new ZoomInTransformer());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        String[] titles = Utils.getStringArray(R.array.tweets_viewpage_arrays);

        if (mShowItems.size() > 0) {
            mShowItems.clear();
        }

        mShowItems.add(new FragmentInfo(titles[0], new NewTweetFragment()));
        mShowItems.add(new FragmentInfo(titles[1], new HotTweetFragment()));
        mShowItems.add(new FragmentInfo(titles[2], new MyTweetFragment()));

        mVpShowLayout.setAdapter(new TweetPagerAdapter(getChildFragmentManager(), mShowItems));
        mPstTitleLayout.setViewPager(mVpShowLayout);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
//        mShowItems.clear();
    }
}
