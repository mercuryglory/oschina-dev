package org.mercury.oschina.explorer.contract;

import org.mercury.oschina.base.BaseModelView;
import org.mercury.oschina.base.BasePresenter;
import org.mercury.oschina.explorer.bean.SoftwareDetail;

/**
 * Created by Mercury on 2017/9/26.
 */

public interface SoftwareDetailContract {

    interface View extends BaseModelView<Presenter, SoftwareDetail> {

    }

    interface Presenter extends BasePresenter<View> {
        void refreshData(String ident);
    }

}
