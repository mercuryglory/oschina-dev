package org.mercury.oschina.adapter;

import android.app.Activity;

import org.mercury.oschina.bean.Active;
import org.mercury.oschina.holder.BasicHolder;
import org.mercury.oschina.holder.MeViewHolder;

import java.util.List;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/18 14:26
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class MeAdapter extends BasicAdapter <Active> {


    private Activity mActivity;

    public void setContext(Activity activity) {
        mActivity = activity;

    }

    public MeAdapter(List<Active> showItems) {
        super(showItems);

    }
    public MeAdapter(List<Active> showItems,Activity activity) {
        super(showItems);


        mActivity = activity;
    }

    @Override
    public BasicHolder createViewHolder(int postion) {
        return new MeViewHolder(mActivity);
    }
}
