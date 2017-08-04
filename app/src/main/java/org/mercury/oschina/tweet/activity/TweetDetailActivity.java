package org.mercury.oschina.tweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.mercury.oschina.Constant;
import org.mercury.oschina.R;
import org.mercury.oschina.emoji.EmojiView;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.tweet.adapter.CommentTweetAdapter;
import org.mercury.oschina.tweet.bean.Comment;
import org.mercury.oschina.tweet.bean.CommentList;
import org.mercury.oschina.tweet.bean.Tweet;
import org.mercury.oschina.tweet.holder.TweetHeadHolder;
import org.mercury.oschina.utils.TDevice;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wang.zhonghao on 2017/5/25
 * descript:  动弹详情
 */
public class TweetDetailActivity extends AppCompatActivity implements AdapterView
        .OnItemClickListener, PullToRefreshBase.OnRefreshListener {

    @Bind(R.id.toolbar)
    Toolbar               toolbar;
    @Bind(R.id.ptr_tweet_refresh)
    PullToRefreshListView ptrTweetRefresh;
    @Bind(R.id.et_content)
    EditText              etContent;
    @Bind(R.id.iv_tweet_emoji)
    ImageView             ivTweetEmoji;
    @Bind(R.id.emoji_keyboard_fragment)
    FrameLayout           emojiKeyboardFragment;
    private EditText    mEtContent;
    private ImageView   mIvTweetEmoji;
    private FrameLayout mEmojiKeyboardFragment;

    private CommentTweetAdapter   mAdapter;
    private PullToRefreshListView mPtrListView;

    private boolean isLoadMore = false;
    private int     page       = 1;
    private List<Comment> mList;
    private int           mId;

    private TweetHeadHolder mTweetHeadHolder;
    private EmojiView       mEmojiView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        ButterKnife.bind(this);


        mEtContent = (EditText) findViewById(R.id.et_content);
        mIvTweetEmoji = (ImageView) findViewById(R.id.iv_tweet_emoji);

        //占位弹出选择框
        mEmojiKeyboardFragment = (FrameLayout) findViewById(R.id.emoji_keyboard_fragment);


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
        getTweetDetail(mId);
        getCommentData(mId, 3, page);

        toolbar.setTitle("动弹详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });

        mAdapter = new CommentTweetAdapter();
        mPtrListView.setAdapter(mAdapter);
    }

    private void getCommentData(int id, int catalog, int page) {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<CommentList> commentList = retrofitCall.getCommentList(id, catalog, page);
        commentList.enqueue(new Callback<CommentList>() {
            @Override
            public void onResponse(Call<CommentList> call, Response<CommentList> response) {
                CommentList body = response.body();
                if (body != null && body.getCommentList().size() > 0) {
                    mList = body.getCommentList();
                    if (isLoadMore) {
                        loadMore();
                    } else {
                        refresh();
                    }
                    mPtrListView.onRefreshComplete();
                }
            }

            @Override
            public void onFailure(Call<CommentList> call, Throwable t) {

            }
        });

    }

    private void loadMore() {
        mAdapter.addDatas(mList);
    }

    private void refresh() {
        mAdapter.updateDatas(mList);
    }

    private void getTweetDetail(int id) {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<Tweet> tweetDetail = retrofitCall.getTweetDetail(id);
        tweetDetail.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> call, Response<Tweet> response) {
                Tweet body = response.body();
                if (body != null) {
                    mTweetHeadHolder.bindView(mPtrListView, body);
                }

            }

            @Override
            public void onFailure(Call<Tweet> call, Throwable t) {

            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        switch (refreshView.getCurrentMode()) {

            case PULL_FROM_START:
                isLoadMore = false;
                page = 0;
                getCommentData(mId, 3, page);
                break;

            case PULL_FROM_END:
                isLoadMore = true;
                page++;
                getCommentData(mId, 3, page);
                break;

            default:
                break;

        }
    }
}
