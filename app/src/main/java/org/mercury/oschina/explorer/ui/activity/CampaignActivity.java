package org.mercury.oschina.explorer.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;

import org.mercury.oschina.R;
import org.mercury.oschina.bean.base.FragmentInfo;
import org.mercury.oschina.explorer.adapter.ActivityViewPagerAdapter;
import org.mercury.oschina.user.UserFollowFragment;
import org.mercury.oschina.user.UserFavoriteFragment;
import org.mercury.oschina.tweet.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * created by Mercury at 2017/7/27
 * descript: 活动页面
 */
public class CampaignActivity extends AppCompatActivity {

    @Bind(R.id.pst)
    PagerSlidingTabStrip mPst;
    @Bind(R.id.vp)
    ViewPager            mVp;

    private List<FragmentInfo> mBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);
        ButterKnife.bind(this);
        mVp.setPageTransformer(true, new RotateDownTransformer());
        inteTitltBar();
        init();
    }

    private void inteTitltBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("活动中心");

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //处理箭头点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        String[] title = getResources().getStringArray(R.array.tab2_names);
        mBeanList.add(new FragmentInfo(title[0], new UserFollowFragment()));
        mBeanList.add(new FragmentInfo(title[1], new UserFavoriteFragment()));
        mVp.setAdapter(new ActivityViewPagerAdapter(getSupportFragmentManager(), mBeanList));
        mPst.setViewPager(mVp);
    }
}
