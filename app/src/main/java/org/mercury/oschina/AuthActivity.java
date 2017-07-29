package org.mercury.oschina;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.mercury.oschina.utils.PropertyHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthActivity extends AppCompatActivity {

    WebView mWebView;
    String redirectUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_web);
        mWebView = (WebView) findViewById(R.id.web);

        mWebView.getSettings().setJavaScriptEnabled(true);
        String authUrl = "https://www.oschina" +
                ".net/action/oauth2/authorize?response_type=code&client_id=" + BuildConfig
                .CLIENT_ID + "&redirect_uri="+BuildConfig.REDIRECT_URI;
        mWebView.loadUrl(authUrl);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                redirectUrl = url;
                try {
                    String code = Uri.parse(redirectUrl).getQueryParameter("code");
                    initRequest(code);
                    int a = 1;
//                    finish();
                } catch (Exception e) {

                }
                //默认方法返回false，如果返回true的话，就可以让我们自己决定怎么处理重定向的url，而不是直接跳转
                return false;
            }

        });

        String code = Uri.parse(redirectUrl).getQueryParameter("code");
        int a = 1;

//                initRequest();
    }

    private void initRequest(String code) {
        String tokenUrl = "https://www.oschina.net/action/openapi/token";
        RequestBody requestBody = new FormBody.Builder()
                .add("client_id", BuildConfig.CLIENT_ID)
                .add("client_secret", BuildConfig.CLIENT_SECRET)
                .add("grant_type", "authorization_code")
                .add("code", code)
                .add("redirect_uri", BuildConfig.REDIRECT_URI).build();
        Request request = new Request.Builder().post(requestBody).url(tokenUrl).build();

        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String s = e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                String string = response.body().string();
                int a = 2;
            }
        });

        PropertyHelper.get();

    }


}
