package org.mercury.oschina.explorer.ui;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseActivity;
import org.mercury.oschina.base.BaseFragment;

/**
 * created by Mercury at 2017/7/27
 * descript: 开源软件 承载一个viewpagerfragment
 */
public class SoftwareActivity extends BaseActivity {

    BaseFragment mFragment;

    @Override
    protected int getContentView() {
        return R.layout.base_activity_fragment;
    }

    @Override
    protected void initWidget() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("开源软件");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        
    }

    @Override
    protected void initData() {
        mFragment = (BaseFragment) Fragment.instantiate(this, SoftwareFragment.class.getName(), null);
        addFragment(R.id.fl_container, mFragment);
    }

    @Override
    public void onBackPressed() {
        if (mFragment != null) {
            if (!mFragment.onBackPressed()) {
                return;
            }
        }
//        EventBus.getDefault().post("here");
        super.onBackPressed();
    }
}
