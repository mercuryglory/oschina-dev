package org.mercury.oschina.main.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.bean.MyInformation;
import org.mercury.oschina.bean.User;
import org.mercury.oschina.utils.Fields;
import org.mercury.oschina.utils.OschinaUri;
import org.mercury.oschina.utils.Utils;
import org.mercury.oschina.utils.XmlUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.iv_user_pic)
    ImageView      mIvUserPic;
    @Bind(R.id.tv_user_name)
    TextView       mTvUserName;
    @Bind(R.id.rl_user)
    RelativeLayout mRlUser;
    @Bind(R.id.tv_user_time_join)
    TextView       mTvUserTimeJoin;
    @Bind(R.id.tv_user_area)
    TextView       mTvUserArea;
    @Bind(R.id.tv_user_platform)
    TextView       mTvUserPlatform;
    @Bind(R.id.tv_user_super)
    TextView       mTvUserSuper;
    public  DisplayImageOptions mOptions;
    private PopupWindow         pw;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
        mIvUserPic.setOnClickListener(this);
        initView();
        initData();
        initActionbar();
    }


    private void initView() {
        mOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)// 会识别图片的方向信息
                // .displayer(new FadeInBitmapDisplayer(220))
                .build();
    }

    private void initData() {
        StringCallback callback = new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int i) {

                Toast.makeText(AppContext.context, "请求失败!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String s, int i) {
             //   Toast.makeText(AppContext.context, "请求成功!", Toast.LENGTH_SHORT).show();
                MyInformation myInformation = XmlUtils.toBean(MyInformation.class, s.getBytes());
                final User user = myInformation.getUser();
                url = user.getPortrait();

                Utils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageLoader.getInstance().displayImage(user.getPortrait(), mIvUserPic, mOptions);
                        mTvUserName.setText(user.getName());
                        mTvUserArea.setText(user.getFrom());
                        mTvUserTimeJoin.setText(user.getJointime());
                        mTvUserPlatform.setText(user.getDevplatform());
                        mTvUserSuper.setText(user.getExpertise());

                    }
                });

            }
        };
        OkHttpUtils
                .get()
                .url(OschinaUri.ME_URL)

                .build()
                .execute(callback);


    }

    private void initActionbar() {
        // 得到actionbar
        ActionBar actionBar = getSupportActionBar();
        // 设置标题
        actionBar.setTitle("我的资料");

        // 设置箭头
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSettingPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this,
                R.layout.pw_appmanager, null);
        builder.setView(view);

        // 显示出来
        final AlertDialog dialog = builder.show();

        // 查找控件
        RelativeLayout tv_popup_1 = (RelativeLayout) view.findViewById(R.id.tv_popup_1);
        RelativeLayout tv_popup_2 = (RelativeLayout) view.findViewById(R.id.tv_popup_2);
        final RelativeLayout tv_popup_cancel = (RelativeLayout) view.findViewById(R.id.tv_popup_cancel);
        tv_popup_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicDialog();
                dialog.dismiss();

            }
        });

        tv_popup_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppContext.context, AppScreenActivity.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Fields.SCREENIMAGS, url);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        tv_popup_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        // 关闭


    }

    private void showChoosePicDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this,
                R.layout.pw_choose_pic, null);
        builder.setView(view);

        // 显示出来
        final AlertDialog dialog = builder.show();

        // 查找控件
        RelativeLayout tv_popup_choose_1 = (RelativeLayout) view.findViewById(R.id.tv_popup_choose_1);
        RelativeLayout tv_popup_choose_2 = (RelativeLayout) view.findViewById(R.id.tv_popup_choose_2);
        final RelativeLayout tv_popup_choose_cancel = (RelativeLayout) view.findViewById(R.id.tv_popup_choose_cancel);

        tv_popup_choose_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_popup_choose_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//调用android的图库
                startActivityForResult(i, 2);
                dialog.dismiss();
            }
        });
        tv_popup_choose_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用android自带的照相机
                Uri photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                startActivityForResult(intent, 1);
                dialog.dismiss();
            }
        });
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
        case 1:
            switch (resultCode) {
            case Activity.RESULT_OK://照相完成点击确定
                String sdStatus = Environment.getExternalStorageState();
                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                    Log.v("TestFile", "SD card is not avaiable/writeable right now.");
                    return;
                }
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                FileOutputStream b = null;
                File file = new File("/sdcard/pk4fun/");
                file.mkdirs();// 创建文件夹，名称为pk4fun // 照片的命名，目标文件夹下，以当前时间数字串为名称，即可确保每张照片名称不相同。网上流传的其他Demo这里的照片名称都写死了，则会发生无论拍照多少张，后一张总会把前一张照片覆盖。细心的同学还可以设置这个字符串，比如加上“ＩＭＧ”字样等；然后就会发现sd卡中myimage这个文件夹下，会保存刚刚调用相机拍出来的照片，照片名称不会重复。
                String str = null;
                Date date = null;
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// 获取当前时间，进一步转化为字符串
                date = new Date(resultCode);
                str = format.format(date);
                String fileName = "/sdcard/myImage/" + str + ".jpg";
                // sendBroadcast(fileName);
                try {
                    b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        b.flush();
                        b.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Activity.RESULT_CANCELED:// 取消
                break;
            }
            break;
        case 2:
            switch (resultCode) {
            case Activity.RESULT_OK: {
                Uri uri = data.getData();
                Cursor cursor = this.getContentResolver().query(uri, null,
                        null, null, null);
                cursor.moveToFirst();
                String imgNo = cursor.getString(0); // 图片编号
                String imgPath = cursor.getString(1); // 图片文件路径
                String imgSize = cursor.getString(2); // 图片大小
                String imgName = cursor.getString(3); // 图片文件名
                cursor.close();
                // Options options = new BitmapFactory.Options();
                // options.inJustDecodeBounds = false;
                // options.inSampleSize = 10;
                // Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
            }
            break;
            case Activity.RESULT_CANCELED:// 取消
                break;
            }
            break;
        }

    }

*/


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        case R.id.iv_user_pic:
            showSettingPwdDialog();

            break;
        case R.id.tv_popup_cancel:
            break;


        default:
            break;

        }
    }
}
