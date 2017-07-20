package org.mercury.oschina.synthesis.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.mercury.oschina.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity {

    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.tv_source)
    TextView mTvSource;
    @Bind(R.id.ll_header)
    LinearLayout mLlHeader;
    @Bind(R.id.webview)
    WebView mWebview;
    @Bind(R.id.sv_news_container)
    ScrollView mSvNewsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String href = intent.getStringExtra("href");
        Logger.i(href + "");
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        initActionbar();
        mWebview.loadUrl(href);
    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("详情");
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
