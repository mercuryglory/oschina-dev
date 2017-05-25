package org.mercury.oschina.explorer.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.explorer.adapter.Basic1Holder;
import org.mercury.oschina.explorer.bean.Event;
import org.mercury.oschina.explorer.util.Utils;

import butterknife.Bind;

/**
 * @创建者 LY
 * @创建时间 2016/8/16 18:46
 * @描述 ${TODO}
 */
public class MyActivityHolder extends Basic1Holder<Event> {
    @Bind(R.id.iv_my_icon)
    ImageView mIvMyIcon;
    @Bind(R.id.tv_my_title)
    TextView mTvMyTitle;
    @Bind(R.id.tv_my_time)
    TextView mTvMyTime;
    @Bind(R.id.tv_my_content)
    TextView mTvMyContent;

    @Override
    public View createView() {
        return View.inflate(AppContext.context(), R.layout.my_activity_item, null);
    }

    @Override
    public void bindView(Event appInfo) {
        Utils.setImage(appInfo.getCover(),mIvMyIcon);
        mTvMyContent.setText(appInfo.getSpot());
        mTvMyTitle.setText(appInfo.getTitle());
        mTvMyTime.setText(appInfo.getStartTime());
    }
}
