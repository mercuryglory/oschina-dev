package org.lion.oschina.tweet.holder;

import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.lion.oschina.application.MyApplication;
import org.lion.oschina.tweet.activity.UserHomeActivity;
import org.lion.oschina.tweet.bean.Comment;
import org.lion.oschina.tweet.bean.User;
import org.lion.oschina.tweet.emoji.InputHelper;
import org.lion.oschina.tweet.extra.MyURLSpan;
import org.lion.oschina.tweet.util.Constant;
import org.lion.oschina.tweet.util.GlideUtils;
import org.lion.oschina.tweet.widget.MyLinkMovementMethod;
import org.lion.oschina.tweet.widget.TweetTextView;
import org.lion.oschina.utils.StringUtils;

import butterknife.Bind;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/21
 * 描述:      ${TODO}
 */
public class CommentTweetHolder extends BasicHolder<Comment> {
    @Bind(R.id.iv_tweet_face)
    ImageView     mIvTweetFace;
    @Bind(R.id.tv_tweet_name)
    TextView      mTvTweetName;
    @Bind(R.id.tv_tweet_time)
    TextView      mTvTweetTime;
    @Bind(R.id.tv_tweet_body)
    TweetTextView mTvTweetBody;
    @Bind(R.id.tv_tweet_platform)
    TextView      mTvTweetPlatform;

    @Override
    public View createView() {
        return View.inflate(MyApplication.context, R.layout.item_tweet_comment, null);
    }

    @Override
    public void bindView(ViewGroup parent, final Comment comment) {
        GlideUtils.loadCircleImage(parent.getContext(), comment.getPortrait(), mIvTweetFace);
        //头像跳转
        mIvTweetFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setId(comment.getAuthorId());
                user.setName(comment.getAuthor());

                Intent intent = new Intent(MyApplication.context, UserHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Constant.USER_ID, user);
                MyApplication.context().startActivity(intent);
            }
        });


        mTvTweetName.setText(comment.getAuthor());

        //设置富文本
        mTvTweetBody.setMovementMethod(MyLinkMovementMethod.a());
        mTvTweetBody.setFocusable(false);
        mTvTweetBody.setDispatchToParent(true);
        mTvTweetBody.setLongClickable(false);
        Spanned span = Html.fromHtml(TweetTextView.modifyPath(comment.getContent()));
        span = InputHelper.displayEmoji(MyApplication.context().getResources(), span);
        mTvTweetBody.setText(span);
        MyURLSpan.parseLinkText(mTvTweetBody, span);

        switch (comment.getAppClient()) {
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
        mTvTweetTime.setText(StringUtils.friendly_time(comment.getPubDate()));
    }
}
