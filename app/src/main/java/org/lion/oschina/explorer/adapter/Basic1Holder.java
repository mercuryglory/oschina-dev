package org.lion.oschina.explorer.adapter;


import android.view.View;

import butterknife.ButterKnife;

/**
 * @创建者 LY
 * @创建时间 2016/8/16 16:43
 * @描述 ${TODO}
 */
public abstract class Basic1Holder<T>{

    View view;

    public View getView() {
        return view;
    }

    public Basic1Holder() {
        view = createView();

        ButterKnife.bind(this,view);

        view.setTag(this);

    }


    public abstract View createView();

    // 绑定数据
    public abstract void bindView(T appInfo);
}
