package org.mercury.oschina.explorer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.bean.ShakeObject;
import org.mercury.oschina.general.ui.NewsDetailActivity;
import org.mercury.oschina.utils.Constants;
import org.mercury.oschina.utils.XmlUtils;

import cz.msebera.android.httpclient.Header;

public class ShakeActivity extends AppCompatActivity {
    private ShakeListener mShaker;
    private RelativeLayout layoutRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        initActionbar();
        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        layoutRoot = (RelativeLayout) findViewById(R.id.layoutRoot);

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
//                            View view = snackbar.getView();
//                            TextView tv_snack = (TextView) view.findViewById(R.id.snackbar_text);
//                            tv_snack.setTextSize(24);
//                            tv_snack.setPadding(8,8,8,8);
//                            tv_snack.setTextColor(Color.parseColor("#aaff8888"));
//                            tv_snack.setBackgroundColor(Color.parseColor("#55ff88ff"));
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


    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("今天你摇了吗?");
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
}
