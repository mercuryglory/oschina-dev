package org.mercury.oschina.explorer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.explorer.bean.Active;
import org.mercury.oschina.explorer.ui.activity.ImageBigActivity;
import org.mercury.oschina.explorer.util.DisplayRules;
import org.mercury.oschina.explorer.util.Fileds;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @创建者 Mercury
 * @创建时间 2016/8/14 22:00
 * @描述 ${TODO}
 */
public class FriendAdapter extends BaseAdapter {
    List<Active> mList = new ArrayList<>();
    private final static String AT_HOST_PRE = "http://my.oschina.net";
    private final static String MAIN_HOST = "http://www.oschina.net";
    private Activity mActivity;

    public FriendAdapter(List<Active> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Active getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(mActivity);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bindView(getItem(position));

        return holder.getView();
    }

    public void setContext(Activity activity) {

        mActivity = activity;
    }


    static class ViewHolder {
        @Bind(R.id.tv_more_huifu)
        TextView mTvHuiFu;
        @Bind(R.id.tv_more_type)
        TextView mTvMoreType;
        @Bind(R.id.iv_iamge)
        ImageView mIvIamge;
        @Bind(R.id.iv_friend_q_icon)
        SimpleDraweeView mIvFriendQIcon;
        @Bind(R.id.tv_more_name)
        TextView mTvMoreName;
        @Bind(R.id.tv_more_state)
        TextView mTvMoreState;
        @Bind(R.id.ll)
        LinearLayout mLl;
        @Bind(R.id.tv_more_time)
        TextView mTvMoreTime;
        @Bind(R.id.iv_friend_pl)
        TextView mIvFriendPl;
        @Bind(R.id.tv_more_phone_name)
        TextView mTvMorePhoneName;
        @Bind(R.id.tv_mpre_pl)
        TextView mTvMprePl;
        View view;
        private Activity mActivity;

        public View getView() {
            return view;
        }

        private void bindView(final Active active) {

           /* mIvFriendQIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, UserHomeActivity.class);
                    intent.putExtra(Constant.USER_ID,active);
                    mActivity.startActivity(intent);
                }
            });*/
            mTvMoreName.setText(active.getAuthor());
            Uri uri = UriUtil.parseUriOrNull(active.getPortrait());
            mIvFriendQIcon.setImageURI(uri);
            mTvMoreTime.setText(active.getPubDate());
            // mIvFriendPl.setText(HTMLUtil.delHTMLTag(active.getMessage()));
            mTvMprePl.setText(active.getAppClient() + "");
            mTvMoreState.setText(active.getObjectTitle());
            mIvIamge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity,ImageBigActivity.class);
                    intent.putExtra(Fileds.IMAGE_URL,active.getTweetimage());
                    mActivity.startActivity(intent);
                }
            });
            //如果消息 为空 就隐藏
            if (TextUtils.isEmpty(active.getMessage())) {
                mIvFriendPl.setVisibility(View.GONE);
            } else {
                mIvFriendPl.setMovementMethod(LinkMovementMethod.getInstance());
                mIvFriendPl.setFocusable(false);
                //mIvFriendPl.
                mIvFriendPl.setVisibility(View.VISIBLE);
                String message = modifyPath(active.getMessage());
                Log.i("====", message);
                setTvTextSpanned(R.id.iv_friend_pl, message);
            }
            //设置表情

            mTvMorePhoneName.setVisibility(View.VISIBLE);
            mTvMorePhoneName.setText("Iphone");
            //显示图片
            Uri uri1 = UriUtil.parseUriOrNull(active.getTweetimage());
            mIvIamge.setMaxHeight(200);
            //  holder.mTvMprePl.setBackgroundResource(R.drawable.day_comment_reply_container_bg);
            Glide.with(AppContext.context()) // 指定Context
                    .load(uri1)// 指定图片的URL
                    .override(320, 200)
                    .fitCenter()
                    .into(mIvIamge);//指定显示图片的
            // String htmlInfo = HTMLUtil.delHTMLTag();
            switch (active.getCatalog()) {
                case 3:
                    mTvMorePhoneName.setVisibility(View.VISIBLE);
                    mTvMorePhoneName.setText("Android");
                    break;
                case 0:
                    mTvMorePhoneName.setVisibility(View.VISIBLE);
                    mTvMorePhoneName.setText("Iphone");
                    break;
            }
            switch (active.getObjectType()) {
                case 100:
                    mTvMoreType.setText("更新了状态");
                    break;
                case 3:
                    mTvMoreType.setText("发表了博客");

                    break;
                case 101:
                    mTvMoreType.setText("回复了状态");
                    mTvHuiFu.setVisibility(View.VISIBLE);
                    mTvHuiFu.setText(active.getObjectReply().getObjectName() + ":" + active.getObjectReply().getObjectBody());
                    break;
                case 5:
                    mTvMoreType.setText("分享了一段代码");
                    //  mTvMoreState.setText(active.getObjectTitle());
                    break;
            }
        }

        ViewHolder(Activity activity) {
            mActivity = activity;
            //填充view
            view = View.inflate(mActivity, R.layout.friend_item, null);
            //绑定
            view.setTag(this);
            ButterKnife.bind(this, view);
        }

        /**
         * 设置TextView文字,html数据,显示富文本
         *
         * @param id 控件Id
         * @param s  要设置的文字
         * @return 返回对象本身
         */
        public ViewHolder setTvTextSpanned(int id, String s) {
            TextView view = creatView(id);
            Spanned spanned = Html.fromHtml(s);

            String s1 = spanned.toString();

            SpannableString spannableString = new SpannableString(spanned);

            int start;
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
                spannableString.setSpan(span, first.get(i), last.get(i) + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

            }


            view.setText(spannableString);
            return this;
        }

        private TextView creatView(int id) {
            return (TextView) view.findViewById(id);
        }


        private String modifyPath(String message) {
            message = message.replaceAll("(<a[^>]+href=\")/([\\S]+)\"", "$1"
                    + AT_HOST_PRE + "/$2\"");
            message = message.replaceAll(
                    "(<a[^>]+href=\")http://m.oschina.net([\\S]+)\"", "$1"
                            + MAIN_HOST + "$2\"");
            return message;
        }
    }
}
