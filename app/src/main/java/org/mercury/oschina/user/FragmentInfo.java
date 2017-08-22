package org.mercury.oschina.user;

/**
 * Created by wang.zhonghao on 2017/8/19.
 */

public enum FragmentInfo {

    MY_MESSAGE("消息列表", MsgFragment.class),
    MY_BLOG("用户博客列表", UserBlogFragment.class),
    MY_ACTIVE("我的动态", ActiveFragment.class);

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
