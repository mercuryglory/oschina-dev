package org.mercury.oschina.explorer.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.mercury.oschina.AppContext;
import org.mercury.oschina.R;
import org.mercury.oschina.explorer.adapter.BasicHolder;
import org.mercury.oschina.explorer.bean.Event;
import org.mercury.oschina.explorer.util.Utils;

import butterknife.BindView;

/**
 * @创建者 Mercury
 * @创建时间 2016/8/16 18:46
 * @描述 ${TODO}
 */
public class MyActivityHolder extends BasicHolder<Event> {
    @BindView(R.id.iv_my_icon)
    ImageView mIvMyIcon;
    @BindView(R.id.tv_my_title)
    TextView mTvMyTitle;
    @BindView(R.id.tv_my_time)
    TextView mTvMyTime;
    @BindView(R.id.tv_my_content)
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
