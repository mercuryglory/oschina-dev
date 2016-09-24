package org.lion.oschina.holder;

import android.app.Activity;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import butterknife.ButterKnife;

/**
 * Created by sy_heima on 2016/8/3.
 */
public abstract class BasicHolder<T>
{
    
    // 定义一个view
    View view;
    public  DisplayImageOptions mOptions;
    protected Activity            mActivity;

    public BasicHolder(Activity activity) {
        mActivity = activity;
        view = createView();

        ButterKnife.bind(this,view);

        // 领证
        view.setTag(this);
        // 会识别图片的方向信息
        // 显示的效果
        mOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)// 会识别图片的方向信息
                .displayer(new FadeInBitmapDisplayer(500))
                //.build();
                .displayer(new RoundedBitmapDisplayer(36)).build();
    }

    public View getView()
    {
        return view;
    }
    
    public BasicHolder()
    {
        
        this(null);
        
    }
    
    // 生成布局
    protected abstract View createView();
    
    // 绑定数据
    public abstract void bindView(T appInfo);
}
