package org.mercury.oschina.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mercury.oschina.Constant;
import org.mercury.oschina.R;
import org.mercury.oschina.base.BasePresenterFragment;
import org.mercury.oschina.emoji.UiUtil;
import org.mercury.oschina.main.activity.UserSingleInfoActivity;
import org.mercury.oschina.tweet.TweetListFragment;
import org.mercury.oschina.tweet.activity.ImageGalleryActivity;
import org.mercury.oschina.tweet.bean.User;
import org.mercury.oschina.user.contractor.UserContract;
import org.mercury.oschina.user.contractor.UserPresenter;
import org.mercury.oschina.utils.AccessTokenHelper;
import org.mercury.oschina.utils.DialogHelper;
import org.mercury.oschina.widget.CodeDialog;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mercury on 2016-08-14 19:33:46.
 * 个人中心
 */
public class UserFragment extends BasePresenterFragment<UserContract.Presenter,User> implements View.OnClickListener,UserContract.View {

    @Bind(R.id.iv_setting)
    ImageView       ivSetting;
    @Bind(R.id.iv_user_qrcode)
    ImageView       ivUserQrcode;
    @Bind(R.id.rl_top)
    RelativeLayout  rlTop;
    @Bind(R.id.iv_user_pic)
    CircleImageView ivUserPic;
    @Bind(R.id.iv_user_gender)
    ImageView       ivUserGender;
    @Bind(R.id.rl_user_avatar)
    RelativeLayout  rlUserAvatar;
    @Bind(R.id.tv_user_name)
    TextView        tvUserName;
    @Bind(R.id.rl_user)
    RelativeLayout  rlUser;
    @Bind(R.id.tv_user_tweet)
    TextView        tvUserTweet;
    @Bind(R.id.tv_user_favorite)
    TextView        tvUserFavorite;
    @Bind(R.id.tv_user_like)
    TextView        tvUserLike;
    @Bind(R.id.tv_user_fans)
    TextView        tvUserFans;
    @Bind(R.id.ll_my_msg)
    LinearLayout    llMyMsg;
    @Bind(R.id.ll_my_blog)
    LinearLayout    llMyBlog;
    @Bind(R.id.ll_my_event)
    LinearLayout    llMyEvent;

    private String portraitUrl = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_myinfo;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        //由于采用了沉浸式标题栏（其实相当于将整个布局向上填充遮盖了系统标题栏的高度，导致图标部分被遮挡
        // 在系统标题栏之内），所以人为代码设置paddinttop，和屏幕顶部拉开距离
        rlUser.setPadding(rlUser.getLeft(), UiUtil.getStatusBarHeight(getActivity()), rlUser
                .getRight(), rlUser.getBottom());
        mPresenter = new UserPresenter(this);
        mPresenter.onRefreshing();
    }


    @OnClick({R.id.iv_setting, R.id.iv_user_qrcode, R.id.iv_user_pic, R.id.tv_user_tweet, R.id
            .tv_user_favorite, R.id.tv_user_like, R.id.tv_user_fans,
            R.id.ll_my_msg, R.id.ll_my_blog, R.id.ll_my_event})
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            //开启设置
            case R.id.iv_setting:

                break;

            //弹出二维码
            case R.id.iv_user_qrcode:
                CodeDialog dialog = new CodeDialog(getContext());
                dialog.show();

                break;
            //我的动弹
            case R.id.tv_user_tweet:
                bundle.putInt(TweetListFragment.REQUEST_CATALOG, AccessTokenHelper.getUserId());
                UserSingleInfoActivity.show(getContext(), FragmentInfo.MY_TWEET, bundle);
                break;
            //我的收藏
            case R.id.tv_user_favorite:
                UserSingleInfoActivity.show(getContext(), FragmentInfo.MY_FAVORITE, null);
                break;
            //我的关注
            case R.id.tv_user_like:
                bundle.putInt(Constant.REQUEST_CATALOG, FriendListFragment.RELATION_FOLLOW);
                UserSingleInfoActivity.show(getContext(), FragmentInfo.MY_FRIEND, bundle);
                break;
            //我的粉丝
            case R.id.tv_user_fans:
                bundle.putInt(Constant.REQUEST_CATALOG, FriendListFragment.RELATION_FANS);
                UserSingleInfoActivity.show(getContext(), FragmentInfo.MY_FRIEND, bundle);
                break;

            //更换头像或查看大头像
            case R.id.iv_user_pic:
                updateOrViewIcon();
                break;
            //我的消息
            case R.id.ll_my_msg:
                UserSingleInfoActivity.show(getContext(), FragmentInfo.MY_MESSAGE, null);
                break;
            //我的博客
            case R.id.ll_my_blog:
                UserSingleInfoActivity.show(getContext(), FragmentInfo.MY_BLOG, null);
                break;
            //我的活动
            case R.id.ll_my_event:
                UserSingleInfoActivity.show(getContext(), FragmentInfo.MY_ACTIVE, null);
                break;

        }
    }

    private void updateOrViewIcon() {
        DialogHelper.getSelectDialog(getActivity(), "选择操作",
                getResources().getStringArray(R.array.icon_operation), "取消",
                new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //更换头像
                    mPresenter.portraitUpdate();
                } else if (which == 1) {
                    //查看头像
                    ImageGalleryActivity.show(getActivity(),new String[]{portraitUrl});
                }
            }
        }).show();
    }


    @Override
    public void refreshSuccess(User user) {
        portraitUrl = user.getPortrait();
        tvUserName.setText(user.getName());
        getImgLoader().load(user.getPortrait()).into(ivUserPic);
        if (user.getGender() == 1) {
            ivUserGender.setImageResource(R.mipmap.userinfo_icon_male);
        } else if (user.getGender() == 2) {
            ivUserGender.setImageResource(R.mipmap.userinfo_icon_female);
        }
    }

    @Override
    public void updateIcon() {

    }
}

