package org.mercury.oschina.main.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.base.BaseFragment;
import org.mercury.oschina.bean.MyInformation;
import org.mercury.oschina.bean.User;
import org.mercury.oschina.main.activity.BlogActivity;
import org.mercury.oschina.main.activity.CollectionActivity;
import org.mercury.oschina.main.activity.MsgActivity;
import org.mercury.oschina.main.activity.ThierActivity;
import org.mercury.oschina.main.activity.UserDetailsActivity;
import org.mercury.oschina.tweet.util.ToastUtil;
import org.mercury.oschina.utils.Fields;
import org.mercury.oschina.utils.OschinaUri;
import org.mercury.oschina.utils.Utils;
import org.mercury.oschina.utils.XmlUtils;

import java.util.Hashtable;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

import static org.mercury.oschina.R.id.iv_user_pic;
import static org.mercury.oschina.R.id.iv_user_qr_code;
import static org.mercury.oschina.R.id.ll_user_blog;
import static org.mercury.oschina.R.id.ll_user_care;
import static org.mercury.oschina.R.id.ll_user_collect;
import static org.mercury.oschina.R.id.ll_user_msg;

/**
 * Created by Mercury on 2016-08-14 19:33:46.
 * 个人中心
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {

    private static final int QR_WIDTH  = 100;
    private static final int QR_HEIGHT = 100;

    @Bind(iv_user_pic)
    ImageView      mIvUserPic;
    @Bind(R.id.iv_user_gender)
    ImageView      mIvUserGender;
    @Bind(iv_user_qr_code)
    ImageView      mIvUserQrCode;
    @Bind(R.id.tv_user_name)
    TextView       mTvUserName;
    @Bind(R.id.tv_user_score)
    TextView       mTvUserScore;
    @Bind(R.id.ll_user_score)
    LinearLayout   mLlUserScore;
    @Bind(R.id.tv_user_collect)
    TextView       mTvUserCollect;
    @Bind(ll_user_collect)
    LinearLayout   mLlUserCollect;
    @Bind(R.id.tv_user_stare)
    TextView       mTvUserStare;
    @Bind(ll_user_care)
    LinearLayout   mLlUserCare;
    @Bind(R.id.tv_user_follower)
    TextView       mTvUserFollower;
    @Bind(R.id.ll_user_fans)
    LinearLayout   mLlUserFans;
    @Bind(R.id.rl_user)
    RelativeLayout mRlUser;
    @Bind(ll_user_msg)
    LinearLayout   mLlUserMsg;
    @Bind(ll_user_blog)
    LinearLayout   mLlUserBlog;
    @Bind(R.id.ll_user_note)
    LinearLayout   mLlUserNote;
    @Bind(R.id.ll_user_team)
    LinearLayout   mLlUserTeam;

    public  DisplayImageOptions mOptions;
    private User                mUser;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initData() {
        super.initData();
        mOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)// 会识别图片的方向信息
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        OkHttpUtils.get().url(OschinaUri.ME_URL)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int i) {

                        Toast.makeText(getActivity(), "请求失败!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        //      Toast.makeText(getActivity(), "请求成功!", Toast.LENGTH_SHORT).show();
                        MyInformation myInformation = XmlUtils.toBean(MyInformation.class, s
                                .getBytes());
                        mUser = myInformation.getUser();


                        Utils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                ImageLoader.getInstance().displayImage(mUser.getPortrait(),
                                        mIvUserPic,
                                        mOptions);
                                mIvUserGender.setImageResource(mUser.getGender().equals(1) ? R
                                        .mipmap
                                        .userinfo_icon_male : R.mipmap.userinfo_icon_female);
                                mTvUserName.setText(mUser.getName());
                                mTvUserScore.setText(mUser.getScore() + "");
                                mTvUserCollect.setText(mUser.getFavoritecount() + "");
                                mTvUserStare.setText(mUser.getFollowers() + "");
                                mTvUserFollower.setText(mUser.getFans() + "");

                            }
                        });

                    }
                });

    }


    @OnClick({R.id.ll_user_score, R.id.ll_user_fans, ll_user_care, ll_user_collect,
            iv_user_pic, ll_user_msg,ll_user_blog,iv_user_qr_code})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_user_score:
                break;
            case R.id.ll_user_fans:
                Intent intent = new Intent(getActivity(), ThierActivity.class);
                intent.putExtra(Fields.FANS, "fans");
                startActivity(intent);
                break;
            case ll_user_care:
                Intent intent1 = new Intent(getActivity(), ThierActivity.class);
                intent1.putExtra(Fields.CARE, "care");
                startActivity(intent1);

                break;
            case ll_user_collect:

                startActivity(new Intent(getActivity(), CollectionActivity.class));
                break;
            case iv_user_pic:

                startActivity(new Intent(getActivity(), UserDetailsActivity.class));
                break;

            case ll_user_msg:
                startActivity(new Intent(getActivity(), MsgActivity.class));
                break;
            case ll_user_blog:
                startActivity(new Intent(getActivity(), BlogActivity.class));
                break;
            case iv_user_qr_code:
                showqr_codeDialog();

                break;
        }
    }


    private void showqr_codeDialog() {

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

