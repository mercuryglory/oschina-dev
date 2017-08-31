package org.mercury.oschina.explorer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.mercury.oschina.AppContext;
import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseActivity;
import org.mercury.oschina.bean.ShakeObject;
import org.mercury.oschina.synthesis.ui.NewsDetailActivity;
import org.mercury.oschina.utils.Constants;
import org.mercury.oschina.utils.XmlUtils;

import cz.msebera.android.httpclient.Header;

public class ShakeActivity extends BaseActivity {

    private ShakeListener mShaker;
    private RelativeLayout layoutRoot;


    @Override
    protected int getContentView() {
        return R.layout.activity_shake;
    }

    @Override
    protected void initData() {
        super.initData();
        layoutRoot = (RelativeLayout) findViewById(R.id.layoutRoot);
        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                new AsyncHttpClient().get(Constants.API_URL + "action/api/rock_rock", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        final ShakeObject shakeObject = XmlUtils.toBean(ShakeObject.class, responseBody);

                        if (shakeObject!=null){
                            Snackbar snackbar = Snackbar.make(layoutRoot, shakeObject.getTitle(), Snackbar.LENGTH_LONG)
                                    .setAction("瞧一瞧", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(AppContext.context, NewsDetailActivity.class);
                                            intent.putExtra("href", shakeObject.getUrl());
                                            startActivity(intent);
                                        }
                                    })
                                    .setActionTextColor(Color.parseColor("#DD88ff88"));
                            snackbar.show();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(ShakeActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
                vibe.vibrate(100);
            }
        });
    }

    @Override
    public void onResume() {
        mShaker.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mShaker.pause();
        super.onPause();
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
}
