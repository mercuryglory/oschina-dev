package org.mercury.oschina.user.contractor;

import org.mercury.oschina.base.BaseModelView;
import org.mercury.oschina.base.BasePresenter;
import org.mercury.oschina.tweet.bean.User;

/**
 * Created by wang.zhonghao on 2017/9/12.
 */

public interface OtherUserContract {

    interface View extends BaseModelView<Presenter, User> {

        void updateIcon(int relation);

    }

    interface Presenter extends BasePresenter<View> {

        void refreshing(long userId);

        void addRelation(long userId, int relation);
    }

}
