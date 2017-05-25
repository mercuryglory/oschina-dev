package org.mercury.oschina.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.mercury.oschina.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.et_username)
    AppCompatEditText mEtUsername;
    @Bind(R.id.et_password)
    AppCompatEditText mEtPassword;
    @Bind(R.id.btn_login)
    Button            mBtnLogin;
    @Bind(R.id.iv_qq_login)
    ImageView         mIvQqLogin;
    @Bind(R.id.iv_wx_login)
    ImageView         mIvWxLogin;
    @Bind(R.id.iv_sina_login)
    ImageView         mIvSinaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initActionbar();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login, R.id.iv_qq_login, R.id.iv_wx_login, R.id.iv_sina_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                break;
            case R.id.iv_qq_login:
                break;
            case R.id.iv_wx_login:
                break;
            case R.id.iv_sina_login:
                break;
        }
    }


    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("登陆");
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
