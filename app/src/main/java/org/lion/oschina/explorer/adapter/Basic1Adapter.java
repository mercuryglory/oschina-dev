package org.lion.oschina.explorer.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 LY
 * @创建时间 2016/8/14 22:00
 * @描述 ${TODO}
 */
public abstract class Basic1Adapter<T> extends BaseAdapter {
    List<T> mList = new ArrayList<>();


    public Basic1Adapter(List<T> list) {
        mList = (List<T>) list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Basic1Holder holder;
        if (convertView == null) {
            holder = createHolder(position);
        } else {
            holder = (Basic1Holder) convertView.getTag();
        }
        holder.bindView(getItem(position));

        return holder.getView();
    }

    public abstract Basic1Holder createHolder(int position);
}
