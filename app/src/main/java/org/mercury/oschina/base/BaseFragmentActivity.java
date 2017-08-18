package org.mercury.oschina.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import org.mercury.oschina.R;

import butterknife.Bind;

/**
 * Created by wang.zhonghao on 2017/8/18.
 * 只选择加载哪个fragment,不负责具体的fragment
 */

public abstract class BaseFragmentActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar     toolbar;
    @Bind(R.id.fl_container)
    FrameLayout flContainer;

    public static final String TITLE_KEY = "TITLE_KEY";
    protected String title;

    @Override
    protected int getContentView() {
        return R.layout.base_activity_fragment;
    }

    @Override
    protected void initWidget() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });
    }

    @Override
    protected void initData() {
        addFragment(R.id.fl_container, getFragment());
    }

    @Override
    protected void initBundle(Bundle bundle) {
        if (bundle != null) {
            title = bundle.getString(TITLE_KEY);
        }
    }

    protected abstract Fragment getFragment();

}
