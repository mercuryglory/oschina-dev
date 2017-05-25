package org.mercury.oschina.explorer.adapter;


import org.mercury.oschina.explorer.bean.Event;
import org.mercury.oschina.explorer.holder.MyActivityHolder;

import java.util.List;

/**
 * @创建者 LY
 * @创建时间 2016/8/16 18:45
 * @描述 ${TODO}
 */
public class MyActivityAdpter extends Basic1Adapter<Event> {
    public MyActivityAdpter(List<Event> list) {
        super(list);
    }

    @Override
    public Basic1Holder createHolder(int position) {
        return new MyActivityHolder();
    }
}
