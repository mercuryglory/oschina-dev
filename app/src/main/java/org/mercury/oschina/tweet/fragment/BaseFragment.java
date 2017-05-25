package org.mercury.oschina.tweet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mercury.oschina.tweet.manager.LoadPager;


/**
 * 类名:      BaseFragment
 * 创建者:    Mercury
 * 创建时间:  2016/8/15
 * 描述:      ${TODO}
 */
public abstract class BaseFragment extends Fragment {

    public LoadPager mLoadPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLoadPager == null) {
            mLoadPager = new LoadPager(getContext()) {
                @Override
                public View createSuccessView() {
                    return createView();
                }

                @Override
                public Object onSubLoadData() {
                    return loadDataThread();
                }

            };
        }
        return mLoadPager;
    }

    protected abstract Object loadDataThread();

    protected abstract View createView();
}
