package org.mercury.oschina.tweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.mercury.oschina.Constant;
import org.mercury.oschina.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends AppCompatActivity {

    @BindView(R.id.wv)
    WebView mWv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        initActionBar();
        initView();
        initData();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("页面");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(Constant.URL);
        mWv.loadUrl(url);
    }

    private void initView() {
        WebSettings settings = mWv.getSettings();
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);
        mWv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });

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
