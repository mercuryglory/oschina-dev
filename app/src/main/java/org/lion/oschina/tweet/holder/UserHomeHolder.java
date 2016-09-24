package org.lion.oschina.tweet.holder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.lion.oschina.application.MyApplication;
import org.lion.oschina.bean.Active;
import org.lion.oschina.tweet.emoji.InputHelper;
import org.lion.oschina.tweet.extra.MyURLSpan;
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
public class UserHomeHolder extends BasicHolder<Active> {
    private StringBuilder sb;
    private  Spanned       spanned;
    private  Intent        intent;

    @Bind(R.id.iv_userhome_face)
    ImageView      mIvUserhomeFace;
    @Bind(R.id.tv_userhome_name)
    TextView       mTvUserhomeName;
    @Bind(R.id.tv_userhome_time)
    TextView       mTvUserhomeTime;
    @Bind(R.id.tv_userhome_state)
    TweetTextView  mTvUserhomeState;
    @Bind(R.id.tv_userhome_body)
    TweetTextView  mTvUserhomeBody;
    @Bind(R.id.tv_userhome_reply)
    TweetTextView  mTvUserhomeReply;
    @Bind(R.id.rl_active_body)
    RelativeLayout mRlActiveBody;
    @Bind(R.id.tv_userhome_platform)
    TextView       mTvUserhomePlatform;
    @Bind(R.id.tv_userhome_comment_count)
    TextView       mTvUserhomeCommentCount;

    @Override
    public View createView() {
        return View.inflate(MyApplication.context, R.layout.item_userhome_active, null);
    }

    @Override
    public void bindView(ViewGroup parent, Active active) {
        GlideUtils.loadCircleImage(parent.getContext(), active.getPortrait(), mIvUserhomeFace);

        mTvUserhomeName.setText(active.getAuthor());
        mTvUserhomeTime.setText(StringUtils.friendly_time(active.getPubDate()));

        //动态列表的内容设置
        mTvUserhomeBody.setMovementMethod(MyLinkMovementMethod.a());
        mTvUserhomeBody.setFocusable(false);
        mTvUserhomeBody.setDispatchToParent(true);
        mTvUserhomeBody.setLongClickable(false);

        String body = active.getMessage().replaceAll("[\\s\t\n]", "");
        Spanned span = Html.fromHtml(body);
        span = InputHelper.displayEmoji(MyApplication.context().getResources(), span);
        mTvUserhomeBody.setText(span);
        MyURLSpan.parseLinkText(mTvUserhomeBody, span);

        switch (active.getObjectType()) {
            case 100:
                mTvUserhomeState.setText("更新了动态");
                mRlActiveBody.setVisibility(View.GONE);
                break;
            case 101:
                mTvUserhomeState.setText("回复了动态:");
                mRlActiveBody.setVisibility(View.VISIBLE);
                mTvUserhomeReply.setText(active.getObjectReply().getObjectBody());
                break;
            case 18:
                sb = new StringBuilder("在博客发表评论");
                sb.insert(3, b(active.getObjectTitle()));
                spanned = Html.fromHtml(sb.toString());
                mTvUserhomeState.setText(spanned);
                mRlActiveBody.setVisibility(View.GONE);
                break;

            case 17:
                sb = new StringBuilder("在对回帖发表评论");
                sb.insert(1, r(active.getObjectTitle()));
                spanned = Html.fromHtml(sb.toString());
                mTvUserhomeState.setText(spanned);

                if (active.getObjectReply() != null) {
                    mRlActiveBody.setVisibility(View.VISIBLE);
                    mTvUserhomeReply.setMovementMethod(MyLinkMovementMethod.a());
                    mTvUserhomeReply.setFocusable(false);
                    mTvUserhomeReply.setDispatchToParent(true);
                    mTvUserhomeReply.setLongClickable(false);
                    String reply = active.getObjectReply().objectBody.replaceAll
                            ("[\\s\t\n]", "");
                    Spanned spanned = Html.fromHtml(reply);
                    spanned = InputHelper.displayEmoji(MyApplication.context.getResources(),
                            spanned);
                    mTvUserhomeReply.setText(spanned);
                } else {
                    mRlActiveBody.setVisibility(View.GONE);
                }
                break;

            case 16:
                sb = new StringBuilder("在新闻发表评论");
                sb.insert(3, g(active.getObjectTitle()));
                spanned = Html.fromHtml(sb.toString());
                mTvUserhomeState.setText(spanned);
                mRlActiveBody.setVisibility(View.GONE);
                break;

            default:
                break;

        }


        switch (active.getAppClient()) {
            case 1:
                mTvUserhomePlatform.setVisibility(View.GONE);
                break;
            case 3:
                mTvUserhomePlatform.setVisibility(View.VISIBLE);
                mTvUserhomePlatform.setText("Android");
                break;
            case 4:
                mTvUserhomePlatform.setVisibility(View.VISIBLE);
                mTvUserhomePlatform.setText("iPhone");
                break;
            default:
                break;

        }
        mTvUserhomeCommentCount.setText(active.getCommentCount() + "");
    }

    @NonNull
    private String b(String arg) {
        return String.format("<font color='#00AAFF' size='20'>%s</font>", arg);
    }

    @NonNull
    private String r(String arg) {
        return String.format("<font color='#FF8CB3' size='20'>%s</font>", arg);
    }

    @NonNull
    private String g(String arg) {
        return String.format("<font color='#55FFAA' size='20'>%s</font>", arg);
    }
}
