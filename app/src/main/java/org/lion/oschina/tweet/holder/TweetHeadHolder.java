package org.lion.oschina.tweet.holder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.lion.oschina.application.MyApplication;
import org.lion.oschina.tweet.activity.PhotoActivity;
import org.lion.oschina.tweet.activity.UserHomeActivity;
import org.lion.oschina.tweet.activity.WebActivity;
import org.lion.oschina.tweet.bean.Tweet;
import org.lion.oschina.tweet.bean.User;
import org.lion.oschina.tweet.util.Constant;
import org.lion.oschina.tweet.util.GlideUtils;
import org.lion.oschina.tweet.util.ToastUtil;
import org.lion.oschina.tweet.widget.TweetTextView;
import org.lion.oschina.utils.StringUtils;

import butterknife.Bind;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/22
 * 描述:      ${TODO}
 */
public class TweetHeadHolder extends BasicHolder<Tweet> {
    @Bind(R.id.iv_tweet_face)
    ImageView     mIvTweetFace;
    @Bind(R.id.tv_tweet_name)
    TextView      mTvTweetName;
    @Bind(R.id.tv_tweet_time)
    TextView      mTvTweetTime;
    @Bind(R.id.tv_tweet_platform)
    TextView      mTvTweetPlatform;
    @Bind(R.id.wv)
    WebView       mWv;
    @Bind(R.id.iv_tweet_image)
    ImageView     mIvTweetImage;
    @Bind(R.id.iv_like_state)
    ImageView     mIvLikeState;
    @Bind(R.id.tv_tweet_comment_count)
    TextView      mTvTweetCommentCount;
    @Bind(R.id.tv_tweet_like)
    TweetTextView mTvTweetLike;

    @Override
    public View createView() {
        return View.inflate(MyApplication.context, R.layout.head_tweet_detail, null);
    }

    @Override
    public void bindView(final ViewGroup parent, final Tweet tweet) {
        GlideUtils.loadCircleImage(parent.getContext(), tweet.getPortrait(), mIvTweetFace);
        //头像跳转到动弹详情
        mIvTweetFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setId(tweet.getAuthorid());
                user.setName(tweet.getAuthor());

                Intent intent = new Intent(MyApplication.context, UserHomeActivity.class);
                intent.putExtra(Constant.USER_ID, user);
                parent.getContext().startActivity(intent);
            }
        });

        if (tweet.getImgBig().equals("") && tweet.getImgSmall().equals("")) {
            mIvTweetImage.setVisibility(View.GONE);
        } else {
            mIvTweetImage.setVisibility(View.VISIBLE);
            Glide.with(MyApplication.context).load(tweet.getImgBig())
                    .into(mIvTweetImage);

            mIvTweetImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyApplication.context, PhotoActivity.class);
                    intent.putExtra(Constant.PICTURE, tweet.getImgBig());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplication.context().startActivity(intent);
                }
            });
        }

        mTvTweetName.setText(tweet.getAuthor());
        mTvTweetTime.setText(StringUtils.friendly_time(tweet.getPubDate()));
        tweet.setLikeUsers(parent.getContext(), mTvTweetLike, true);

        switch (tweet.getAppclient()) {
            case 1:
                mTvTweetPlatform.setVisibility(View.GONE);
                break;
            case 3:
                mTvTweetPlatform.setVisibility(View.VISIBLE);
                mTvTweetPlatform.setText("Android");
                break;
            case 4:
                mTvTweetPlatform.setVisibility(View.VISIBLE);
                mTvTweetPlatform.setText("iPhone");

                break;
            default:
                break;

        }

        WebSettings settings = mWv.getSettings();
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);
        mWv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent(parent.getContext(), WebActivity.class);
                intent.putExtra(Constant.URL, url);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                parent.getContext().startActivity(intent);
                return true;
            }
        });
        mWv.loadDataWithBaseURL(null, tweet.getBody(), "text/html", "utf-8", null);

        //        RichText.from(tweet.getBody()).
        //                autoFix(false).
        //                fix(new ImageFixCallback() {
        //                    @Override
        //                    public void onFix(ImageHolder holder, boolean imageReady) {
        //                        if (imageReady) {
        //                            holder.setAutoFix(false);
        //                        }
        //                    }
        //                }).
        //                urlClick(new OnURLClickListener() {
        //                    @Override
        //                    public boolean urlClicked(String url) {
        //                        Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
        //                        Intent intent = new Intent(MyApplication.context, WebActivity.class);
        //                        intent.putExtra(Constant.URL, url);
        //                        startActivity(intent);
        //                        return true;
        //                    }
        //                }).into(mTvTweetBody);
        mIvLikeState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("先登录才能点赞哟");
            }
        });
        mTvTweetCommentCount.setText(tweet.getLikeCount() + "");
    }
}
