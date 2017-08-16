package org.mercury.oschina.synthesis.ui.fragment;

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
                new PagerInfo("推荐博客", BlogNewestFragment.class, null),
                new PagerInfo("每日博客", BlogHeatFragment.class, null)
        };
    }


}
