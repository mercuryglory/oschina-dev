package org.mercury.oschina.base;

/**
 * Created by wang.zhonghao on 2017/9/12.
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();

}
