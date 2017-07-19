package org.mercury.oschina.explorer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.explorer.adapter.MyActivityAdpter;
import org.mercury.oschina.explorer.bean.Event;
import org.mercury.oschina.explorer.bean.EventList;
import org.mercury.oschina.explorer.util.XmlUtils;
import org.mercury.oschina.explorer.ui.view.LoadingPager;
import org.mercury.oschina.explorer.util.ApiHttp;
import org.mercury.oschina.explorer.util.Fileds;
import org.mercury.oschina.explorer.util.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * @创建者 LY
 * @创建时间 2016/8/15 19:34
 * @描述 ${TODO}
 */
public class MyActivityFragment extends Fragment {
    private List<Event> mList = new ArrayList<>();
    private PullToRefreshListView mListView;
    private LoadingPager mLoadingPager;
    private ListView mLv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载数据
        //跟新ui界面
        mLoadingPager = new LoadingPager(getContext()) {
            @Override
            public View isOnSuccess() {
                mListView = (PullToRefreshListView) View.inflate(AppContext.context(), R.layout.activity_list, null);
               /* mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
                mRefreshLayout.setOnRefreshListener(MyActivityFragment.this);
                mRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
                mListView = (ListView) view.findViewById(R.id.lv_lsit);*/

                return mListView;
            }

            @Override
            public void onLoad() {
                //加载数据
                StringCallback callBack = new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e , int i) {

                    }

                    @Override
                    public void onResponse(String response , int i) {
                        EventList eventList = XmlUtils.toBean(EventList.class, response.getBytes());
                        List<Event> list = eventList.getList();
                        mList.addAll(list);

                        if (onDataComplete(mList)) {
                            showView();
                        }
                    }
                };
                //跟新ui界面
                Utils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mLv = mListView.getRefreshableView();
                        mLv.setAdapter(new MyActivityAdpter(mList));
                        View view = View.inflate(AppContext.context(), R.layout.item_textview,null);
                        mLv.addFooterView(view);
                    }
                });
                ApiHttp.getData(Fileds.URL + "oschina/list/my_event_list/page0.xml", callBack);

            }
        };
        return mLoadingPager;
    }

   /* //下拉刷新
    @Override
    public void onRefresh() {
        //下拉重新 获取数据 更新页面
        mList.clear();
        //從新加載數據
        mLoadingPager.onLoad();
       setSwipeRefreshLoadingState();
    }
    // 完成刷新
    protected void executeOnLoadFinish() {
        setSwipeRefreshLoadedState();

    }
    protected void setSwipeRefreshLoadingState() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(true);
            // 防止多次重复刷新
            mRefreshLayout.setEnabled(false);
        }
    }
    protected void setSwipeRefreshLoadedState() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
            // 防止多次重复刷新
            mRefreshLayout.setEnabled(true);
            }
        }*/

}
