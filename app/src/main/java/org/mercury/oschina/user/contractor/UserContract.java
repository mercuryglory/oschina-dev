package org.mercury.oschina.user.contractor;

import org.mercury.oschina.base.BaseModelView;
import org.mercury.oschina.base.BasePresenter;
import org.mercury.oschina.tweet.bean.User;

/**
 * Created by wang.zhonghao on 2017/9/12.
 */

public interface UserContract {

    interface View extends BaseModelView<Presenter, User> {

        void updateIcon();

    }

    interface Presenter extends BasePresenter {
        void portraitUpdate();
    }

}
