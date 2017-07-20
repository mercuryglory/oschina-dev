package org.mercury.oschina.main.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;

import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;

import org.mercury.oschina.R;
import org.mercury.oschina.adapter.MainShowAdpter;
import org.mercury.oschina.bean.base.FragmentInfo;
import org.mercury.oschina.tweet.widget.PagerSlidingTabStrip;
import org.mercury.oschina.main.fragment.ConcertFragment;
import org.mercury.oschina.main.fragment.FansFragment;
import org.mercury.oschina.utils.Fields;
import org.mercury.oschina.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ThierActivity extends AppCompatActivity {

    @Bind(R.id.pst_main_title_layout)
    PagerSlidingTabStrip mPstMainTitleLayout;
    @Bind(R.id.vp_main_show_layout)
    ViewPager            mVpMainShowLayout;
    @Bind(R.id.dl_main_root_layout)
    DrawerLayout         mDlMainRootLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private List<FragmentInfo> mShowItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans_concert);
        ButterKnife.bind(this);
        mVpMainShowLayout.setPageTransformer(true,new FlipVerticalTransformer());
        initActionbar();
        initData();
    }

    private void initData() {
        String str = "care";
        String care = getIntent().getStringExtra(Fields.CARE);
        String fans = getIntent().getStringExtra(Fields.FANS);
        String[] titles = Utils.getStringArray(R.array.tab_names2);
        if (TextUtils.equals(str, care)) {

        mShowItems.add(new FragmentInfo(titles[0], new ConcertFragment()));
        mShowItems.add(new FragmentInfo(titles[1], new FansFragment()));
        }else{
            mShowItems.add(new FragmentInfo(titles[1], new ConcertFragment()));
            mShowItems.add(new FragmentInfo(titles[0], new FansFragment()));
        }
        // 得到字符数组里面的信息

        mVpMainShowLayout.setAdapter(new MainShowAdpter(getSupportFragmentManager(), mShowItems));

        // 给页签设置viewpager
        mPstMainTitleLayout.setViewPager(mVpMainShowLayout);


    }



    private void initActionbar() {
        // 得到actionbar
        ActionBar actionBar = getSupportActionBar();
        // 设置标题
        actionBar.setTitle("关注/粉丝");

        // 设置箭头
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
