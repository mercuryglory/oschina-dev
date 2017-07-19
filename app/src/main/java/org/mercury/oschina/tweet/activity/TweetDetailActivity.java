package org.mercury.oschina.tweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.mercury.oschina.R;
import org.mercury.oschina.emoji.EmojiView;
import org.mercury.oschina.tweet.adapter.CommentTweetAdapter;
import org.mercury.oschina.tweet.bean.Comment;
import org.mercury.oschina.tweet.bean.CommentList;
import org.mercury.oschina.tweet.bean.Tweet;
import org.mercury.oschina.tweet.bean.TweetDetail;
import org.mercury.oschina.tweet.holder.TweetHeadHolder;
import org.mercury.oschina.tweet.net.HttpApi;
import org.mercury.oschina.tweet.url.TweetUrl;
import org.mercury.oschina.tweet.util.Constant;
import org.mercury.oschina.tweet.util.ToastUtil;
import org.mercury.oschina.tweet.util.Utils;
import org.mercury.oschina.tweet.util.XmlUtils;
import org.mercury.oschina.utils.TDevice;

import java.util.List;

import okhttp3.Call;

/**
 * Created by wang.zhonghao on 2017/5/25
 * descript:  动弹详情
 */
public class TweetDetailActivity extends AppCompatActivity implements AdapterView
        .OnItemClickListener, PullToRefreshBase.OnRefreshListener {

    private EditText mEtContent;
    private ImageView mIvTweetEmoji;
    private FrameLayout mEmojiKeyboardFragment;

    private CommentTweetAdapter mAdapter;
    private PullToRefreshListView mPtrListView;

    private boolean isLoadMore = false;
    private int pageIndex = 0;
    private List<Comment> mList;
    private int mId;

    private TweetHeadHolder mTweetHeadHolder;
    private EmojiView mEmojiView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);


        mEtContent =(EditText)findViewById(R.id.et_content);
        mIvTweetEmoji = (ImageView) findViewById(R.id.iv_tweet_emoji);

        //占位弹出选择框
        mEmojiKeyboardFragment =(FrameLayout)findViewById(R.id.emoji_keyboard_fragment);

        initActionBar();

        mPtrListView = (PullToRefreshListView) findViewById(R.id.ptr_tweet_refresh);
        mTweetHeadHolder = new TweetHeadHolder();
        mPtrListView.getRefreshableView().addHeaderView(mTweetHeadHolder.getView());
        initEmoji();
        initEvent();
        initData();
    }


    private void initEmoji() {

        mEtContent.setFocusable(true);
        mEtContent.setFocusableInTouchMode(true);
        mEtContent.requestFocus();

        //弹出表情选择框
        mIvTweetEmoji.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (mEmojiView == null) {
                    mEmojiView = new EmojiView(TweetDetailActivity.this, mEtContent);
                    mEmojiKeyboardFragment.addView(mEmojiView);
                }
                mEmojiView.openPanel();
                TDevice.closeKeyboard(mEtContent);

            }
        });
    }

    private void initEvent() {
        mPtrListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPtrListView.setOnRefreshListener(this);
        mPtrListView.setOnItemClickListener(this);
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        //设置标题
        actionBar.setTitle("动弹详情");

        //设置箭头,固定写法,一起出现
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        Intent intent = getIntent();
        mId = intent.getIntExtra(Constant.TWEET_DETAIL, 0);
        getHttpData(mId);
        getCommentData(mId);
    }

    private void getCommentData(int id) {
        StringCallback callback = new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                mPtrListView.onRefreshComplete();
                ToastUtil.showToast("请求失败");
            }

            @Override
            public void onResponse(String response, int i) {
                ToastUtil.showToast("请求成功");
                CommentList commentList = XmlUtils.toBean(CommentList.class, response);
                mList = commentList.getList();
                Utils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isLoadMore) {
                            loadMore();
                        } else {
                            refresh();
                        }

                        if (mPtrListView != null) {
                            mPtrListView.onRefreshComplete();
                        }
                    }
                });

            }

        };

        HttpApi.getTweetComment(pageIndex, id, callback);
    }

    private void loadMore() {
        if (mAdapter == null) {
            mAdapter = new CommentTweetAdapter();
            mAdapter.updateDatas(mList);
            mPtrListView.setAdapter(mAdapter);
        } else {
            mAdapter.addDatas(mList);
        }
    }

    private void refresh() {

        if (mAdapter == null) {
            mAdapter = new CommentTweetAdapter();
            mAdapter.updateDatas(mList);
            mPtrListView.setAdapter(mAdapter);
        } else {
            mAdapter.updateDatas(mList);
        }
    }

    private void getHttpData(int id) {
        StringCallback callback = new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.showToast("请求失败");
            }

            @Override
            public void onResponse(String response, int i) {
                ToastUtil.showToast("请求成功");
                TweetDetail tweetDetail = XmlUtils.toBean(TweetDetail.class, response);
                final Tweet tweet = tweetDetail.getTweet();
                Utils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mTweetHeadHolder.bindView(mPtrListView,tweet);
                    }
                });

            }

        };
        OkHttpUtils.get().url(TweetUrl.TWEET_DETAIL).addParams("id", id + "")
                .build().execute(callback);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        switch (refreshView.getCurrentMode()) {

            case PULL_FROM_START:
                isLoadMore = false;
                pageIndex = 0;
                getCommentData(mId);
                break;

            case PULL_FROM_END:
                isLoadMore = true;
                pageIndex++;
                getCommentData(mId);
                break;

            default:
                break;

        }
    }
}
