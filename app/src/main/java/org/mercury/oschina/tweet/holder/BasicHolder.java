package org.mercury.oschina.tweet.holder;

import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/21
 * 描述:      ${TODO}
 */
public abstract class BasicHolder<T> {

    View view;

    public BasicHolder() {
        view = createView();
        ButterKnife.bind(this, view);
        view.setTag(this);

    }

    public abstract View createView();

    public View getView() {
        return view;
    }

    public abstract void bindView(ViewGroup parent,T t);
}
