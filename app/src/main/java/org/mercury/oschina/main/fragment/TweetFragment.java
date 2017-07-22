package org.mercury.oschina.main.fragment;


import android.support.v4.view.ViewPager;

import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;

import org.mercury.oschina.R;
import org.mercury.oschina.main.BaseTitleFragment;
import org.mercury.oschina.tweet.adapter.TweetPagerAdapter;
import org.mercury.oschina.tweet.bean.FragmentInfo;
import org.mercury.oschina.tweet.fragment.HotTweetFragment;
import org.mercury.oschina.tweet.fragment.MyTweetFragment;
import org.mercury.oschina.tweet.fragment.NewTweetFragment;
import org.mercury.oschina.tweet.util.Utils;
import org.mercury.oschina.tweet.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mercury on 2016-08-14 19:33:46.
 * 动弹 模块
 * 子模块：最新动弹  热门动弹  我的动弹
 */
public class TweetFragment extends BaseTitleFragment {
    @Bind(R.id.pst_title_layout)
    PagerSlidingTabStrip mPstTitleLayout;
    @Bind(R.id.vp_show_layout)
    ViewPager            mVpShowLayout;

    public List<FragmentInfo> mShowItems = new ArrayList<>();

    @Override
    protected  void initData() {
        String[] titles = Utils.getStringArray(R.array.tweets_viewpage_arrays);

        if (mShowItems.size() > 0) {
            mShowItems.clear();
        }

        mShowItems.add(new FragmentInfo(titles[0], new NewTweetFragment()));
        mShowItems.add(new FragmentInfo(titles[1], new HotTweetFragment()));
        mShowItems.add(new FragmentInfo(titles[2], new MyTweetFragment()));

        mVpShowLayout.setPageTransformer(true,new ZoomInTransformer());
        mVpShowLayout.setAdapter(new TweetPagerAdapter(getChildFragmentManager(), mShowItems));
        mPstTitleLayout.setViewPager(mVpShowLayout);
    }

    @Override
    protected int getTitleRes() {
        return R.string.main_tab_name_tweet;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_tweet_list;
    }
}
