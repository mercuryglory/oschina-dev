package org.lion.oschina.tweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.lion.oschina.tweet.adapter.CommentTweetAdapter;
import org.lion.oschina.tweet.bean.Comment;
import org.lion.oschina.tweet.bean.CommentList;
import org.lion.oschina.tweet.bean.Tweet;
import org.lion.oschina.tweet.bean.TweetDetail;
import org.lion.oschina.tweet.holder.TweetHeadHolder;
import org.lion.oschina.tweet.net.HttpApi;
import org.lion.oschina.tweet.url.TweetUrl;
import org.lion.oschina.tweet.util.Constant;
import org.lion.oschina.tweet.util.ToastUtil;
import org.lion.oschina.tweet.util.Utils;
import org.lion.oschina.tweet.util.XmlUtils;

import java.util.List;

import okhttp3.Call;

public class TweetDetailActivity extends AppCompatActivity implements AdapterView
        .OnItemClickListener, PullToRefreshBase.OnRefreshListener
        , EmojiconGridFragment.OnEmojiconClickedListener
        , EmojiconsFragment.OnEmojiconBackspaceClickedListener {


    private com.rockerhieu.emojicon.EmojiconEditText mEtContent;
    private ImageView mIvTweetEmoji;
    private FrameLayout mEmojiKeyboardFragment;

    private CommentTweetAdapter mAdapter;
    private PullToRefreshListView mPtrListView;

    private boolean isLoadMore = false;
    private int pageIndex = 0;
    private List<Comment> mList;
    private int mId;
    private boolean isShowEmoji = false;
    private TweetHeadHolder mTweetHeadHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);


        mEtContent =(com.rockerhieu.emojicon.EmojiconEditText)findViewById(R.id.et_content);
        mIvTweetEmoji =(ImageView)findViewById(R.id.iv_tweet_emoji);
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
        EmojiconsFragment fragment = new EmojiconsFragment();
        //从sp中拿到数据，填充
        initEmoticonsEditText();

    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mEtContent, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mEtContent);
    }


    private void initEmoticonsEditText() {

        mEtContent.setFocusable(true);
        mEtContent.setFocusableInTouchMode(true);
        mEtContent.requestFocus();
        mIvTweetEmoji.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!isShowEmoji) {
                    mEmojiKeyboardFragment.setVisibility(View.VISIBLE);
                } else {
                    mEmojiKeyboardFragment.setVisibility(View.GONE);
                }

                isShowEmoji = !isShowEmoji;

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

        //        设置箭头,固定写法,一起出现
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
                System.out.println("refresh");
                isLoadMore = false;
                pageIndex = 0;
                getCommentData(mId);
                break;

            case PULL_FROM_END:
                System.out.println("more");
                isLoadMore = true;
                pageIndex++;
                getCommentData(mId);
                break;

            default:
                break;

        }
    }
}
