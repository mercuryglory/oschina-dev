package org.mercury.oschina.base;

/**
 * Created by wang.zhonghao on 2017/9/12.
 */

public interface BaseView<Presenter> {

    void setPresenter(Presenter presenter);

    void errorNetWork(Throwable throwable);


}
