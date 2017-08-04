package org.mercury.oschina.tweet.adapter;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;

import org.mercury.oschina.tweet.holder.BasicHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/15
 * 描述:      ${TODO}
 */
public abstract class BasicAdapter<T> extends BaseAdapter {


    private List<T> mShowItems = new ArrayList<>();

    public void updateDatas(List<T> list) {
        this.mShowItems = list;
        notifyDataSetChanged();
    }

    public void addDatas(List<T> list) {
        if (mShowItems != null) {
            mShowItems.addAll(list);
        }
        notifyDataSetChanged();
    }

    public List<T> getShowItems() {
        return mShowItems;
    }

    public void setShowItems(List<T> showItems) {
        mShowItems = showItems;
    }

    @Override
    public int getCount() {
        return mShowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mShowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        BasicHolder vh;
        if (convertView == null) {
            vh = createViewHolder();
        } else {
            vh = (BasicHolder) convertView.getTag();
        }

        vh.bindView(parent, mShowItems.get(position));
        View view = vh.getView();

        view.setScaleX(.6f);
        view.setScaleY(.6f);

        view.setTranslationX(300);

        //还原动画
        ViewCompat.animate(view).translationX(0).scaleX(1f).scaleY(1f).setDuration(600).rotation
                (0).setInterpolator(new OvershootInterpolator()).start();

        return view;
    }

    public abstract BasicHolder createViewHolder() ;


}
