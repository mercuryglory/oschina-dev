package org.mercury.oschina.explorer.ui.activity;

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
import com.zhy.http.okhttp.callback.StringCallback;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.explorer.adapter.FriendAdapter;
import org.mercury.oschina.explorer.bean.Active;
import org.mercury.oschina.explorer.bean.ActiveList;
import org.mercury.oschina.explorer.util.XmlUtils;
import org.mercury.oschina.explorer.util.ApiHttp;
import org.mercury.oschina.explorer.util.Fileds;
import org.mercury.oschina.tweet.activity.TweetDetailActivity;
import org.mercury.oschina.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * created by Mercury at 2017/7/27
 * descript: 朋友圈
 */
public class FriendCircleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private PullToRefreshListView mLv_show;
    private List<Active> mList = new ArrayList<>();
    private FriendAdapter mQActivity;
    private ListView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_circle);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        initView();
        initActionbar();

        initData();
    }

    private int i = 0;

    //获取数据
    private void initData() {
      /*  String info = "page0.xml";*/
        switch (mLv_show.getCurrentMode()) {
            case PULL_FROM_START:
                mList.clear();
                i = 0;
                break;
           /* case PULL_FROM_END:
                System.out.println("下拉");
                info = "page1.xml";
                break;*/
        }

        String url = Fileds.URL + "oschina/list/active_list1/page" + i + ".xml";
        i++;
        StringCallback stringCallback;
        stringCallback = new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                //可以直接在这里进行UI的操作
                //网络访问错误
                Toast.makeText(getApplicationContext(), "亲!已经没有数据了", Toast.LENGTH_SHORT).show();
                View view = View.inflate(AppContext.context(), R.layout.item_textview, null);
                mLv_show.onRefreshComplete();//停止刷新
                if(mView.getFooterViewsCount() <= 1){

                mView.addFooterView(view);
                }
            }

            @Override
            public void onResponse(String response, int i) {
                //可以直接在这里进行UI的操作
                //网络正常逻辑
                Toast.makeText(getApplicationContext(), "数据获取成功", Toast.LENGTH_SHORT).show();
                ActiveList list = XmlUtils.toBean(ActiveList.class, response.getBytes());
                List<Active> list1 = list.getList();

                if (list1 != null) {
                    mList.addAll(list1);
                } else {
                    Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                    //return;
                }
                if (list1.size() == 0) {
                    Toast.makeText(getApplicationContext(), "已经到底了", Toast.LENGTH_SHORT).show();
                }

                mQActivity.notifyDataSetChanged();
                mLv_show.onRefreshComplete();//停止刷新

            }
        };
        ApiHttp.getData(url, stringCallback);

    }


    private void initView() {
        mLv_show = (PullToRefreshListView) findViewById(R.id.lv_more_friend);

        mLv_show.setMode(PullToRefreshBase.Mode.BOTH); //设置模式 上下 刷新
        //设置 刷新监听
        mLv_show.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                initData();
            }
        });
        mQActivity = new FriendAdapter(mList);
        mQActivity.setContext(FriendCircleActivity.this);
        mView = mLv_show.getRefreshableView();
        mView.setAdapter(mQActivity);

        //设置条目点击事件
        mLv_show.setOnItemClickListener(this);
        //下拉刷新监听
      /*  mSwipwrefreshlayout.setOnRefreshListener(this);
        mSwipwrefreshlayout.setColorSchemeColors(Color.BLUE,Color.RED,Color.GREEN);*/
        //长按条目监听
        //mLv_show.setOnItemClickListener(this);
    }


    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("好友圈");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    //设置箭头点击事件
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
        if(mList.get(position-1).getObjectType() == 3){
            //跳转到到博客详情页面
            Intent intent = new Intent(FriendCircleActivity.this,BlogInfoActivity.class);
            intent.putExtra(Fileds.BLOG_ID,mList.get(position -1).getObjectId());
            startActivity(intent);
        }else{
            //跳转到动弹详情
            Intent intent = new Intent(AppContext.context(),TweetDetailActivity.class);
            intent.putExtra(Constant.TWEET_DETAIL,mList.get(position -1).getObjectId());
            startActivity(intent);
        }
    }


}
