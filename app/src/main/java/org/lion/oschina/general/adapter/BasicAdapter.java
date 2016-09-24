package org.lion.oschina.general.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.lion.oschina.general.holder.BasicHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by more on 2016-08-02 22:19:46.
 */
public abstract class BasicAdapter<T> extends BaseAdapter {
    private List<T> mDatas = new ArrayList<>();

    protected Map<T,TextView> mHolderMap = new HashMap<>();
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {

        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BasicHolder holder = null;
        if (convertView == null) {
            holder =  createHolder(position);
        } else {
            holder = (BasicHolder) convertView.getTag();
        }
        T item = getItem(position);
        mHolderMap.put(item,holder.getTitleView());
        holder.bindView(item);
        return holder.getView();
    }


    public TextView getTitleView(T t){
        return mHolderMap.get(t);
    }


    public void addItem(T t){
        mDatas.add(t);
        notifyDataSetChanged();
    }

    public void updateItem(Collection<T> ts){
        mDatas.clear();
        mDatas.addAll(ts);
        notifyDataSetChanged();
    }

    public void appendItem(Collection<T> ts){
        mDatas.addAll(ts);
        notifyDataSetChanged();
    }




    protected abstract BasicHolder createHolder(int position);

}
