package org.mercury.oschina.synthesis.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseActivity;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.synthesis.adapter.RelativeRecommendAdapter;
import org.mercury.oschina.synthesis.bean.NewsDetail;
import org.mercury.oschina.utils.StringUtils;
import org.mercury.oschina.widget.OWebView;

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
    OWebView         webview;
    @Bind(R.id.nsv_container)
    NestedScrollView nsvContainer;
    @Bind(R.id.rv_recommend)
    RecyclerView     rvRecommend;
    @Bind(R.id.ll_content)
    LinearLayout     llContent;

    public static final String KEY_ID = "key_id";


    private int newsId;

    private TextView                 mTextView;
    private RelativeRecommendAdapter mAdapter;

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
        toolbar.setTitle("资讯详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });
        rvRecommend.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RelativeRecommendAdapter(this);
        rvRecommend.setAdapter(mAdapter);
        showWaitDialog();
        // TODO: 2017/9/4 底部的富文本输入框,发表评论,收藏,分享

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        MenuItem item = menu.findItem(R.id.menu_comment_coumt);
        if (item != null) {
            View actionView = item.getActionView();
            if (actionView != null) {
                mTextView = (TextView) actionView.findViewById(R.id.tv_comment_count);

            }
        }
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
                hideWaitDialog();
            }
        });
    }

    private void onSuccess(final NewsDetail body) {
        hideWaitDialog();
        tvTitle.setText(body.getTitle());
        tvAuthor.setText("@" + body.getAuthor());
        tvTime.setText("发布于" + StringUtils.friendlyTime(body.getPubDate()));
        mTextView.setText(body.getCommentCount() + "");
        webview.loadDataAsync(body.getBody(), new OWebView.FinishTask() {
            @Override
            public void finishTask() {
                llContent.setVisibility(View.VISIBLE);
                rvRecommend.setVisibility(View.VISIBLE);
                mAdapter.setData(body.getRelativies());
            }
        });

    }
}
