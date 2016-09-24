package org.lion.oschina.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.lion.oschina.adapter.MeAdapter;
import org.lion.oschina.application.MyApplication;
import org.lion.oschina.base.AppContext;
import org.lion.oschina.bean.Active;
import org.lion.oschina.bean.ActiveList;
import org.lion.oschina.global.OsChinaApp;
import org.lion.oschina.tweet.activity.TweetDetailActivity;
import org.lion.oschina.tweet.util.Constant;
import org.lion.oschina.utils.OschinaUri;
import org.lion.oschina.utils.Utils;
import org.lion.oschina.utils.XmlUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * @创建者 Administrator
 * @创建时间 2016/8/17 15:11
 * @描述 ${TODU}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODU}
 */
public class MeFragment extends BasicFragment implements AdapterView.OnItemClickListener {
    private PullToRefreshListView mPtrlistView;
    private List<Active> mActives = new ArrayList<>();
    private Activity  mActivity;
    private MeAdapter mMeAdapter;
    private boolean isRefresh = true;

    @Override
    public View createView() {

        mPtrlistView =
                (PullToRefreshListView) View.inflate(OsChinaApp.context, R.layout.refresh_list_view_layout2, null);

        // 设置模式
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
                mMLoadPager.loadData();
            }
        });
        mPtrlistView.setOnItemClickListener(this);


        return mPtrlistView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity =  getActivity();
        mMeAdapter = new MeAdapter(mActives, mActivity);
        //mPtrlistView.setAdapter(mMeAdapter);
        mPtrlistView.setAdapter(mMeAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    protected Object loadDataThread() {

              AppContext.mainHandle.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      OkHttpUtils
                              .get()
                              .url(OschinaUri.MEOO_URL)

                              .build()
                              .execute(new StringCallback() {


                                  @Override
                                  public void onError(Call call, Exception e, int i) {

                                      Toast.makeText(OsChinaApp.context, "请求失败!", Toast.LENGTH_SHORT).show();
                                  }

                                  @Override
                                  public void onResponse(String s, int i) {
                                      //    Toast.makeText(OsChinaApp.context, "请求成功!", Toast.LENGTH_SHORT).show();
                                      ActiveList activeList = XmlUtils.toBean(ActiveList.class, s.getBytes());
                                      Log.i("=====", "onResponse: "+ mActives.size());
                                      if (isRefresh) {
                                          mActives.clear();
                                          mActives.addAll(activeList.getList());

                                      }else {
                                          Toast.makeText(OsChinaApp.context, "请求失败!没有更多了!", Toast.LENGTH_SHORT).show();

                                      }
                                      Utils.runOnUIThread(new Runnable() {


                                          @Override
                                          public void run() {
                                              mMeAdapter.notifyDataSetChanged();
                                              //停止刷新


                                              mPtrlistView.onRefreshComplete();
                                          }
                                      });

                                  }
                              });
                  }
              },300)  ;


        return "";
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MyApplication.context, TweetDetailActivity.class);
        //  Tweet tweet = mActives.get((int) id);
        Active active = mActives.get((int) id);
        intent.putExtra(Constant.TWEET_DETAIL, active.getId());
        startActivity(intent);
    }


}
