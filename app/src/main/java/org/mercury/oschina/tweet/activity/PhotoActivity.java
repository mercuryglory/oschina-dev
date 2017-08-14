package org.mercury.oschina.tweet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.mercury.oschina.R;

import butterknife.ButterKnife;

public class PhotoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_viewpager_image);
        ButterKnife.bind(this);
//        String picUrl = getIntent().getStringExtra(Constant.PICTURE);
//        Glide.with(this)
//                .load(picUrl)
//                .into(mPvScreen);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
