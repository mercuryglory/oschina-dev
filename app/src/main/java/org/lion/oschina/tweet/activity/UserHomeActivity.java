package org.lion.oschina.tweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.lion.oschina.bean.Active;
import org.lion.oschina.general.ui.NewsDetailActivity;
import org.lion.oschina.tweet.adapter.UserHomeAdapter;
import org.lion.oschina.tweet.bean.User;
import org.lion.oschina.tweet.bean.UserInformation;
import org.lion.oschina.tweet.holder.UserHeadHolder;
import org.lion.oschina.tweet.net.HttpApi;
import org.lion.oschina.tweet.util.Constant;
import org.lion.oschina.tweet.util.ToastUtil;
import org.lion.oschina.tweet.util.Utils;
import org.lion.oschina.tweet.util.XmlUtils;

import java.util.List;

import okhttp3.Call;

public class UserHomeActivity extends AppCompatActivity implements AdapterView
        .OnItemClickListener, PullToRefreshBase.OnRefreshListener {

    private PullToRefreshListView mPtrListView;
    private ListView              mListView;

    private UserHomeAdapter mAdapter;
    private int pageIndex = 0;
    private List<Active> mList;
    private User                               mUser;
    private Intent                             intent;
    private UserHeadHolder mUserHeadHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        initActionBar();

        mPtrListView = (PullToRefreshListView) findViewById(R.id.ptr_tweet_refresh);
        mListView = mPtrListView.getRefreshableView();
        mUserHeadHolder = new UserHeadHolder();
        mListView.addHeaderView(mUserHeadHolder.getView());
        initData();
        initEvent();


    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("用户中心");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        Intent intent = getIntent();
        mUser = (User) intent.getSerializableExtra(Constant.USER_ID);
        getActivies(mUser);
    }

    private void getActivies(final User user) {
        StringCallback callback = new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                mPtrListView.onRefreshComplete();
            }

            @Override
            public void onResponse(String s, int i) {
                UserInformation userInformation = XmlUtils.toBean(UserInformation.class, s);
                final User thisUser = userInformation.getUser();
                mList = userInformation.getActiveList();
                Utils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mUserHeadHolder.bindView(mPtrListView, thisUser);
                        loadMore();
                        if (mPtrListView != null) {
                            mPtrListView.onRefreshComplete();
                        }
                    }
                });
            }
        };
        HttpApi.getOtherUserData(pageIndex, user.getName(), user.getId(), callback);
    }

    private void loadMore() {
        if (mAdapter == null) {
            mAdapter = new UserHomeAdapter();
            mAdapter.updateDatas(mList);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.addDatas(mList);
        }
    }


    private void initEvent() {
        mPtrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mPtrListView.setOnRefreshListener(this);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Active active = mAdapter.getShowItems().get(position - 2);
        switch (active.getObjectType()) {
            case 100:
            case 101:
                intent = new Intent(this, TweetDetailActivity.class);
                intent.putExtra(Constant.TWEET_DETAIL, active.getId());
                System.out.println("100activeId:" + active.getId());
                break;

            //TODO
            //博客详情
            case 18:
                intent = new Intent(this, NewsDetailActivity.class);
                intent.putExtra("href", active.getId());
                System.out.println("18activeId:" + active.getId());
                break;

            //帖子详情
            case 17:
                intent = new Intent(this, NewsDetailActivity.class);
                intent.putExtra("href", active.getId());
                System.out.println("17activeId:" + active.getId());
                break;

            //资讯详情
            case 16:
                intent = new Intent(this, NewsDetailActivity.class);
                intent.putExtra(Constant.TO_NEWS, active.getId());
                System.out.println("16activeId:" + active.getId());
                break;

            default:
                ToastUtil.showToast("抱歉,服务器正在维护");
                return;

        }
        startActivity(intent);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        pageIndex++;
        getActivies(mUser);
    }
}
