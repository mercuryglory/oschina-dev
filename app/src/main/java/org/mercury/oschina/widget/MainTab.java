package org.mercury.oschina.widget;

import org.mercury.oschina.R;
import org.mercury.oschina.main.fragment.ExploreFragment;
import org.mercury.oschina.main.fragment.SynthesisFragment;
import org.mercury.oschina.main.fragment.TweetFragment;
import org.mercury.oschina.main.fragment.UserFragment;

/**
 * Created by wang.zhonghao on 2017/5/25
 * descript:  首页的底部导航栏信息 枚举类
 */
public enum MainTab {

	/*
    NEWS(0, R.string.main_tab_name_news, R.drawable.tab_icon_new,
			NewsViewPagerFragment.class),
			*/

    NEWS(0, R.string.main_tab_name_news, R.drawable.tab_icon_new,
            SynthesisFragment.class),

    TWEET(1, R.string.main_tab_name_tweet, R.drawable.tab_icon_tweet,
            TweetFragment.class),

    QUICK(2, R.string.main_tab_add, R.color.transparent,
            null),

    EXPLORE(3, R.string.main_tab_name_explore, R.drawable.tab_icon_explore,
            ExploreFragment.class),

    ME(4, R.string.main_tab_name_my, R.drawable.tab_icon_me,
            UserFragment.class);

    private int      idx;
    private int      resName;
    private int      resIcon;
    private Class<?> clz;

    MainTab(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
