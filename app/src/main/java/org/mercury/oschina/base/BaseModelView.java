package org.mercury.oschina.base;

/**
 * Created by wang.zhonghao on 2017/9/12.
 */

public interface BaseModelView<Presenter extends BasePresenter, Model> extends BaseView<Presenter>{

    void refreshSuccess(Model model);

    void showError(String msg);


}
