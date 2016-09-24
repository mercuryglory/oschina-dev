package org.lion.oschina.holder;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.lion.oschina.bean.Active;
import org.lion.oschina.global.OsChinaApp;
import org.lion.oschina.utils.DisplayRules;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/18 14:27
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class MeViewHolder extends BasicHolder<Active> {
    @Bind(R.id.iv_me1_pic)
    ImageView mIvMe1Pic;
    @Bind(R.id.tv_me1_name)
    TextView  mTvMe1Name;
    @Bind(R.id.tv_comment)
    TextView  mTvComment;
    @Bind(R.id.tv_me1_content)
    TextView  mTvMe1Content;
    @Bind(R.id.tv_me1_time)
    TextView  mTvMe1Time;
    private Context mContext;

    public MeViewHolder(Activity activity) {
        super(activity);
    }


    @Override
    protected View createView() {
        System.out.println("zjhelizoulllllll");

        return View.inflate(mActivity, R.layout.item_me, null);
    }

    @Override
    public void bindView(Active appInfo) {
        ImageLoader.getInstance().displayImage(appInfo.getPortrait(), mIvMe1Pic, mOptions);
        mTvMe1Name.setText(appInfo.getAuthor());
        mTvMe1Time.setText(appInfo.getPubDate());
        mTvMe1Content.setMovementMethod(LinkMovementMethod.getInstance());
        mTvMe1Content.setFocusable(false);

        setContent(mTvMe1Content, appInfo.getMessage());
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
            Drawable drawable = OsChinaApp.context().getResources().getDrawable(resId);
            drawable.setBounds(0, 0, 20, 20);
            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            System.out.println(first.get(i));
            spannableString.setSpan(span, first.get(i), last.get(i)+1 , Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        }


        view.setText(spannableString);

    }


}
