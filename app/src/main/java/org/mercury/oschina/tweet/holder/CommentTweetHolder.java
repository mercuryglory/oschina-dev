package org.mercury.oschina.tweet.holder;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.mercury.oschina.Constant;
import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.tweet.activity.UserHomeActivity;
import org.mercury.oschina.tweet.bean.Comment;
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
        return View.inflate(AppContext.context, R.layout.item_tweet_comment, null);
    }

    @Override
    public void bindView(ViewGroup parent, final Comment comment) {
        GlideUtils.loadCircleImage(parent.getContext(), comment.getCommentPortrait(), mIvTweetFace);
        //头像跳转
        mIvTweetFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setId(comment.getCommentAuthorId());
                user.setName(comment.getCommentAuthor());

                Intent intent = new Intent(AppContext.context, UserHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Constant.USER_ID, user);
                AppContext.context().startActivity(intent);
            }
        });


        mTvTweetName.setText(comment.getCommentAuthor());

        //设置富文本
        if (!TextUtils.isEmpty(comment.getContent())) {
            String content = comment.getContent().replaceAll("[\n\\s]+", " ");
            mTvTweetBody.setText(TweetParser.getInstance().parse(parent.getContext(), content));
            mTvTweetBody.setMovementMethod(LinkMovementMethod.getInstance());
            mTvTweetBody.setFocusable(false);
            mTvTweetBody.setDispatchToParent(true);
            mTvTweetBody.setLongClickable(false);
        }

        switch (comment.getClient_type()) {
            case 3:
                mTvTweetPlatform.setText("Android");
                break;
            case 4:
                mTvTweetPlatform.setText("iPhone");
                break;
            case 5:
                mTvTweetPlatform.setText("WP");
                break;
            default:
                break;

        }
        mTvTweetTime.setText(StringUtils.friendly_time(comment.getPubDate()));
    }
}
