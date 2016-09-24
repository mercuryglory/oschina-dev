package org.lion.oschina.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TianZiDetailesActivity extends AppCompatActivity {


    @Bind(R.id.wv_webview2)
    WebView mWvWebview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details2);
        ButterKnife.bind(this);


        initActionbar();
        final String appurl = getIntent().getStringExtra("TianZi");
        System.out.println("-----------" + appurl);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

        mWvWebview2.loadUrl(appurl);
            }
        });
    }

    private void initActionbar() {
        // 得到actionbar
        ActionBar actionBar = getSupportActionBar();
        // 设置标题
        actionBar.setTitle("帖子详情");

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
