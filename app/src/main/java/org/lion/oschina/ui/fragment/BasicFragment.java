package org.lion.oschina.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.lion.oschina.uimanger.LoadPager;


/**
 * Created by sy_heima on 2016/8/2.
 */
public abstract class BasicFragment extends Fragment
{
    protected LoadPager mMLoadPager;


    //    public LoadPager mLoadPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        if (mMLoadPager == null) {
            mMLoadPager = new LoadPager(getContext()) {
                @Override
                public View createSuccessView() {

                    return createView();
                }

                @Override
                protected Object onSubLoadData() {
                    return loadDataThread();
                }
            };
        }

        return mMLoadPager;
    }

    protected abstract Object loadDataThread();

    public abstract View createView();
}
