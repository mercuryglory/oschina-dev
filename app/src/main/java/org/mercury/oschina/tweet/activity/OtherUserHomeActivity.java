package org.mercury.oschina.tweet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseActivity;
import org.mercury.oschina.bean.Active;
import org.mercury.oschina.tweet.adapter.UserHomeAdapter;
import org.mercury.oschina.tweet.bean.User;

import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wang.zhonghao on 2017/8/15
 * description:  其它用户的个人中心界面
 */
public class OtherUserHomeActivity extends BaseActivity {

    @Bind(R.id.iv_user_portrait)
    CircleImageView ivUserPortrait;
    @Bind(R.id.tv_username)
    TextView        tvUsername;
    @Bind(R.id.tv_user_jointime)
    TextView        tvUserJointime;
    @Bind(R.id.tv_user_lastlogin)
    TextView        tvUserLastlogin;
    @Bind(R.id.iv_portrait_logo)
    CircleImageView ivPortraitLogo;
    @Bind(R.id.tv_name_logo)
    TextView        tvNameLogo;
    @Bind(R.id.toolbar)
    Toolbar         toolbar;
    @Bind(R.id.layout_appbar)
    AppBarLayout    layoutAppbar;
    @Bind(R.id.tablayout)
    TabLayout tablayout;


    private static final String KEY_AUTHORID = "KEY_AUTHORID";


    private UserHomeAdapter mAdapter;
    private int pageIndex = 0;
    private List<Active> mList;
    private User         mUser;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.menu_follow:
                showToast("follow");
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        setSupportActionBar(toolbar);

        layoutAppbar.addOnOffsetChangedListener(new AppbarListener());

        tvUsername.setText(mUser.getName());
        tvNameLogo.setText(mUser.getName());
        getImageLoader().load(mUser.getPortrait()).error(R.mipmap.widget_dface).into
                (ivPortraitLogo);
        getImageLoader().load(mUser.getPortrait()).error(R.mipmap.widget_dface).into
                (ivUserPortrait);

        tablayout.addTab(tablayout.newTab().setText("动弹"));
        tablayout.addTab(tablayout.newTab().setText("博客"));
        tablayout.addTab(tablayout.newTab().setText("讨论"));
    }

    @Override
    protected void initBundle(Bundle bundle) {
        mUser = bundle.getParcelable(KEY_AUTHORID);
        if (mUser == null) {
            return;
        }
    }

    private class AppbarListener implements AppBarLayout.OnOffsetChangedListener {
        int     maxOffset = -1;
        boolean isShow    = false;

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (maxOffset == -1) {
                maxOffset = appBarLayout.getTotalScrollRange();
            }
            if (maxOffset + verticalOffset == 0) {
                ivPortraitLogo.setVisibility(View.VISIBLE);
                tvNameLogo.setVisibility(View.VISIBLE);
                isShow = true;
            } else if (isShow) {
                ivPortraitLogo.setVisibility(View.GONE);
                tvNameLogo.setVisibility(View.GONE);
                isShow = false;
            }
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_other_user;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_other_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public static void show(Context context, User user) {
        Intent intent = new Intent(context, OtherUserHomeActivity.class);
        intent.putExtra(KEY_AUTHORID, user);
        context.startActivity(intent);

    }


}
