package org.mercury.oschina.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseActivity;
import org.mercury.oschina.utils.DialogHelper;
import org.mercury.oschina.utils.FileUtil;
import org.mercury.oschina.utils.TDevice;
import org.mercury.oschina.utils.UIHelper;

import java.io.File;

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
    protected void initData() {
        calculateCacheSize();

    }

    private void calculateCacheSize() {
        long dirSize = 0;
        String cacheSize = "0KB";
        File filesDir = getFilesDir();
        File cacheDir = getCacheDir();

        dirSize += FileUtil.getDirSize(filesDir);
        dirSize += FileUtil.getDirSize(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            File externalCacheDir = getExternalCacheDir();
            dirSize += FileUtil.getDirSize(externalCacheDir);
        }
        if (dirSize > 0) {
            cacheSize = FileUtil.formatSize(dirSize);
        }
        tvCacheSize.setText(cacheSize);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.rl_clear_cache, R.id.tv_goto_market,R.id.tv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            //清理缓存,但不会清除数据库和SharedPreference
            case R.id.rl_clear_cache:
                cleanCache();
                break;
            //如果本机的rom有自己的应用市场,去应用市场评分
            case R.id.tv_goto_market:
                TDevice.gotoMarket(this, getPackageName());
                break;
            //注销,也就是清空sp文件,跳转到重新认证界面
            case R.id.tv_logout:

                break;
        }
    }

    private void cleanCache() {
        DialogHelper.getConfirmDialog(this, "是否清空缓存?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UIHelper.clearAppCache(true);
                tvCacheSize.setText("0KB");

            }
        }).show();
    }
}
