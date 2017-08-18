package org.mercury.oschina.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import org.mercury.oschina.tweet.util.ToastUtil;

import butterknife.ButterKnife;

/**
 * Created by Mercury on 2017/8/5.
 * Activity 的基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String mPackageName = this.getClass().getName();
    protected String mClassName = this.getClass().getSimpleName();

    protected RequestManager mRequestManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBundle(getIntent().getExtras());

        initWindow();

        setContentView(getContentView());
        ButterKnife.bind(this);

        initWidget();
        initData();

    }

    protected void addFragment(int frameLayouId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //已经有这个fragment存在了
            if (fragment.isAdded()) {
                transaction.show(fragment);
            } else {
                transaction.add(frameLayouId, fragment);
            }
            transaction.commit();
        }
    }

    protected void initWindow() {

    }

    protected void initBundle(Bundle bundle) {

    }

    protected void initData() {

    }

    protected void initWidget() {

    }

    protected abstract int getContentView();

    protected void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    public synchronized RequestManager getImageLoader() {
        if (mRequestManager == null) {
            mRequestManager = Glide.with(this);
        }
        return mRequestManager;
    }
}
