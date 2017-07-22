package org.mercury.oschina.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.explorer.ui.activity.QRCodeActivity;
import org.mercury.oschina.utils.Constants;
import org.mercury.oschina.utils.Notice;
import org.mercury.oschina.widget.BadgeView;
import org.mercury.oschina.main.MainTab;
import org.mercury.oschina.widget.OnTabReselectListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        TabHost.OnTabChangeListener, View.OnClickListener,
        View.OnTouchListener {

    private static final String TAG = "====_MainActivity";

    public static Activity mMainActivity;

    private long mBackPressedTime;

    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabHost;
    @Bind(R.id.quick_option_iv)
    View mAddBt;

    private BadgeView mBvNotice;

    public static Notice mNotice;

    private String[] mTitles;

    /**
     * Used to store the last screen title. For use in
     * {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    private LinearLayout mLy_quick_option_text;
    private LinearLayout mLy_quick_option_voice2;
    private LinearLayout mLy_quick_option_photo;
    private LinearLayout mLy_quick_option_album;
    private LinearLayout mLy_quick_option_scan;
    private LinearLayout mLy_quick_option_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainActivity = this;
        ButterKnife.bind(this);
        initView();
    }


    public void initView() {
        mTitle = getResources().getString(R.string.main_tab_name_news);
        mTitles = getResources().getStringArray(R.array.main_titles_arrays);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }
        initTabs();

        // 中间按键图片触发
        mAddBt.setOnClickListener(this);

        mTabHost.setCurrentTab(0);
        // mTabHost.setOnTabChangedListener(this);

        IntentFilter filter = new IntentFilter(Constants.INTENT_ACTION_NOTICE);
        filter.addAction(Constants.INTENT_ACTION_LOGOUT);
    }

    private void showQuickOption() {
        //        final QuickOptionDialog dialog = new QuickOptionDialog(
        //                MainActivity.this);
        //        dialog.setCancelable(true);
        //        dialog.setCanceledOnTouchOutside(true);
        //        dialog.show();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = View.inflate(getApplicationContext(), R.layout.dialog_quick_option, null);
        builder.setView(view);
        final AlertDialog dialog = builder.show();
        //文字
        mLy_quick_option_text = (LinearLayout) view.findViewById(R.id.ly_quick_option_text);
        //语音
        mLy_quick_option_voice2 = (LinearLayout) view.findViewById(R.id.ly_quick_option_voice2);
        //拍照
        mLy_quick_option_photo = (LinearLayout) view.findViewById(R.id.ly_quick_option_photo);
        //相册
        mLy_quick_option_album = (LinearLayout) view.findViewById(R.id.ly_quick_option_album);
        //扫一扫
        mLy_quick_option_scan = (LinearLayout) view.findViewById(R.id.ly_quick_option_scan);
        //便签
        mLy_quick_option_note = (LinearLayout) view.findViewById(R.id.ly_quick_option_note);

        mLy_quick_option_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), QuickTextActivity.class));
                dialog.dismiss();

            }
        });
        mLy_quick_option_voice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), QuickViodeActivity.class));
                dialog.dismiss();
            }
        });
        mLy_quick_option_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setType("image/*");
                startActivityForResult(intent, 0);
                dialog.dismiss();

            }
        });
        mLy_quick_option_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
                dialog.dismiss();
            }
        });

        mLy_quick_option_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QRCodeActivity.class));
                dialog.dismiss();
            }
        });
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

    @SuppressWarnings("deprecation")
    private void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
//        restoreActionBar();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.search:
//                UIHelper.showSimpleBack(this, SimpleBackPage.SEARCH);
//                break;
//            default:
//                break;
//        }
        return super.onOptionsItemSelected(item);
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
        int id = v.getId();
        switch (id) {
            // 点击了快速操作按钮
            case R.id.quick_option_iv:
                //  TweetPublishActivity.show(MainActivity.this);
                showQuickOption();
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}
