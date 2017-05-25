package org.mercury.oschina.explorer.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.explorer.bean.Event;
import org.mercury.oschina.explorer.bean.Post;
import org.mercury.oschina.explorer.bean.PostDetail;
import org.mercury.oschina.explorer.bean.XmlUtils;
import org.mercury.oschina.explorer.util.Fileds;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class AllActivityWebView extends AppCompatActivity {


    @Bind(R.id.tv_message_title)
    TextView mTvMessageTitle;
    @Bind(R.id.tv_message_start)
    TextView mTvMessageStart;
    @Bind(R.id.tv_message_end)
    TextView mTvMessageEnd;
    @Bind(R.id.message_address)
    TextView mMessageAddress;
    @Bind(R.id.tv_at_baoming)
    TextView mTvAtBaoming;
    @Bind(R.id.tv_line)
    TextView mTvLine;
    @Bind(R.id.tv_message)
    TextView mTvMessage;
    @Bind(R.id.wv2)
    WebView mWv2;
    @Bind(R.id.sv)
    ScrollView mSv;
    @Bind(R.id.iv_message_opt)
    ImageView mIvMessageOpt;
    @Bind(R.id.iv_message_review)
    ImageView mIvMessageReview;
    @Bind(R.id.tv_message_count)
    TextView mTvMessageCount;
    @Bind(R.id.iv_message_write)
    ImageView mIvMessageWrite;
    @Bind(R.id.iv_message_favor)
    ImageView mIvMessageFavor;
    @Bind(R.id.iv_message_share)
    ImageView mIvMessageShare;
    @Bind(R.id.ll_message_bar)
    LinearLayout mLlMessageBar;
    @Bind(R.id.iv_message_opt1)
    ImageView mIvMessageOpt1;
    @Bind(R.id.iv_message_emoji)
    ImageView mIvMessageEmoji;
    @Bind(R.id.iv_message_biz)
    ImageView mIvMessageBiz;
    @Bind(R.id.ll_message_bar1)
    LinearLayout mLlMessageBar1;
    private Post mPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("活动详情");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();
        // String stringExtra = intent.getStringExtra(Fileds.URL_WEB);
        Event event = (Event) intent.getSerializableExtra(Fileds.URL_WEB);
        mTvMessageStart.setText("开始: " + event.getStartTime());
        mTvMessageEnd.setText("结束: " + event.getEndTime());
        mTvMessageTitle.setText(event.getTitle());
        mMessageAddress.setText(event.getCity() + " " + event.getSpot());
        //mWv.loadUrl(stringExtra);
       // mWv.loadDataWithBaseURL();
        // mWv2.loadDataWithBaseURL();
        //mWv2.loadUrl(event.get);
        OkHttpUtils.post()
                .url(Fileds.URL + "oschina/detail/post_detail/" + event.getId() + ".xml")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Toast.makeText(AppContext.context() , "请求失败" ,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        PostDetail post = XmlUtils.toBean(PostDetail.class, s.getBytes());
                        mPost = post.getPost();
                        String body = mPost.getBody();
                        WebSettings settings = mWv2.getSettings();
                        settings.setJavaScriptEnabled(true);
                        //mWv2.loadUrl(body);
                       // mWv2.loadData(body,"text/html","utf-8");
                        mWv2.loadDataWithBaseURL(null,body,"text/html","utf-8",null);
                    }
                });
            //报名设置点击事件
            mTvAtBaoming.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(mPost.getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
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
