package org.mercury.oschina.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseActivity;
import org.mercury.oschina.user.FragmentInfo;

import butterknife.BindView;

/**
 * Created by wang.zhonghao on 2017/8/18.
 * 只选择加载哪个fragment,不负责具体的fragment
 */

public class UserSingleInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar     toolbar;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    public static final String FRAGMENT_KEY = "FRAGMENT_KEY";
    public static final String BUNDEL_KEY   = "bundle_key";

    private FragmentInfo mInfo;
    private Bundle       mBundle;

    @Override
    protected int getContentView() {
        return R.layout.base_activity_fragment;
    }

    @Override
    protected void initWidget() {
        toolbar.setTitle(mInfo.getTitle());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });
    }

    @Override
    protected void initData() {
        addFragment(R.id.fl_container, Fragment.instantiate(this, mInfo.getClazz().getName(),
                mBundle));

    }

    @Override
    protected void initBundle(Bundle bundle) {
        if (bundle != null) {
            mBundle = bundle.getBundle(BUNDEL_KEY);
            mInfo = (FragmentInfo) bundle.getSerializable(FRAGMENT_KEY);
        }
    }


    public static void show(Context context, FragmentInfo info, Bundle bundle) {
        Intent intent = new Intent(context, UserSingleInfoActivity.class);
        intent.putExtra(FRAGMENT_KEY, info);
        intent.putExtra(BUNDEL_KEY, bundle);
        context.startActivity(intent);
    }

}
