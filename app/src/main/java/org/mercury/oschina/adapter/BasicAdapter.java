package org.mercury.oschina.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.mercury.oschina.holder.BasicHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mercury on 2016/8/2.
 */
public abstract class BasicAdapter<T> extends BaseAdapter {

    protected List<T> mShowItems = new ArrayList<>();

    public BasicAdapter(List<T> showItems) {
        this.mShowItems = showItems;
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

    public int headType = 0;
    public int bodyType = 1;

    //类型
    @Override
    public int getItemViewType(int position) {

        if (mShowItems.get(position) instanceof String) {
            return headType;
        } else {
            return bodyType;
        }

    }

    //种类
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BasicHolder viewHolder;
        if (convertView == null) {
            /*convertView = View.inflate(GooglePlayApp.context, R.layout.item_home, null);
            viewHolder = new ViewHolder(convertView);*/
            viewHolder = createViewHolder(position);
            //convertView.setTag(viewHolder);
        } else {
            viewHolder = (BasicHolder) convertView.getTag();
        }

        //赋值
        viewHolder.bindView(mShowItems.get(position));
        //bindView(viewHolder, mShowItems.get(position));


        View view = viewHolder.getView();

        //先缩小
      /*  view.setScaleX(.6f);
        view.setScaleY(.6f);

        view.setRotation(145);

        view.setTranslationX(300);
*/


        //还原动画
        //       ViewCompat.animate(view).translationX(0).scaleX(1f).scaleY(1f).setDuration(600)
        // .rotation(0).setInterpolator(new OvershootInterpolator()).start();


        return view;
    }

    public abstract BasicHolder createViewHolder(int postion);


}
