package org.mercury.oschina.explorer.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.explorer.adapter.AllActivityAdapter;
import org.mercury.oschina.explorer.bean.Event;
import org.mercury.oschina.explorer.bean.EventList;
import org.mercury.oschina.explorer.util.XmlUtils;
import org.mercury.oschina.explorer.ui.view.LoadingPager;
import org.mercury.oschina.explorer.util.Fileds;
import org.mercury.oschina.explorer.util.SPUtils;
import org.mercury.oschina.explorer.util.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * @创建者 LY
 * @创建时间 2016/8/15 19:34
 * @描述 ${TODO}
 */
public class AllActivityFragment extends Fragment {
    private List<Event> mList = new ArrayList<>();
    private PullToRefreshListView mListView;
    private SwipeRefreshLayout mRefreshLayout;
    private LoadingPager mLoadingPager;
    private AllActivityAdapter mAdapter;
    private ListView mLv;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoadingPager = new MyLoadingPager(getContext());
        return mLoadingPager;
    }

    class MyLoadingPager extends LoadingPager implements AdapterView.OnItemClickListener {


        public MyLoadingPager(Context context) {
            super(context);
        }

        @Override
        public View isOnSuccess() {
            //View view = inflate(getContext(), R.layout.all_activity_item, null);
            mListView = (PullToRefreshListView) View.inflate(AppContext.context(), R.layout.activity_list, null);
            mListView.setMode(PullToRefreshBase.Mode.BOTH);
           /* mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
            mRefreshLayout.setOnRefreshListener(this);
            mRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE);*/
            //mListView = (ListView) view.findViewById(R.id.lv_lsit);
            mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                   // mLoadingPager.onLoad();//获取数据
                    onLoad();
                }
            });
            mAdapter = new AllActivityAdapter(mList);
            mLv = mListView.getRefreshableView();
            mLv.setAdapter(mAdapter);
            mLv.setOnItemClickListener(this);
            return mListView;

        }
        @Override
        public void onLoad() {
            String url = Fileds.URL + "oschina/list/event_list/page0.xml";
            //判断一下当前是下拉,还是上拉
            switch (mListView.getCurrentMode()){
                case PULL_FROM_START:
                    mList.clear();
                    break;
                case PULL_FROM_END:
                    Toast.makeText(AppContext.context(), "已经到了底部", Toast.LENGTH_SHORT).show();
                    View view = View.inflate(AppContext.context(), R.layout.item_textview,null);
                    mLv.addFooterView(view);
                    mList.clear();
                    break;
            }

            OkHttpUtils
                    .get()//如果是Post请求，用.post()
                    .url(url)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e , int i) {
                            Toast.makeText(AppContext.context(), "获取数据失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response , int i) {
                            Toast.makeText(AppContext.context(), "数据获取成功", Toast.LENGTH_SHORT).show();
                            //ActiveList list = XmlUtils.toBean(ActiveList.class, response.getBytes());
                            EventList eventList = XmlUtils.toBean(EventList.class, response.getBytes());
                            List<Event> mContent = eventList.getList();
                            if (mContent == null) {
                                return;
                            }
                            if (mContent.size() ==0) {
                                Toast.makeText(AppContext.context(), "已经到了底部", Toast.LENGTH_SHORT).show();
                            }
                            mList.addAll(mContent);
                            if (onDataComplete(mList)) {
                                Utils.runOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showView();
                                        mAdapter.notifyDataSetChanged();
                                        mListView.onRefreshComplete();//停止刷新
                                    }
                                });
                            }
                        }
                    });

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SPUtils.saveBoolean(AppContext.mContext,mList.get(position - 1).getId()+"",true);

            Intent intent = new Intent(AppContext.context(),AllActivityWebView.class);
            Event event = mList.get(position - 1);
            intent.putExtra(Fileds.URL_WEB,event);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          //intent.putExtra(Fileds.URL_WEB,mList.get(position - 1).getUrl());
            AppContext.context().startActivity(intent);
            mAdapter.notifyDataSetChanged();
        }
    }
}
