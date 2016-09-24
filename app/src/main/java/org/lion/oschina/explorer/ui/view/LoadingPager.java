package org.lion.oschina.explorer.ui.view;


import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

/**
 * @创建者 LY
 * @创建时间 2016/8/2 23:59
 * @描述 ${TODO}
 */
public abstract class LoadingPager extends FrameLayout {
    private View mSuccess;
    private View mFail;
    private View mLoading;

    //定义三种状态进行 区分页面 然后定义一个变量 用来记录
    private static final int start_success = 101;
    private static final int start_loading = 102;
    private static final int start_fail = 103;

    private int start_change = start_loading;

    public LoadingPager(Context context) {
        this(context, null);
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStataView();
    }

    //现在我们的主要的功能就是页面切换
    //主要的思想就是3中不同的页面叠加在一起
    //然后 根据不同的状态进行切换


    private void initStataView() {
        if (mSuccess == null) {
            //自己根据需求实现
            mSuccess = isOnSuccess();
        }
        addView(mSuccess);
        if (mLoading == null) {
            mLoading = View.inflate(getContext(), R.layout.page_loading, null);

        }
        addView(mLoading);
        if (mFail == null) {
            mFail = View.inflate(getContext(), R.layout.page_error, null);
        }
        addView(mFail);

        // 显示默认的页面
        showView();

        //服务端获取数据 更新 ui界面
        LoaderData();
    }

    /**
     * 服务器获取数据 需要开启子线程去操作
     */
    private void LoaderData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //服务请求数据 模拟睡两秒
                SystemClock.sleep(2000);
                //根据需求 去网络获取数据
                onLoad();
                //根据获取的信息进行判断 改变状态更新页面

            }
        }).start();
    }

    protected boolean onDataComplete(Object obj){
        start_change = changeStata(obj);
        return start_change == start_success;
        //在主线程跟新ui
    }

    private int changeStata(Object data) {
        if (data != null) {
            if (data instanceof List) {
                List list = (List) data;
                if (list.size() == 0) {
                    return start_fail;
                } else {
                    return start_success;
                }
        }else{
                return  start_success;
            }
        } else {
            return  start_fail;
        }
    }

    protected void showView() {
        //初始化全部默认设置为隐藏
        mSuccess.setVisibility(GONE);
        mFail.setVisibility(GONE);
        mLoading.setVisibility(GONE);

        switch (start_change) {
            case start_loading:
                mLoading.setVisibility(VISIBLE);
                break;
            case start_fail:
                mFail.setVisibility(VISIBLE);
                break;
            case start_success:
                mSuccess.setVisibility(VISIBLE);
                break;
        }
    }

    public abstract View isOnSuccess();

    public abstract void onLoad();
}
