package org.mercury.oschina.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.mercury.oschina.AppConfig;
import org.mercury.oschina.tweet.activity.ImageGalleryActivity;
import org.mercury.oschina.utils.AppOperator;
import org.mercury.oschina.utils.ConfigUtil;
import org.mercury.oschina.utils.TDevice;

/**
 * Created by wang.zhonghao on 2017/9/4.
 */

public class OWebView extends WebView {

    private Handler mHandler;

    public static final String JSNAME = "android";

    public OWebView(Context context) {
        super(context);
        init();
    }

    public OWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @SuppressLint("JavascriptInterface")
    private void init() {
        mHandler = new Handler();
        setClickable(false);
        setFocusable(false);
        setBackgroundColor(0);
        setHorizontalScrollBarEnabled(false);

        WebSettings settings = getSettings();
        settings.setDefaultFontSize(16);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);

        addJavascriptInterface(this, JSNAME);

    }

    @JavascriptInterface
    public void showImage(String bigImageUrl) {
        ImageGalleryActivity.show(getContext(), new String[]{bigImageUrl});
    }

    public void loadDataAsync(final String content, FinishTask task) {
        setWebViewClient(new OWebViewClient(task));
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                final String body = setupWebContent(content, true, true, "");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        loadDataWithBaseURL("", body, "text/html", "UTF-8", "");
                    }
                });
            }
        });
    }

    public interface FinishTask {

        void finishTask();
    }


    public static String setupWebContent(String content, boolean isShowHighlight, boolean
            isShowImagePreview, String css) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(content.trim())) {
            return "";
        }
        if (ConfigUtil.getBoolean(AppConfig.KEY_LOAD_IMAGE, true) || TDevice.isWifiOpen()) {
            content = content.replaceAll("<([u|o])l.*>", "<$1l style=\"padding-left:20px\">");
            // 过滤掉 img标签的width,height属性
            content = content.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
            content = content.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");

            // 添加点击图片放大支持
            if (isShowImagePreview) {
                // TODO 用一个正则就搞定??
                content = content.replaceAll("<img[^>]+src=\"([^\"\'\\s]+)\"[^>]*>",
                        "<img src=\"$1\" onClick=\"javascript:android.showImage('$1')\"/>");
                content = content.replaceAll(
                        "<a\\s+[^<>]*href=[\"\']([^\"\']+)[\"\'][^<>]*>\\s*<img\\s+src=\"" +
                                "([^\"\']+)\"[^<>]*/>\\s*</a>",
                        "<a href=\"$1\"><img src=\"$2\"/></a>");
            }

        } else {
            // 过滤掉 img标签
            content = content.replaceAll("<\\s*img\\s+([^>]*)\\s*/>", "");
        }

        // 过滤table的内部属性
        content = content.replaceAll("(<table[^>]*?)\\s+border\\s*=\\s*\\S+", "$1");
        content = content.replaceAll("(<table[^>]*?)\\s+cellspacing\\s*=\\s*\\S+", "$1");
        content = content.replaceAll("(<table[^>]*?)\\s+cellpadding\\s*=\\s*\\S+", "$1");


        return String.format(
                "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + (isShowHighlight ? "<link rel=\"stylesheet\" type=\"text/css\" " +
                        "href=\"file:///android_asset/css/shCore.css\">" : "")
                        + (isShowHighlight ? "<link rel=\"stylesheet\" type=\"text/css\" " +
                        "href=\"file:///android_asset/css/shThemeDefault.css\">" : "")
                        + "<link rel=\"stylesheet\" type=\"text/css\" " +
                        "href=\"file:///android_asset/css/common_detail.css\">"
                        + "%s"
                        + "</head>"
                        + "<body>"
                        + "<div class='body-content'>"
                        + "%s"
                        + "</div>"
                        + (isShowHighlight ? "<script type=\"text/javascript\" " +
                        "src=\"file:///android_asset/shCore.js\"></script>" : "")
                        + (isShowHighlight ? "<script type=\"text/javascript\" " +
                        "src=\"file:///android_asset/brush.js\"></script>" : "")
                        + (isShowHighlight ? "<script type=\"text/javascript\">SyntaxHighlighter" +
                        ".all();</script>" : "")
                        + "</body>"
                        + "</html>"
                , (css == null ? "" : css), content);
    }

    @Override
    public void destroy() {
        super.destroy();
        setWebViewClient(null);
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(false);

        removeJavascriptInterface(JSNAME);
        removeAllViewsInLayout();
        removeAllViews();

    }

    private static class OWebViewClient extends WebViewClient implements Runnable {

        private FinishTask tastRunnable;
        private boolean  isPageFinished;

        OWebViewClient(FinishTask finishTask) {
            this.tastRunnable = finishTask;
        }


        @Override
        public synchronized void run() {
            if (tastRunnable != null) {
                tastRunnable.finishTask();
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            isPageFinished = false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (!isPageFinished) {
                isPageFinished = true;
                run();
            }
        }
    }


}
