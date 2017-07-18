package org.mercury.oschina.tweet.manager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;

import java.util.List;

/**
 * 类名:      LoadPager
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      ${TODO}
 */
public abstract class LoadPager extends FrameLayout {
    public View mLoadingView;
    private View mSuccessView;

    public LoadPager(Context context) {
        this(context,null);
    }

    public LoadPager(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initLoadPager();
    }

    //实现页面切换
    private void initLoadPager() {
        if (mLoadingView == null) {
            mLoadingView = View.inflate(AppContext.context, R.layout.page_loading, null);
//            ImageView iv = (ImageView) mLoadingView.findViewById(R.id.iv);
//            Animation animation = AnimationUtils.loadAnimation(AppContext.context, R.anim
//                    .anim_alpha_to_hide);
//            iv.setAnimation(animation);
//            animation.start();

        }
        addView(mLoadingView);

        if (mSuccessView == null) {
            mSuccessView = createSuccessView();

        }
        addView(mSuccessView);

        showPage();

        loadData();
    }

    //暴露给调用者实现
    public abstract View createSuccessView();

    public static final int state_loading = 101;

    public static final int state_success = 102;

    public int currentState = state_loading;

    //根据服务器传过来的信息切换当前页面
    public void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //得到解析后的对象
                Object data = onSubLoadData();

                //根据当前对象判断当前状态,这里修改,交给子类自己去判断
//                currentState = checkData(data);
//                Utils.runOnUIThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showPage();
//                    }
//                });
            }
        }).start();

    }

    public int checkData(Object data) {
        if (data == null) {
            return state_loading;
        } else {
            if (data instanceof List) {
                List list = (List) data;
                if (list.size() == 0) {
                    return state_loading;
                }
            }
        }
        return state_success;
    }

    public abstract Object onSubLoadData() ;

    public void showPage() {
        mLoadingView.setVisibility(INVISIBLE);
        mSuccessView.setVisibility(INVISIBLE);

        switch (currentState) {
            case state_loading:
                mLoadingView.setVisibility(VISIBLE);
                break;

            case state_success:
                mSuccessView.setVisibility(VISIBLE);
                break;
            default:
                break;

        }
    }

}
