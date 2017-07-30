package org.mercury.oschina;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.mercury.oschina.bean.AccessToken;
import org.mercury.oschina.http.HttpApi;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created by Mercury at 2017/7/30
 * descript: 调用openapi的auth页面
 */
public class AuthActivity extends AppCompatActivity {

    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;
    @Bind(R.id.webview)
    WebView     webview;

    String redirectUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_web);
        ButterKnife.bind(this);

        webview.getSettings().setJavaScriptEnabled(true);
        String authUrl = "https://www.oschina" +
                ".net/action/oauth2/authorize?response_type=code&client_id=" + BuildConfig
                .CLIENT_ID + "&redirect_uri=" + BuildConfig.REDIRECT_URI;

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                redirectUrl = url;
                try {
                    String code = Uri.parse(redirectUrl).getQueryParameter("code");
                    requestToken(code);
                    finish();
                } catch (Exception e) {

                }
                //默认方法返回false，如果返回true的话，就可以让我们自己决定怎么处理重定向的url，而不是直接跳转
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbLoading.setMax(100);
                pbLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.GONE);
            }
        });
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pbLoading.setProgress(newProgress);
            }
        });
        webview.loadUrl(authUrl);
    }

    private void requestToken(String code) {
//        String tokenUrl = "https://www.oschina.net/action/openapi/token";
//
//        RequestBody requestBody = new FormBody.Builder()
//                .add("client_id", BuildConfig.CLIENT_ID)
//                .add("client_secret", BuildConfig.CLIENT_SECRET)
//                .add("grant_type", "authorization_code")
//                .add("code", code)
//                .add("redirect_uri", BuildConfig.REDIRECT_URI).build();
//        Request request = new Request.Builder().post(requestBody).url(tokenUrl).build();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        String a = Constant.BASE_API_URL;
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_API_URL)
                .build();
        HttpApi httpApi = retrofit.create(HttpApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("client_id", BuildConfig.CLIENT_ID);
        params.put("client_secret", BuildConfig.CLIENT_SECRET);
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        params.put("redirect_uri", BuildConfig.REDIRECT_URI);
        Call<AccessToken> call = httpApi.getAccessToken(params);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                int a = response.code();
                AccessToken body = response.body();
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                String s = t.getMessage();
                int b = 2;
            }
        });

//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                String s = e.getMessage();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                int code = response.code();
//                String string = response.body().string();
//                int a = 2;
//            }
//        });
//
//        PropertyHelper.get();

    }


}
