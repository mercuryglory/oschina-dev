package org.lion.oschina.explorer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.lion.oschina.explorer.bean.Blog;
import org.lion.oschina.explorer.bean.BlogDetail;
import org.lion.oschina.explorer.bean.XmlUtils;
import org.lion.oschina.explorer.util.Fileds;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class BlogInfoActivity extends AppCompatActivity {

    @Bind(R.id.tv_message_title)
    TextView mTvMessageTitle;
    @Bind(R.id.tv_message_author)
    TextView mTvMessageAuthor;
    @Bind(R.id.tv_message_time)
    TextView mTvMessageTime;
    @Bind(R.id.tv_line)
    TextView mTvLine;
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
    private int mExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mExtra = intent.getIntExtra(Fileds.BLOG_ID, 0);
        System.out.println("mextra" + mExtra);
        init();
    }

    private void init() {
        OkHttpUtils.get()
                .url(Fileds.URL + "oschina/detail/blog_detail/" + mExtra + ".xml")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        System.out.println("获取数据失败");
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        BlogDetail blogDetail = XmlUtils.toBean(BlogDetail.class, s.getBytes());
                        Blog blog = blogDetail.getBlog();
                      // mWv2.loadData(blog.getBody(),"text/html","utf-8");
                        mWv2.loadDataWithBaseURL(null,blog.getBody(),"text/html","utf-8",null);
                        mTvMessageTitle.setText(blog.getTitle());
                        mTvMessageAuthor.setText(blog.getAuthor());
                        mTvMessageTime.setText(blog.getPubDate());
                    }
                });
    }
}
