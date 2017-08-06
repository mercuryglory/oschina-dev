package org.mercury.oschina.tweet.holder;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.mercury.oschina.Constant;
import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.tweet.activity.PhotoActivity;
import org.mercury.oschina.tweet.activity.UserHomeActivity;
import org.mercury.oschina.tweet.activity.WebActivity;
import org.mercury.oschina.tweet.bean.Tweet;
import org.mercury.oschina.tweet.bean.User;
import org.mercury.oschina.tweet.util.GlideUtils;
import org.mercury.oschina.utils.StringUtils;

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
    @Bind(R.id.wv)
    WebView       mWv;
    @Bind(R.id.iv_tweet_image)
    ImageView     mIvTweetImage;
    @Bind(R.id.tv_tweet_comment_count)
    TextView      mTvTweetCommentCount;

    @Override
    public View createView() {
        return View.inflate(AppContext.context, R.layout.head_tweet_detail, null);
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

                Intent intent = new Intent(AppContext.context, UserHomeActivity.class);
                intent.putExtra(Constant.USER_ID, user);
                parent.getContext().startActivity(intent);
            }
        });

        if (TextUtils.isEmpty(tweet.getImgBig()) && TextUtils.isEmpty(tweet.getImgSmall())) {
            mIvTweetImage.setVisibility(View.GONE);
        } else {
            mIvTweetImage.setVisibility(View.VISIBLE);
            Glide.with(AppContext.context).load(tweet.getImgBig())
                    .into(mIvTweetImage);

            mIvTweetImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AppContext.context, PhotoActivity.class);
                    intent.putExtra(Constant.PICTURE, tweet.getImgBig());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    AppContext.context().startActivity(intent);
                }
            });
        }

        mTvTweetCommentCount.setText(tweet.getCommentCount() + "");
        mTvTweetName.setText(tweet.getAuthor());
        mTvTweetTime.setText(StringUtils.friendly_time(tweet.getPubDate()));

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


    }
}
