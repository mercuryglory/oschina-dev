package org.mercury.oschina.synthesis.holder;

import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.mercury.oschina.R;

import butterknife.ButterKnife;

public abstract class BasicHolder<T> {
    protected View view;
    public DisplayImageOptions mOptions;

    public View getView() {
        return view;
    }

    public BasicHolder() {
        view = createView();
        ButterKnife.bind(this,view);
        view.setTag(this);

        mOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)

                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)// 会识别图片的方向信息
                .displayer(new FadeInBitmapDisplayer(500))
                .build();

    }

    public abstract TextView getTitleView();

    public abstract void bindView(T t);

    public abstract View createView();
}