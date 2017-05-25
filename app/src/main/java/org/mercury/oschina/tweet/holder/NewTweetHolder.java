package org.mercury.oschina.tweet.holder;

import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.tweet.activity.PhotoActivity;
import org.mercury.oschina.tweet.activity.UserHomeActivity;
import org.mercury.oschina.tweet.bean.Tweet;
import org.mercury.oschina.tweet.bean.User;
import org.mercury.oschina.tweet.emoji.InputHelper;
import org.mercury.oschina.tweet.extra.MyURLSpan;
import org.mercury.oschina.tweet.util.Constant;
import org.mercury.oschina.tweet.util.GlideUtils;
import org.mercury.oschina.tweet.util.ToastUtil;
import org.mercury.oschina.tweet.widget.MyLinkMovementMethod;
import org.mercury.oschina.tweet.widget.TweetTextView;
import org.mercury.oschina.ui.activity.MainActivity;
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
    @Bind(R.id.tv_tweet_like)
    TweetTextView mTvTweetLike;
    @Bind(R.id.tv_tweet_time)
    TextView      mTvTweetTime;
    @Bind(R.id.tv_tweet_platform)
    TextView      mTvTweetPlatform;
    @Bind(R.id.iv_like_state)
    ImageView     mIvLikeState;
    @Bind(R.id.tv_tweet_like_count)
    TextView      mTvTweetLikeCount;
    @Bind(R.id.tv_tweet_comment_count)
    TextView      mTvTweetCommentCount;
    @Bind(R.id.ll_info)
    LinearLayout  mLlInfo;

    @Override
    public View createView() {
        return View.inflate(MainActivity.mMainActivity, R.layout.item_tweet_new, null);

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
        mTvTweetBody.setMovementMethod(MyLinkMovementMethod.a());
        mTvTweetBody.setFocusable(false);
        mTvTweetBody.setDispatchToParent(true);
        mTvTweetBody.setLongClickable(false);
        Spanned span = Html.fromHtml(TweetTextView.modifyPath(tweet.getBody()));
        span = InputHelper.displayEmoji(AppContext.context().getResources(), span);
        mTvTweetBody.setText(span);
        System.out.println("span:" + span);
        MyURLSpan.parseLinkText(mTvTweetBody, span);

        //赞.直接调用bean里面已经封装好的方法
        tweet.setLikeUsers(parent.getContext(), mTvTweetLike, true);
        //        List<User> likeUser = tweet.getLikeUser();
        //        String str = "";
        //        if (likeUser != null && likeUser.size() > 0) {
        //            vh.mTvTweetLike.setVisibility(View.VISIBLE);
        //            for (int i = 0; i < likeUser.size(); i++) {
        //                if (i == 4) {
        //                    break;
        //                }
        //                str = str + g(likeUser.get(i).getName()) + ("、");
        //            }
        //            str = str.substring(0, str.length() - 1);
        //            if (likeUser.size() > 4) {
        //                str = str + "等" + g(tweet.getLikeCount() + "人")+"觉得很赞";
        //            } else {
        //                str = str + "觉得很赞";
        //            }
        //
        //            Spanned spanned = Html.fromHtml(str);
        //            vh.mTvTweetLike.setText(spanned);
        //        } else {
        //            vh.mTvTweetLike.setVisibility(View.GONE);
        //        }

        if (tweet.getImgBig().equals("") && tweet.getImgSmall().equals("")) {
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
        mIvLikeState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("先登录才能点赞哟");
            }
        });
        mTvTweetTime.setText(StringUtils.friendly_time(tweet.getPubDate()));
        mTvTweetLikeCount.setText(tweet.getLikeCount() + "");
        mTvTweetCommentCount.setText(tweet.getCommentCount() + "");
    }

}
