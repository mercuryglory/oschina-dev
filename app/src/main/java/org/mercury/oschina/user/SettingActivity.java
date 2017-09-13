package org.mercury.oschina.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseActivity;
import org.mercury.oschina.utils.TDevice;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar        toolbar;
    @Bind(R.id.tv_cache_size)
    TextView       tvCacheSize;
    @Bind(R.id.rl_clear_cache)
    RelativeLayout rlClearCache;
    @Bind(R.id.tv_goto_market)
    TextView       tvGotoMarket;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    public static void show(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initWidget() {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.rl_clear_cache, R.id.tv_goto_market})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_clear_cache:
                break;
            case R.id.tv_goto_market:
                TDevice.gotoMarket(this, getPackageName());
                break;
        }
    }
}
