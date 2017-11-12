package org.mercury.oschina.main.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.orhanobut.dialogplus.SimpleAnimationListener;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wang.zhonghao on 2017/9/8.
 * 浏览器加载的页面,自带路由
 */

public class BrowserActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar      toolbar;
    @BindView(R.id.webview)
    WebView      webview;
    @BindView(R.id.browser_back)
    ImageView    browserBack;
    @BindView(R.id.browser_forward)
    ImageView    browserForward;
    @BindView(R.id.browser_refresh)
    ImageView    browserRefresh;
    @BindView(R.id.browser_system_browser)
    ImageView    browserSystemBrowser;
    @BindView(R.id.ll_browser)
    LinearLayout llBrowser;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    public static final String DEFAULT = "http://www.oschina.net/";
    private String currentUrl = DEFAULT;
    public static final String KEY_URL = "key_url";

    GestureDetector mGestureDetector;
    Animation animationIn, animationOut;

    private int count = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_browser;
    }

    public static void show(Context context, String currentUrl) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(KEY_URL, currentUrl);
        context.startActivity(intent);
    }

    @Override
    protected void initBundle(Bundle bundle) {
        if (bundle != null) {
            currentUrl = bundle.getString(KEY_URL, DEFAULT);
        }
    }

    @Override
    protected void initData() {
        mGestureDetector = new GestureDetector(this, new MyGestureListener());

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(true);
        //优先使用缓存
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webview.loadUrl(currentUrl);
        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
        webview.setWebChromeClient(new MyWebChromeClient());
        webview.setWebViewClient(new MyWebViewClient());

        initAnimation();

    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress > 90) {
                pbLoading.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            toolbar.setTitle(title);
            super.onReceivedTitle(view, title);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            pbLoading.setVisibility(View.VISIBLE);
            currentUrl = url;
            return super.shouldOverrideUrlLoading(view, url);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            pbLoading.setVisibility(View.GONE);
            currentUrl = url;
            super.onPageFinished(view, url);
        }
    }


    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (count % 2 == 0) {
                llBrowser.startAnimation(animationIn);
            } else {
                llBrowser.startAnimation(animationOut);
            }
            count++;
            return super.onDoubleTap(e);
        }
    }

    private void initAnimation() {
        animationIn = AnimationUtils.loadAnimation(this, R.anim.bottom_in);
        animationIn.setAnimationListener(new SimpleAnimationListener(){
            @Override
            public void onAnimationEnd(Animation animation) {
                llBrowser.setVisibility(View.VISIBLE);
            }
        });
        animationOut = AnimationUtils.loadAnimation(this, R.anim.bottom_out);
        animationOut.setAnimationListener(new SimpleAnimationListener(){
            @Override
            public void onAnimationEnd(Animation animation) {
                llBrowser.setVisibility(View.GONE);
            }
        });
    }

    @OnClick({R.id.browser_back, R.id.browser_forward, R.id.browser_refresh, R.id
            .browser_system_browser})
    public void onClick(View view) {
        switch (view.getId()) {
            //回退
            case R.id.browser_back:
                webview.goBack();
                break;
            //转到下一页
            case R.id.browser_forward:
                webview.goForward();
                break;
            //刷新当前
            case R.id.browser_refresh:
                webview.loadUrl(webview.getUrl());
                break;
            //外部浏览器
            case R.id.browser_system_browser:
                Uri uri = Uri.parse(currentUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
    }
}
