package org.mercury.oschina.main.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;

import org.mercury.oschina.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by Mercury at 2017/7/19
 * descript: 软件详情
 */
public class AppDetailsActivity extends AppCompatActivity {

    @BindView(R.id.wv_webview)
    WebView mWvWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details);
        ButterKnife.bind(this);
        initActionbar();

        String appurl = getIntent().getStringExtra("APPURL");
        mWvWebview.loadUrl(appurl);
    }

    private void initActionbar() {
        // 得到actionbar
        ActionBar actionBar = getSupportActionBar();
        // 设置标题
        actionBar.setTitle("软件详情");

        // 设置箭头
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
