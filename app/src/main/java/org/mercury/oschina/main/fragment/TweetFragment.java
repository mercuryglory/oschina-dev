package org.mercury.oschina.main.fragment;


import android.os.Bundle;

import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseViewPagerFragment;
import org.mercury.oschina.bean.PageInfo;
import org.mercury.oschina.tweet.TweetListFragment;
import org.mercury.oschina.utils.AccessTokenHelper;

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
    protected PageInfo[] getPagerInfo() {
        int userId = AccessTokenHelper.getUserId();
        return new PageInfo[]{
                new PageInfo("最新动弹", TweetListFragment.class, getBundle(TweetListFragment
                        .CATALOG_NEW)),
                new PageInfo("热门动弹", TweetListFragment.class, getBundle(TweetListFragment
                        .CATALOG_HOT)),
                new PageInfo("我的动弹", TweetListFragment.class, getBundle(userId))
        };
    }

    private Bundle getBundle(int catalog) {
        Bundle bundle = new Bundle();
        bundle.putInt(TweetListFragment.REQUEST_CATALOG, catalog);
        return bundle;
    }
}
