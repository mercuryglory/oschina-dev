package org.mercury.oschina.explorer.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;

import org.mercury.oschina.R;
import org.mercury.oschina.explorer.adapter.ActivityViewPagerAdapter;
import org.mercury.oschina.explorer.bean.ActivitysBean;
import org.mercury.oschina.explorer.ui.fragment.AllActivityFragment;
import org.mercury.oschina.explorer.ui.fragment.MyActivityFragment;
import org.mercury.oschina.explorer.ui.view.PagerSlidingTab;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Activitys extends AppCompatActivity {

    @Bind(R.id.pst)
    PagerSlidingTab mPst;
    @Bind(R.id.vp)
    ViewPager mVp;
    private List<ActivitysBean> mBeanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitys);
        ButterKnife.bind(this);
        mVp.setPageTransformer(true,new RotateDownTransformer());
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
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        String[] title = getResources().getStringArray(R.array.tab2_names);
        mBeanList.add(new ActivitysBean(title[0],new AllActivityFragment()));
        mBeanList.add(new ActivitysBean(title[1],new MyActivityFragment()));
        mVp.setAdapter(new ActivityViewPagerAdapter(getSupportFragmentManager(),mBeanList));
        mPst.setViewPager(mVp);
    }
}
