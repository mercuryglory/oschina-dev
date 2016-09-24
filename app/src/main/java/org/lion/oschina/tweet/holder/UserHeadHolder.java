package org.lion.oschina.tweet.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.lion.oschina.application.MyApplication;
import org.lion.oschina.tweet.activity.PhotoActivity;
import org.lion.oschina.tweet.bean.User;
import org.lion.oschina.tweet.util.Constant;
import org.lion.oschina.tweet.util.GlideUtils;
import org.lion.oschina.tweet.util.ToastUtil;
import org.lion.oschina.tweet.widget.CustomDialog;
import org.lion.oschina.ui.activity.BlogActivity;
import org.lion.oschina.utils.StringUtils;

import butterknife.Bind;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/22
 * 描述:      ${TODO}
 */
public class UserHeadHolder extends BasicHolder<User> {
    @Bind(R.id.iv_userhome_portrait)
    ImageView mIvUserhomePortrait;
    @Bind(R.id.tv_userhome_name)
    TextView  mTvUserhomeName;
    @Bind(R.id.tv_userhome_score)
    TextView  mTvUserhomeScore;
    @Bind(R.id.tv_userhome_follow)
    TextView  mTvUserhomeFollow;
    @Bind(R.id.tv_userhome_fans)
    TextView  mTvUserhomeFans;
    @Bind(R.id.tv_userhome_latesttime)
    TextView  mTvUserhomeLatesttime;
    @Bind(R.id.btn_userhome_blog)
    Button    mBtnUserhomeBlog;
    @Bind(R.id.btn_userhome_data)
    Button    mBtnUserhomeData;

    private Context context;

    @Override
    public View createView() {
        return View.inflate(MyApplication.context, R.layout.head_user_home, null);
    }


    @Override
    public void bindView(final ViewGroup parent, final User user) {
        GlideUtils.loadCircleImage(parent.getContext(), user.getPortrait(), mIvUserhomePortrait);
        //点击用户头像可以放大
        mIvUserhomePortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.context, PhotoActivity.class);
                intent.putExtra(Constant.PICTURE, user.getPortrait());
                parent.getContext().startActivity(intent);
            }
        });
        mTvUserhomeName.setText(user.getName());
        mTvUserhomeScore.setText(user.getScore() + "");
        mTvUserhomeFollow.setText(user.getFollowers() + "");
        mTvUserhomeFans.setText(user.getFans() + "");
        mTvUserhomeLatesttime.setText(StringUtils.friendly_time(user.getLatestonline()));

        mBtnUserhomeBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("按钮1被点击了");
                Intent intent = new Intent(MyApplication.context, BlogActivity.class);
                parent.getContext().startActivity(intent);
            }
        });

        mBtnUserhomeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("按钮2被点击了");
                CustomDialog customDialog = new CustomDialog(context, R.layout
                        .customdialog);
                customDialog.show();
                View view = customDialog.getCustomView();
                //                ViewHolder holder = new ViewHolder(R.layout.customdialog);
                //                final DialogPlus customDialog = DialogPlus.newDialog(parent.getContext())
                //                        .setContentHolder(holder).setGravity(Gravity.CENTER)
                //                        .setExpanded(true, 500)  // This will enable the expand feature, (similar
                //                        // to android L share dialog)
                //                        .create();

                TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
                TextView tv_district = (TextView) view.findViewById(R.id.tv_district);
                TextView tv_platform = (TextView) view.findViewById(R.id.tv_platform);
                TextView tv_hobby = (TextView) view.findViewById(R.id.tv_hobby);

                tv_time.setText(user.getJointime());
                tv_district.setText(user.getFrom());
                tv_platform.setText(user.getDevplatform());
                tv_hobby.setText(user.getExpertise());

            }
        });
    }
}
