package org.mercury.oschina.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.mercury.oschina.R;
import org.mercury.oschina.adapter.MyBlogAdapter;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.bean.Blog;
import org.mercury.oschina.bean.BlogList;
import org.mercury.oschina.synthesis.ui.activity.NewsDetailActivity;
import org.mercury.oschina.utils.OschinaUri;
import org.mercury.oschina.utils.Utils;
import org.mercury.oschina.utils.XmlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class BlogActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.ptrlv_home_root_layout)
    PullToRefreshListView mPtrlistView;
    private  boolean      isRefresh = false;
    private List<Blog> mActives  = new ArrayList<>();
    private MyBlogAdapter mBlogAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh_list_view_layout);
        ButterKnife.bind(this);

        initView();
        initAdapter();
        loadData();
        setData();
        initActionbar();

    }

    private void setData() {
        mPtrlistView.setAdapter(mBlogAdapter);
    }

    private void initAdapter() {
        mBlogAdapter=  new MyBlogAdapter(BlogActivity.this,mActives);

    }

    private void initView() {

        mPtrlistView.setMode(PullToRefreshBase.Mode.BOTH);

        mPtrlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                switch (refreshView.getCurrentMode()) {
                case PULL_FROM_END:
                    isRefresh = false;
                    break;
                case PULL_FROM_START:
                    isRefresh = true;
                    break;
                }

                // 网络操作
                loadData();
            }
        });mPtrlistView.setOnItemClickListener(this);




    }

    private void loadData() {

        OkHttpUtils
                .get()
                .url(OschinaUri.BLOG_URL)

                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int i) {

                        Toast.makeText(AppContext.context, "请求失败!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        Toast.makeText(AppContext.context, "请求成功!", Toast.LENGTH_SHORT).show();
                        BlogList blogList = XmlUtils.toBean(BlogList.class, s.getBytes());

                        // Log.i("=====", "onResponse: "+ blogList.size());
                        if (isRefresh) {
                            mActives.clear();
                            mActives.addAll( blogList.getBloglist());

                        }else {
                            mActives.addAll( blogList.getBloglist());
                        }
                        Utils.runOnUIThread(new Runnable() {


                            @Override
                            public void run() {
                                mBlogAdapter.notifyDataSetChanged();
                                //停止刷新

                                mPtrlistView.onRefreshComplete();
                            }
                        });

                    }
                });

    }
    private void initActionbar() {
        // 得到actionbar
        ActionBar actionBar = getSupportActionBar();
        // 设置标题
        actionBar.setTitle("用户博客列表");

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mActives != null) {
        String url = mActives.get((int) id).getUrl();
            Intent intent = new Intent(AppContext.context, NewsDetailActivity.class);
            intent.putExtra("href", url);
            startActivity(intent);

        }


    }
}
