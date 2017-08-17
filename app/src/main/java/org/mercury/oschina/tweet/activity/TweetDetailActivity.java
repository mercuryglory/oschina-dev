package org.mercury.oschina.tweet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseActivity;
import org.mercury.oschina.emoji.EmojiView;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.tweet.adapter.CommentTweetAdapter;
import org.mercury.oschina.tweet.bean.Comment;
import org.mercury.oschina.tweet.bean.CommentResponse;
import org.mercury.oschina.tweet.bean.Tweet;
import org.mercury.oschina.tweet.bean.User;
import org.mercury.oschina.tweet.util.GlideUtils;
import org.mercury.oschina.tweet.util.TweetParser;
import org.mercury.oschina.tweet.widget.TweetTextView;
import org.mercury.oschina.utils.StringUtils;
import org.mercury.oschina.utils.TDevice;
import org.mercury.oschina.widget.TweetPicturesLayout;
import org.mercury.oschina.widget.recyclerload.HaoRecyclerView;
import org.mercury.oschina.widget.recyclerload.OnLoadMoreListener;

import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wang.zhonghao on 2017/5/25
 * descript:  动弹详情
 */
public class TweetDetailActivity extends BaseActivity implements SwipeRefreshLayout
        .OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.toolbar)
    Toolbar             toolbar;
    @Bind(R.id.et_content)
    EditText            etContent;
    @Bind(R.id.iv_tweet_emoji)
    ImageView           ivTweetEmoji;
    @Bind(R.id.emoji_keyboard_fragment)
    FrameLayout         emojiKeyboardFragment;
    @Bind(R.id.iv_tweet_face)
    ImageView           ivTweetFace;
    @Bind(R.id.tv_tweet_name)
    TextView            tvTweetName;
    @Bind(R.id.tv_tweet_body)
    TweetTextView       tvTweetBody;
    @Bind(R.id.tv_tweet_time)
    TextView            tvTweetTime;
    @Bind(R.id.tv_tweet_comment_count)
    TextView            tvTweetCommentCount;
    @Bind(R.id.recyclerview)
    HaoRecyclerView     recyclerview;
    @Bind(R.id.layout_tweet_picture)
    TweetPicturesLayout layoutTweetPicture;
    @Bind(R.id.layout_appbar)
    AppBarLayout        layoutAppbar;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout  refreshLayout;

    private CommentTweetAdapter mAdapter;

    private boolean isLoadMore = false;
    private int     page       = 1;
    public static final String KEY_TWEET = "KEY_TWEET";

    private EmojiView mEmojiView;
    private Tweet mTweet;

    public static void show(Context context, Tweet tweet) {
        Intent intent = new Intent(context, TweetDetailActivity.class);
        intent.putExtra(KEY_TWEET, tweet);
        context.startActivity(intent);

    }

    @Override
    protected void initBundle(Bundle bundle) {
        mTweet = bundle.getParcelable(KEY_TWEET);
        if (mTweet == null) {
            return;
        }
    }

    private void initEmoji() {

        //弹出表情选择框
        ivTweetEmoji.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (mEmojiView == null) {
                    mEmojiView = new EmojiView(TweetDetailActivity.this, etContent);
                    emojiKeyboardFragment.addView(mEmojiView);
                }
                mEmojiView.openPanel();
                TDevice.closeKeyboard(etContent);

            }
        });
    }


    protected void initData() {
        initEmoji();

        //动弹详情从列表页传递,效率更高
        updateTweetDetail(mTweet);

        //获取评论列表
        getCommentList(mTweet.getId(), 3, page);

        toolbar.setTitle("动弹详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommentTweetAdapter(this);
        recyclerview.setAdapter(mAdapter);
        refreshLayout.setOnRefreshListener(this);
        recyclerview.setOnLoadMoreListener(this);

        refreshLayout.setColorSchemeResources(R.color.swiperefresh_color1, R.color
                .swiperefresh_color2, R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        layoutAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    refreshLayout.setEnabled(true);
                } else {
                    refreshLayout.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_tweet_detail;
    }

    private void getCommentList(int id, int catalog, int page) {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<CommentResponse> commentList = retrofitCall.getCommentList(id, catalog, page);
        commentList.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                CommentResponse body = response.body();
                if (body.getCommentList() != null && body.getCommentList().size() > 0) {
                    if (isLoadMore) {
                        loadMore(body.getCommentList());
                    } else {
                        refresh(body.getCommentList());
                    }
                } else {
                    recyclerview.loadMoreEnd();
                }
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {

            }
        });

    }

    private void loadMore(List<Comment> commentList) {
        if (commentList.size() > 0) {
            mAdapter.addAll(commentList);
            recyclerview.loadMoreComplete();
        } else {
            recyclerview.loadMoreEnd();
        }
    }

    private void refresh(List<Comment> commentList) {
        if (mAdapter == null) {
            mAdapter.addAll(commentList);
        } else {
            mAdapter.setData(commentList);
        }
        recyclerview.refreshComplete();

    }


    private void updateTweetDetail(final Tweet tweet) {
        GlideUtils.loadCircleImage(this, tweet.getPortrait(), ivTweetFace);
        //头像跳转到动弹详情
        ivTweetFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUid(tweet.getAuthorid());
                user.setName(tweet.getAuthor());
                user.setPortrait(tweet.getPortrait());
                OtherUserHomeActivity.show(TweetDetailActivity.this, user);
            }
        });

        if (TextUtils.isEmpty(tweet.getImgBig()) && TextUtils.isEmpty(tweet.getImgSmall())) {
            layoutTweetPicture.setVisibility(View.GONE);
        } else {
            layoutTweetPicture.setVisibility(View.VISIBLE);
            //加载内容图片,可能一张或多张
            layoutTweetPicture.setImage(tweet.getImgSmall(), tweet.getImgBig());
        }

        //设置内容中的富文本
        if (!TextUtils.isEmpty(tweet.getBody())) {
            String content = tweet.getBody().replaceAll("[\n\\s]+", " ");
            tvTweetBody.setText(TweetParser.getInstance().parse(this, content));
            tvTweetBody.setMovementMethod(LinkMovementMethod.getInstance());
            tvTweetBody.setFocusable(false);
            tvTweetBody.setDispatchToParent(true);
            tvTweetBody.setLongClickable(false);
        }

        tvTweetCommentCount.setText(tweet.getCommentCount() + "");
        tvTweetName.setText(tweet.getAuthor());
        tvTweetTime.setText(StringUtils.friendly_time(tweet.getPubDate()));


    }


    @Override
    public void onRefresh() {
        isLoadMore = false;
        recyclerview.setCanloadMore(false);
        page = 1;
        getCommentList(mTweet.getId(), 3, page);
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        page++;
        getCommentList(mTweet.getId(), 3, page);
    }


}
