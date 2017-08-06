package org.mercury.oschina.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Mercury on 2017/8/5.
 * Activity 的基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String mPackageName = this.getClass().getName();
    protected String mClassName = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());
        ButterKnife.bind(this);

        initWidget();
        initData();

    }

    protected void initData() {

    }

    protected void initWidget() {

    }

    protected abstract int getContentView();


}
