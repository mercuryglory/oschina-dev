package org.mercury.oschina.user.bean;

import org.mercury.oschina.bean.base.BaseBean;

import java.util.List;

/**
 * Created by wang.zhonghao on 2017/8/30.
 */

public class FavoriteResponse extends BaseBean {

    private List<Favorite> favoriteList;

    public List<Favorite> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
    }
}
