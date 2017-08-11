package org.mercury.oschina.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang.zhonghao on 2017/8/10.
 * RecyclerView
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

    public List<T> mData;
    public LayoutInflater mInflater;
    public Context mContext;

    public BaseRecyclerAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindDefaultViewHolder(holder, getData().get(position), position);
    }

    protected abstract void onBindDefaultViewHolder(RecyclerView.ViewHolder h, T data, int
            position);

    public final List<T> getData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<T> items) {
        if (items != null) {
            mData.clear();
            mData.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void addAll(List<T> items) {
        if (items != null) {
            int lastIndex = mData.size();
            mData.addAll(items);
            notifyItemRangeInserted(lastIndex, items.size());
        }
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    //就是将新数据追加到尾部
    public void addItem(T item) {
        if (item != null) {
            mData.add(item);
            notifyItemChanged(mData.size());
        }
    }


    public void addItem(int position, T item) {
        if (item != null) {
            mData.add(position, item);
            notifyItemInserted(position);
        }
    }

    public void replaceItem(int position, T item) {
        if (item != null) {
            mData.set(position, item);
            notifyItemChanged(position);
        }
    }

    public void updateItem(int position) {
        if (getItemCount() > position) {
            notifyItemChanged(position);
        }
    }


    public void removeItem(T item) {
        if (mData.contains(item)) {
            int position = mData.indexOf(item);
            mData.remove(item);
            notifyItemRemoved(position);
        }
    }

    public final void removeItem(int position) {
        if (getItemCount() > position) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public T getItem(int position) {
        if(position<0||position>=mData.size())return null;
        return mData.get(position);
    }

}
