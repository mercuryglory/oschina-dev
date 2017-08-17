package org.mercury.oschina.synthesis.ui;

import android.os.Bundle;

import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseViewPagerFragment;

/**
 * Created by mercury on 2016-08-14 19:33:46.
 * 综合模块
 * 子模块：新闻资讯  推荐博客  每日博客
 */
public class SynthesisFragment extends BaseViewPagerFragment {

    @Override
    protected  void initData() {
        //选择了一个fragment切换时的动画效果
        baseViewpager.setPageTransformer(true, new ZoomInTransformer());
    }

    @Override
    protected int getTitleRes() {
        return R.string.main_tab_name_news;
    }

    @Override
    protected PagerInfo[] getPagerInfo() {
        return new PagerInfo[]{
                new PagerInfo("新闻资讯", NewsFragment.class, null),
                new PagerInfo("最新博客", BlogFragment.class, getBundle(BlogFragment.CATALOG_NEW)),
                new PagerInfo("热门博客", BlogFragment.class, getBundle(BlogFragment.CATALOG_HOT)),
                new PagerInfo("技术问答", PostFragment.class, getBundle(PostFragment.CATALOG_ANSWER))
        };
    }

    public Bundle getBundle(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(BlogFragment.REQUEST_CATALOG, type);
        return bundle;
    }

}
