package org.lion.oschina.holder;

import android.view.View;
import android.widget.TextView;

import org.lion.oschina.bean.Favorite;
import org.lion.oschina.global.OsChinaApp;

import butterknife.Bind;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/16 16:55
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class CollectViewHolder extends BasicHolder<Favorite> {
    @Bind(R.id.tv_collect)
    TextView mTvCollect;

    @Override
    protected View createView() {

        return View.inflate(OsChinaApp.context, R.layout.items_collect, null);
    }

    @Override
    public void bindView(Favorite appInfo) {

        mTvCollect.setText(appInfo.getTitle());
    }
}
