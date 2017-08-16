package org.mercury.oschina.main.fragment;


import android.os.Bundle;

import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;

import org.mercury.oschina.Constant;
import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseViewPagerFragment;
import org.mercury.oschina.tweet.TweetListFragment;
import org.mercury.oschina.utils.SpUtils;

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
        int userId = Integer.parseInt(SpUtils.get(mContext, Constant.USER_ID, "").toString());
        return new PagerInfo[]{
                new PagerInfo("最新动弹", TweetListFragment.class, getBundle(TweetListFragment
                        .CATALOG_NEW)),
                new PagerInfo("热门动弹", TweetListFragment.class, getBundle(TweetListFragment
                        .CATALOG_HOT)),
                new PagerInfo("我的动弹", TweetListFragment.class, getBundle(userId))
        };
    }

    private Bundle getBundle(int catalog) {
        Bundle bundle = new Bundle();
        bundle.putInt(TweetListFragment.REQUEST_CATALOG, catalog);
        return bundle;
    }
}
