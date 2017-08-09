package org.mercury.oschina.main.fragment;


import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseViewPagerFragment;
import org.mercury.oschina.tweet.fragment.HotTweetFragment;
import org.mercury.oschina.tweet.fragment.MyTweetFragment;
import org.mercury.oschina.tweet.fragment.NewTweetFragment;

/**
 * Created by mercury on 2016-08-14 19:33:46.
 * 动弹 模块
 * 子模块：最新动弹  热门动弹  我的动弹
 */
public class TweetFragment extends BaseViewPagerFragment {

    @Override
    protected  void initData() {
        //选择了一个fragment切换时的动画效果
        baseViewpager.setPageTransformer(true, new ZoomInTransformer());
    }

    @Override
    protected int getTitleRes() {
        return R.string.main_tab_name_tweet;
    }

    @Override
    protected PagerInfo[] getPagerInfo() {
        return new PagerInfo[]{
                new PagerInfo("最新动弹", NewTweetFragment.class, null),
                new PagerInfo("热门动弹", HotTweetFragment.class, null),
                new PagerInfo("我的动弹", MyTweetFragment.class, null)
        };
    }
}
