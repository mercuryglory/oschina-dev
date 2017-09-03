package org.mercury.oschina.synthesis.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseActivity;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.synthesis.bean.NewsDetail;
import org.mercury.oschina.utils.StringUtils;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wang.zhonghao on 2017/8/17
 * description:  资讯详情
 */
public class NewsDetailActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar          toolbar;
    @Bind(R.id.tv_title)
    TextView         tvTitle;
    @Bind(R.id.tv_author)
    TextView         tvAuthor;
    @Bind(R.id.tv_time)
    TextView         tvTime;
    @Bind(R.id.webview)
    WebView          webview;
    @Bind(R.id.nsv_container)
    NestedScrollView nsvContainer;

    public static final String KEY_ID = "key_id";

    private int newsId;

    @Override
    protected int getContentView() {
        return R.layout.activity_news_detail;
    }

    public static void show(Context context, int id) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(KEY_ID, id);
        context.startActivity(intent);

    }

    @Override
    protected void initBundle(Bundle bundle) {
        if (bundle != null) {
            newsId = bundle.getInt(KEY_ID, 0);
        }
    }

    @Override
    protected void initWidget() {
        toolbar.setTitle("咨询详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void initData() {
        request();
    }

    private void request() {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<NewsDetail> newsDetail = retrofitCall.getNewsDetail(newsId);
        newsDetail.enqueue(new Callback<NewsDetail>() {
            @Override
            public void onResponse(Call<NewsDetail> call, Response<NewsDetail> response) {
                NewsDetail body = response.body();
                if (body != null) {
                    onSuccess(body);
                }
            }

            @Override
            public void onFailure(Call<NewsDetail> call, Throwable t) {

            }
        });
    }

    private void onSuccess(NewsDetail body) {
        tvTitle.setText(body.getTitle());
        tvAuthor.setText("@" + body.getAuthor());
        tvTime.setText("发布于" + StringUtils.friendlyTime(body.getPubDate()));
        webview.loadDataWithBaseURL("", body.getBody(), "text/html", "UTF-8", "");
    }
}
