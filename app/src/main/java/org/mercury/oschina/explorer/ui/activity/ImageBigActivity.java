package org.mercury.oschina.explorer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.mercury.oschina.R;
import org.mercury.oschina.explorer.util.Fileds;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

public class ImageBigActivity extends AppCompatActivity {

    @Bind(R.id.iv_iamgebig)
    PhotoView mIvIamgebig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_big);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra(Fileds.IMAGE_URL);
        Glide.with(this)
                .load(url)
                .fitCenter()
                .into(mIvIamgebig);

    }
}
