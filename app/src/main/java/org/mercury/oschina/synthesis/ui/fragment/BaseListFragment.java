package org.mercury.oschina.synthesis.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.orhanobut.logger.Logger;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.synthesis.adapter.BasicAdapter;
import org.mercury.oschina.synthesis.bean.Entity;
import org.mercury.oschina.synthesis.ui.activity.NewsDetailActivity;
import org.mercury.oschina.synthesis.utils.GeneralUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by more on 2016-08-15 15:03:38.
 */
public abstract class BaseListFragment<T extends Entity> extends Fragment
        implements AdapterView.OnItemClickListener,
        PullToRefreshBase.OnRefreshListener {
    protected static final String TAG = "====_BaseListFragment";
    protected ListView mListView;
    protected List<T> mDatas = new ArrayList<>();
    protected static final int DATA_LOADED = 490;
    protected BasicAdapter<T> mAdapter;

    protected ListHandler mHandler = new ListHandler();
    protected PullToRefreshListView mPtrListView;

    protected boolean isRefresh = false;

    protected String preToken = "0";
    protected String nextToken = "0";
    protected String token = "0";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mPtrListView == null) {
            mPtrListView = (PullToRefreshListView) View.inflate(AppContext.context, R.layout.fragment_listview, null);
            mPtrListView.setMode(PullToRefreshBase.Mode.BOTH);
            mListView = mPtrListView.getRefreshableView();
            mListView.setOnItemClickListener(this);
            mAdapter = onCreateAdapter();
            mListView.setAdapter(mAdapter);
            mPtrListView.setOnRefreshListener(this);
        }
        return mPtrListView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        onLoadData();
    }


    protected abstract BasicAdapter<T> onCreateAdapter();

    protected abstract void onLoadData();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Logger.i(position + "");
        T item = mAdapter.getItem(position - 1);

        TextView titleView = mAdapter.getTitleView(item);
        titleView.setTextColor(GeneralUtils.getColor(R.color.item_clicked));
        String href = item.getHref();
        if (!AppContext.mAllVisitedItem.containsKey(href)) {
            GeneralUtils.writeVisitedItem(href);
        }
        Intent intent = new Intent(AppContext.context, NewsDetailActivity.class);
        intent.putExtra("href", href);
        startActivity(intent);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        switch (refreshView.getCurrentMode()) {
            case PULL_FROM_START:
                isRefresh = true;
                token = "0";
                break;
            case PULL_FROM_END:
                isRefresh = false;
                token = nextToken;
                break;
        }
        onLoadData();
    }

    protected void stopRefresh() {
        mPtrListView.onRefreshComplete();
    }

    public class ListHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DATA_LOADED:
                    break;
            }
        }
    }

}
