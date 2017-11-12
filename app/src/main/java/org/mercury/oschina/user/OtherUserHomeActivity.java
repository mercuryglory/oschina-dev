package org.mercury.oschina.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BasePresenterActivity;
import org.mercury.oschina.tweet.TweetListFragment;
import org.mercury.oschina.tweet.bean.User;
import org.mercury.oschina.user.contractor.OtherUserContract;
import org.mercury.oschina.user.contractor.OtherUserPresenter;
import org.mercury.oschina.utils.DialogHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by wang.zhonghao on 2017/8/15
 * description:  其它用户的个人中心界面
 */
public class OtherUserHomeActivity extends BasePresenterActivity<OtherUserContract.Presenter,
        User> implements OtherUserContract.View,DialogInterface.OnClickListener{

    @BindView(R.id.iv_user_portrait)
    CircleImageView ivUserPortrait;
    @BindView(R.id.tv_username)
    TextView        tvUsername;
    @BindView(R.id.tv_user_jointime)
    TextView        tvUserJointime;
    @BindView(R.id.tv_user_lastlogin)
    TextView        tvUserLastlogin;
    @BindView(R.id.iv_portrait_logo)
    CircleImageView ivPortraitLogo;
    @BindView(R.id.tv_name_logo)
    TextView        tvNameLogo;
    @BindView(R.id.toolbar)
    Toolbar         toolbar;
    @BindView(R.id.layout_appbar)
    AppBarLayout    layoutAppbar;
    @BindView(R.id.tablayout)
    TabLayout       tablayout;
    @BindView(R.id.view_divider)
    View            viewDivider;
    @BindView(R.id.viewPager)
    ViewPager       viewPager;
    @BindView(R.id.iv_gender)
    ImageView       ivGender;

    private static final String KEY_AUTHORID = "KEY_AUTHORID";

    private static final String KEY_USERID = "KEY_USERID";


    private List<Pair<String, Fragment>> mFragments;

    private long userId;
    MenuItem followItem;
    private int mRelation;  //1-已关注（对方未关注我）2-相互关注 3-未关注


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.menu_follow:
                DialogHelper.getConfirmDialog(this, "确定取消关注吗?",this).show();

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void initWidget() {
        mPresenter = new OtherUserPresenter(this);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        setSupportActionBar(toolbar);

        layoutAppbar.addOnOffsetChangedListener(new AppbarListener());


        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(new Pair<String, Fragment>("动弹", TweetListFragment.instantiate(new
                    Long(userId).intValue())));
            //todo 博客 问答
            mFragments.add(new Pair<String, Fragment>("博客", new Fragment()));
            mFragments.add(new Pair<String, Fragment>("讨论", new Fragment()));

        }
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {
        mPresenter.refreshing(userId);
    }

    @Override
    public void refreshSuccess(User user) {
        hideWaitDialog();
        tvUsername.setText(user.getName());
        tvNameLogo.setText(user.getName());
        getImageLoader().load(user.getPortrait()).error(R.mipmap.widget_dface).into
                (ivPortraitLogo);
        getImageLoader().load(user.getPortrait()).error(R.mipmap.widget_dface).into
                (ivUserPortrait);
        if (user.getGender() == 1) {
            ivGender.setImageResource(R.mipmap.ic_male);
        } else if (user.getGender() == 2) {
            ivGender.setImageResource(R.mipmap.userinfo_icon_female);
        }
        //1-已关注（对方未关注我）2-相互关注
        if (user.getRelation() == 1 || user.getRelation() == 2) {
            followItem.setIcon(R.drawable.selector_user_following);
        } else if (user.getRelation() == 3) {
            followItem.setIcon(R.drawable.selector_user_follow);
        }
        mRelation = user.getRelation();
    }

    @Override
    public void updateIcon(int relation) {
        if (relation == 2) {
            followItem.setIcon(R.drawable.selector_user_following);
        } else if (relation == 3) {
            followItem.setIcon(R.drawable.selector_user_follow);
        }
        mRelation = relation;

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        mPresenter.addRelation(userId, mRelation == 3 ? 1 : 0);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position).second;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragments.get(position).first;
        }
    }


    @Override
    protected void initBundle(Bundle bundle) {
        userId = bundle.getLong(KEY_USERID, 0);
        if (userId == 0) {
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
                viewDivider.setVisibility(View.GONE);
                isShow = true;
            } else if (isShow) {
                ivPortraitLogo.setVisibility(View.GONE);
                tvNameLogo.setVisibility(View.GONE);
                viewDivider.setVisibility(View.VISIBLE);
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
        followItem = menu.findItem(R.id.menu_follow);
        return super.onCreateOptionsMenu(menu);
    }


    //传递authorId过来
    public static void show(Context context, long id) {
        Intent intent = new Intent(context, OtherUserHomeActivity.class);
        intent.putExtra(KEY_USERID, id);
        context.startActivity(intent);

    }


}
