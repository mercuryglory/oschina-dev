package org.mercury.oschina.explorer.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import org.mercury.oschina.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GenerateQRCodeActivity extends AppCompatActivity {
    @Bind(R.id.iv_generate_qrcode)
    ImageView mIvGenerateQrcode;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);
        ButterKnife.bind(this);
        initActionbar();
        createQRCode();

    }

    private void createQRCode() {
        createChineseQRCodeWithLogo();
    }


    private void createChineseQRCodeWithLogo() {
        /*
        这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
        请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
         */
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap logoBitmap = BitmapFactory.decodeResource(GenerateQRCodeActivity.this
                        .getResources(), R.mipmap.ic_launcher);
                return logoBitmap;
//                return QRCodeEncoder.syncEncodeQRCode("http://www.baidu.com", BGAQRCodeUtil.dp2px(GenerateQRCodeActivity.this, 250), Color.parseColor("#FFAACE5F"), logoBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    mIvGenerateQrcode.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(GenerateQRCodeActivity.this, "生成带logo的中文二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("我的二维码");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    //设置箭头点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
