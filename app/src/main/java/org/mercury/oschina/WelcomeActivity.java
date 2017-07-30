package org.mercury.oschina;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import org.mercury.oschina.explorer.ui.activity.MainActivity;
import org.mercury.oschina.utils.AccessTokenHelper;
import org.mercury.oschina.utils.SpUtils;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String userId = SpUtils.get(WelcomeActivity.this, "userId", "").toString();
                if (!TextUtils.isEmpty(userId)) {
                    if (!TextUtils.isEmpty(AccessTokenHelper.getAccessToken(userId))) {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(WelcomeActivity.this, AuthActivity.class));
                    }
                } else {
                    startActivity(new Intent(WelcomeActivity.this, AuthActivity.class));
                }
                finish();
            }
        }, 1000);
    }


}
