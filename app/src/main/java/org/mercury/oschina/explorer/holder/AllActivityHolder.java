package org.mercury.oschina.explorer.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.AppContext;
import org.mercury.oschina.explorer.adapter.BasicHolder;
import org.mercury.oschina.explorer.bean.Event;
import org.mercury.oschina.explorer.util.SPUtils;
import org.mercury.oschina.explorer.util.Utils;

import butterknife.Bind;

/**
 * @创建者 Mercury
 * @创建时间 2016/8/16 17:21
 * @描述 ${TODO}
 */
public class AllActivityHolder extends BasicHolder<Event> {
    @Bind(R.id.ic_all_icon)
    ImageView mIcAllIcon;
    @Bind(R.id.tv_all_title)
    TextView mTvAllTitle;
    @Bind(R.id.tv_all_time)
    TextView mTvAllTime;
    @Bind(R.id.tv_all_content)
    TextView mTvAllContent;

    @Override
    public View createView() {
        return View.inflate(AppContext.context(), R.layout.all_activity_item, null);
    }

    @Override
    public void bindView(Event appInfo) {
        boolean aFalse = SPUtils.getBoolean_false(AppContext.getContext(), appInfo.getId()+"");
        if(aFalse){
            mTvAllTitle.setTextColor(Color.RED);
        }else{
            mTvAllTitle.setTextColor(Color.BLACK);
        }
        mTvAllTitle.setText(appInfo.getTitle());
        Utils.setImage(appInfo.getCover(), mIcAllIcon);
        mTvAllTime.setText(appInfo.getStartTime());
        mTvAllContent.setText(appInfo.getSpot());
    }

}
