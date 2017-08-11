package org.mercury.oschina.tweet.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mercury.oschina.tweet.util.ToastUtil;

import butterknife.ButterKnife;


/**
 * 类名:      BaseFragment
 * 创建者:    Mercury
 * 创建时间:  2016/8/15
 * 描述:      Fragment 基类
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected View mRoot;
    protected Bundle mBundle;
    protected LayoutInflater mInflater;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initBundle(mBundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null) {
                parent.removeView(mRoot);

            }
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            mInflater = inflater;

            //可留给子类重写,这里面不要做操作控件的事情
            onBindViewBefore(mRoot);

            ButterKnife.bind(this, mRoot);
            if (savedInstanceState != null) {
                onRestartInstance(savedInstanceState);
            }

            //init
            initWidget(mRoot);
            initData();
        }
        return mRoot;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mBundle = null;
    }

    protected void initData() {

    }

    protected void initWidget(View root) {
    }

    protected void onBindViewBefore(View root) {
    }

    protected void onRestartInstance(Bundle savedInstanceState) {

    }

    protected abstract int getLayoutId();

    protected void initBundle(Bundle bundle) {

    }

    protected void showToast(String msg) {
        ToastUtil.showToast(mContext, msg);
    }
}
