package org.mercury.oschina.explorer.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 Mercury
 * @创建时间 2016/8/14 22:00
 * @描述 ${TODO}
 */
public abstract class BasicAdapter<T> extends BaseAdapter {
    List<T> mList = new ArrayList<>();


    public BasicAdapter(List<T> list) {
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
        BasicHolder holder;
        if (convertView == null) {
            holder = createHolder(position);
        } else {
            holder = (BasicHolder) convertView.getTag();
        }
        holder.bindView(getItem(position));

        return holder.getView();
    }

    public abstract BasicHolder createHolder(int position);
}
