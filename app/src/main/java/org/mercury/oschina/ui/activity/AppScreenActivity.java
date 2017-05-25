package org.mercury.oschina.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.mercury.oschina.R;
import org.mercury.oschina.utils.Fields;

import uk.co.senab.photoview.PhotoView;

public class AppScreenActivity extends AppCompatActivity {
    public DisplayImageOptions mOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_screen);

        Intent intent = getIntent();

        String imageUrl = intent.getStringExtra(Fields.SCREENIMAGS);

        PhotoView iv_need_big = (PhotoView) findViewById(R.id.iv_need_big);

        mOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)// 会识别图片的方向信息
                .displayer(new FadeInBitmapDisplayer(500))
                .build();
        ImageLoader.getInstance().displayImage(imageUrl,iv_need_big,mOptions);

    }
}
