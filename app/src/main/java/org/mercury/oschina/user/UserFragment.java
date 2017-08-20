package org.mercury.oschina.user;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.mercury.oschina.AppContext;
import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseFragment;
import org.mercury.oschina.emoji.UiUtil;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.main.activity.UserSingleInfoActivity;
import org.mercury.oschina.tweet.bean.User;
import org.mercury.oschina.tweet.util.ToastUtil;

import java.util.Hashtable;

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

    private static final int QR_WIDTH  = 100;
    private static final int QR_HEIGHT = 100;


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

    @OnClick({R.id.iv_setting, R.id.iv_user_qrcode, R.id.iv_user_pic, R.id.tv_user_tweet, R.id.tv_user_favorite, R.id.tv_user_like, R.id.tv_user_fans,
            R.id.ll_my_msg, R.id.ll_my_blog, R.id.ll_my_event})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //开启设置
            case R.id.iv_setting:

                break;

            //弹出二维码
            case R.id.iv_user_qrcode:
                showQRCodeDialog();

                break;
            //我的动弹
            case R.id.tv_user_tweet:
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
                UserSingleInfoActivity.show(getContext(), FragmentInfo.MY_MESSAGE);
                break;
            //我的博客
            case R.id.ll_my_blog:
                UserSingleInfoActivity.show(getContext(), FragmentInfo.MY_BLOG);
                break;
            //我的活动
            case R.id.ll_my_event:
                break;

        }
    }


    private void showQRCodeDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = View.inflate(AppContext.context,
                R.layout.dialog_code, null);
        builder.setView(view);

        // 显示出来
        final AlertDialog dialog = builder.show();

        // 查找控件
        ImageView iv_code = (ImageView) view.findViewById(R.id.iv_code);
        createQRImage("http://www.cnblogs.com/mythou/p/3280023.html", iv_code);
        iv_code.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastUtil.showToast("已保存到本地");
                dialog.dismiss();
                return true;
            }
        });

    }

    public void createQRImage(String url, ImageView iv_code) {
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH,
                    QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            iv_code.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


}

