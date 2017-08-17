package org.mercury.oschina.synthesis.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.mercury.oschina.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wang.zhonghao on 2017/8/17
 * description:  资讯详情
 */
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
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
    }


}
