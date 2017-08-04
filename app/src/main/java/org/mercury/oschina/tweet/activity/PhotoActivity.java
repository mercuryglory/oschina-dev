package org.mercury.oschina.tweet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.mercury.oschina.Constant;
import org.mercury.oschina.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

public class PhotoActivity extends AppCompatActivity {

    @Bind(R.id.pv_screen)
    PhotoView mPvScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        String picUrl = getIntent().getStringExtra(Constant.PICTURE);
        Glide.with(this)
                .load(picUrl)
                .into(mPvScreen);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
