package org.mercury.oschina.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;

import org.mercury.oschina.R;
import org.mercury.oschina.adapter.MainShowAdpter;
import org.mercury.oschina.bean.base.FragmentInfo;
import org.mercury.oschina.tweet.widget.PagerSlidingTabStrip;
import org.mercury.oschina.ui.fragment.CommentFragment;
import org.mercury.oschina.ui.fragment.FansFragment;
import org.mercury.oschina.ui.fragment.MeFragment;
import org.mercury.oschina.ui.fragment.PSFragment;
import org.mercury.oschina.ui.fragment.PraiseFragment;
import org.mercury.oschina.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MsgActivity extends AppCompatActivity {

    @Bind(R.id.pst_main_title_layout)
    PagerSlidingTabStrip mPstMainTitleLayout;
    @Bind(R.id.vp_main_show_layout)
    ViewPager            mVpMainShowLayout;
    @Bind(R.id.dl_main_root_layout)
    DrawerLayout         mDlMainRootLayout;
    private List<FragmentInfo> mShowItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        mVpMainShowLayout.setPageTransformer(true, new AccordionTransformer());
        initActionbar();
        initData();
    }

    private void initData() {


        // 得到字符数组里面的信息
        String[] titles = Utils.getStringArray(R.array.tab_names0);
        //mShowItems.add(new FragmentInfo(titles[0], new MeFragment()));
        mShowItems.add(new FragmentInfo("@Me", new MeFragment()));
        mShowItems.add(new FragmentInfo(titles[1], new CommentFragment()));
        mShowItems.add(new FragmentInfo(titles[2], new PSFragment()));
        mShowItems.add(new FragmentInfo(titles[3], new FansFragment()));
        mShowItems.add(new FragmentInfo(titles[4], new PraiseFragment()));

        mVpMainShowLayout.setAdapter(new MainShowAdpter(getSupportFragmentManager(), mShowItems));

        // 给页签设置viewpager
        mPstMainTitleLayout.setViewPager(mVpMainShowLayout);

    }


    private void initActionbar() {
        // 得到actionbar
        ActionBar actionBar = getSupportActionBar();
        // 设置标题
        actionBar.setTitle("消息中心");

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