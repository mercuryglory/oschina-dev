package org.mercury.oschina.tweet.holder;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.mercury.oschina.Constant;
import org.mercury.oschina.R;
import org.mercury.oschina.AppContext;
import org.mercury.oschina.tweet.activity.PhotoActivity;
import org.mercury.oschina.tweet.activity.UserHomeActivity;
import org.mercury.oschina.tweet.bean.Tweet;
import org.mercury.oschina.tweet.bean.User;
import org.mercury.oschina.tweet.util.GlideUtils;
import org.mercury.oschina.tweet.util.TweetParser;
import org.mercury.oschina.tweet.widget.TweetTextView;
import org.mercury.oschina.utils.StringUtils;

import butterknife.Bind;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/21
 * 描述:      ${TODO}
 */
public class NewTweetHolder extends BasicHolder<Tweet> {

    @Bind(R.id.iv_tweet_face)
    ImageView     mIvTweetFace;
    @Bind(R.id.tv_tweet_name)
    TextView      mTvTweetName;
    @Bind(R.id.tv_tweet_body)
    TweetTextView mTvTweetBody;
    @Bind(R.id.iv_tweet_image)
    ImageView     mIvTweetImage;
    @Bind(R.id.tv_tweet_time)
    TextView      mTvTweetTime;
    @Bind(R.id.tv_tweet_comment_count)
    TextView      mTvTweetCommentCount;

    @Override
    public View createView() {
        return View.inflate(AppContext.context, R.layout.item_tweet_new, null);

    }

    @Override
    public void bindView(ViewGroup parent, final Tweet tweet) {

        GlideUtils.loadCircleImage(parent.getContext(), tweet.getPortrait(),
                mIvTweetFace);

        //点击头像图片后跳转到该用户中心界面
        mIvTweetFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setId(tweet.getAuthorid());
                user.setName(tweet.getAuthor());

                Intent intent = new Intent(AppContext.context, UserHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Constant.USER_ID, user);
                AppContext.context().startActivity(intent);
            }
        });

        mTvTweetName.setText(tweet.getAuthor());

        //设置内容中的富文本
        if (!TextUtils.isEmpty(tweet.getBody())) {
            String content = tweet.getBody().replaceAll("[\n\\s]+", " ");
            mTvTweetBody.setText(TweetParser.getInstance().parse(parent.getContext(), content));
            mTvTweetBody.setMovementMethod(LinkMovementMethod.getInstance());
            mTvTweetBody.setFocusable(false);
            mTvTweetBody.setDispatchToParent(true);
            mTvTweetBody.setLongClickable(false);
        }

        //赞.直接调用bean里面已经封装好的方法
//        tweet.setLikeUsers(parent.getContext(), mTvTweetLike, true);

        if (TextUtils.isEmpty(tweet.getImgBig()) && TextUtils.isEmpty(tweet.getImgSmall())) {
            mIvTweetImage.setVisibility(View.GONE);
        } else {
            mIvTweetImage.setVisibility(View.VISIBLE);
            Glide.with(AppContext.context).load(tweet.getImgBig())
                    .into(mIvTweetImage);
            //使图片可以跳转到缩放界面
            mIvTweetImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AppContext.context, PhotoActivity
                            .class);
                    intent.putExtra(Constant.PICTURE, tweet.getImgBig());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    AppContext.context().startActivity(intent);
                }
            });
        }


        mTvTweetTime.setText(StringUtils.friendly_time(tweet.getPubDate()));
        mTvTweetCommentCount.setText(tweet.getCommentCount() + "");
    }

}
