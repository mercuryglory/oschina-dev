package org.mercury.oschina.base;

import org.mercury.oschina.R;

/**
 * Created by wang.zhonghao on 2017/9/12.
 */

public abstract class BasePresenterActivity<Presenter extends BasePresenter, Model> extends
        BaseActivity implements BaseModelView<Presenter,Model> {

    protected Presenter mPresenter;

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void errorNetWork(Throwable throwable) {
        showToast(getResources().getString(R.string.state_network_error));
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

}
