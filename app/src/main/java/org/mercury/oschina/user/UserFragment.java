package org.mercury.oschina.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseFragment;
import org.mercury.oschina.emoji.UiUtil;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.main.activity.UserSingleInfoActivity;
import org.mercury.oschina.tweet.TweetListFragment;
import org.mercury.oschina.tweet.bean.User;
import org.mercury.oschina.utils.AccessTokenHelper;
import org.mercury.oschina.widget.CodeDialog;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mercury on 2016-08-14 19:33:46.
 * 个人中心
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {

    private static final int QR_WIDTH  = 300;
    private static final int QR_HEIGHT = 300;


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
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<User> myInfo = retrofitCall.getMyInfo();
        myInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User body = response.body();
                if (body != null) {
                    updateMyInfo(body);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showToast(getResources().getString(R.string.state_network_error));

            }
        });
    }

    private void updateMyInfo(User body) {
        tvUserName.setText(body.getName());
        getImgLoader().load(body.getPortrait()).into(ivUserPic);
        if (body.getGender() == 1) {
            ivUserGender.setImageResource(R.mipmap.userinfo_icon_male);
        } else if (body.getGender() == 2) {
            ivUserGender.setImageResource(R.mipmap.userinfo_icon_female);
        }
    }

    @OnClick({R.id.iv_setting, R.id.iv_user_qrcode, R.id.iv_user_pic, R.id.tv_user_tweet, R.id
            .tv_user_favorite, R.id.tv_user_like, R.id.tv_user_fans,
            R.id.ll_my_msg, R.id.ll_my_blog, R.id.ll_my_event})
    @Override
    public void onClick(View v) {

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
                Bundle bundle = new Bundle();
                bundle.putInt(TweetListFragment.REQUEST_CATALOG, AccessTokenHelper.getUserId());
                UserSingleInfoActivity.show(getContext(), FragmentInfo.MY_TWEET, bundle);
                break;
            //我的收藏
            case R.id.tv_user_favorite:
                break;
            //我的关注
            case R.id.tv_user_like:
                break;
            //我的粉丝
            case R.id.tv_user_fans:
                break;
            //            case R.id.ll_user_score:
            //                break;
            //            case R.id.ll_user_fans:
            //                Intent intent = new Intent(getActivity(), ThierActivity.class);
            //                intent.putExtra(Fields.FANS, "fans");
            //                startActivity(intent);
            //                break;
            //            case ll_user_care:
            //                Intent intent1 = new Intent(getActivity(), ThierActivity.class);
            //                intent1.putExtra(Fields.CARE, "care");
            //                startActivity(intent1);
            //
            //                break;
            //            case ll_user_collect:
            //
            //                startActivity(new Intent(getActivity(), CollectionActivity.class));
            //                break;

            //更换头像或查看大头像
            case R.id.iv_user_pic:

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



}

