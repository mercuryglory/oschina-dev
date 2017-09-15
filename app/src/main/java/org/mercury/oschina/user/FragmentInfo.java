package org.mercury.oschina.user;

import org.mercury.oschina.explorer.ui.SoftwareFragment;
import org.mercury.oschina.tweet.TweetListFragment;

/**
 * Created by wang.zhonghao on 2017/8/19.
 */

public enum FragmentInfo {

    MY_FRIEND("关注列表",FriendListFragment.class),
    MY_FAVORITE("收藏",UserFavoriteFragment.class),
    MY_TWEET("动弹列表", TweetListFragment.class),
    MY_MESSAGE("我的消息", MyMessageFragment.class),
    MY_BLOG("我的博客", UserBlogFragment.class),
    MY_ACTIVE("我的动态", ActiveFragment.class),

    OPEN_SOFTWARE("开源软件",SoftwareFragment.class);

    private String title;
    private Class clazz;

    FragmentInfo(String title, Class<?> clazz) {
        this.title = title;
        this.clazz = clazz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
