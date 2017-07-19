package org.mercury.oschina.holder;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.bean.Active;
import org.mercury.oschina.utils.DisplayRules;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/18 12:10
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class CommentViewHolder extends BasicHolder<Active> {
    @Bind(R.id.iv_comment_pic)
    ImageView    mIvCommentPic;
    @Bind(R.id.tv_comment_name)
    TextView     mTvCommentName;
    @Bind(R.id.tv_comment)
    TextView     mTvComment;
    @Bind(R.id.tv_comment_content)
    TextView     mTvCommentContent;
    @Bind(R.id.tv_comment_time)
    TextView     mTvCommentTime;
    @Bind(R.id.tv_comment_author)
    TextView     mTvCommentAuthor;
    @Bind(R.id.tv_comment_desc)
    TextView     mTvCommentDesc;
    @Bind(R.id.ll_comment_reply)
    LinearLayout mLlCommentReply;

    @Override
    protected View createView() {
        return View.inflate(AppContext.context(), R.layout.item_comment, null);
    }

    @Override
    public void bindView(Active appInfo) {
        ImageLoader.getInstance().displayImage(appInfo.getPortrait(), mIvCommentPic, mOptions);
        mTvCommentName.setText(appInfo.getAuthor());
        mTvCommentTime.setText(appInfo.getPubDate());

        mTvCommentDesc.setText(appInfo.getObjectReply().getObjectBody());
        mTvCommentAuthor.setText(appInfo.getObjectReply().getObjectName()+" : ");

        setContent(mTvCommentContent, appInfo.getMessage());
    }

    private void setContent(TextView tvCommentContent, String s) {
        TextView view = tvCommentContent;

        Spanned spanned = Html.fromHtml(s);

        String s1 = spanned.toString();

        SpannableString spannableString = new SpannableString(spanned);

        int start ;
        int end = 0;
        ArrayList<Integer> first = new ArrayList<>();
        ArrayList<Integer> last = new ArrayList<>();
        ArrayList<String> str = new ArrayList<>();
        String s2 = s1;
        String s3;
        while (s2.indexOf("[") != -1) {

            start = s1.indexOf('[', end);
            end = s1.indexOf(']', end);

            first.add(start);
            last.add(end);
            end += 1;
            s2 = s1.substring(end);
            s3 = s1.substring(start, end);
            str.add(s3);
        }


        for (int i = 0; i < str.size(); i++) {
            int resId = DisplayRules.getEmojiFromName(str.get(i)).getResId();
            Drawable drawable = AppContext.context().getResources().getDrawable(resId);
            drawable.setBounds(0, 0, 20, 20);
            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            spannableString.setSpan(span, first.get(i), last.get(i)+1 , Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        }


        view.setText(spannableString);

    }


}
