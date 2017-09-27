package org.mercury.oschina.main.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import org.mercury.oschina.Constant;
import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseActivity;
import org.mercury.oschina.main.MainTab;
import org.mercury.oschina.utils.AccessTokenHelper;
import org.mercury.oschina.utils.Constants;
import org.mercury.oschina.utils.Notice;
import org.mercury.oschina.utils.SpUtils;
import org.mercury.oschina.widget.BadgeView;
import org.mercury.oschina.widget.OnTabReselectListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements
        TabHost.OnTabChangeListener, View.OnClickListener,
        View.OnTouchListener {

    private static final String TAG = "====_MainActivity";


    private long preTime;

    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabHost;
    @Bind(R.id.quick_option_iv)
    View mAddBt;

    private BadgeView mBvNotice;

    public static Notice mNotice;

    private String[] mTitles;

    /**
     * Used to store the last screen title. For use in
     */
    private CharSequence mTitle;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        Log.e(TAG, "access_token:" + AccessTokenHelper.getAccessToken(SpUtils.get(this,
                Constant.USER_ID, "").toString()));
        mTitle = getResources().getString(R.string.main_tab_name_news);
        mTitles = getResources().getStringArray(R.array.main_titles_arrays);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setShowDividers(0);
        initTabs();

        // 中间按键图片触发
        mAddBt.setOnClickListener(this);

        mTabHost.setCurrentTab(0);
        // mTabHost.setOnTabChangedListener(this);

        IntentFilter filter = new IntentFilter(Constants.INTENT_ACTION_NOTICE);
        filter.addAction(Constants.INTENT_ACTION_LOGOUT);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    @SuppressWarnings("deprecation")
    private void initTabs() {
        MainTab[] tabs = MainTab.values();
        int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()) + this.toString());
            View indicator = View.inflate(this, R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            ImageView icon = (ImageView) indicator.findViewById(R.id.iv_icon);

            Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
            icon.setImageDrawable(drawable);
            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);
            mTabHost.addTab(tab, mainTab.getClz(), null);
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
    }


    @Override
    public void onTabChanged(String tabId) {
        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
                mTitle = mTitles[i == 4 || i == 3 ? i - 1 : i];
            } else {
                v.setSelected(false);
            }
        }
        if (tabId.equals(getString(MainTab.ME.getResName()))) {
            mBvNotice.setText("");
            mBvNotice.hide();
        }
        supportInvalidateOptionsMenu();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 发布动弹
            case R.id.quick_option_iv:
                Intent intent = new Intent(this, PublicTweetActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        boolean consumed = false;
        // use getTabHost().getCurrentTabView to decide if the current tab is
        // touched again
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && v.equals(mTabHost.getCurrentTabView())) {
            // use getTabHost().getCurrentView() to get a handle to the view
            // which is displayed in the tab - and to get this views context
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
                consumed = true;
            }
        }
        return consumed;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - preTime > 2000) {
                showToast("再按一次退出");
                preTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
