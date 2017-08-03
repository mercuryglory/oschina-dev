package org.mercury.oschina.tweet.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.tweet.bean.TweetResponse;

import butterknife.ButterKnife;
import retrofit2.Call;


/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      热门动弹
 */
public class HotTweetFragment extends NewTweetFragment implements AdapterView.OnItemClickListener,
        PullToRefreshBase.OnRefreshListener {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化控件
        ButterKnife.bind(this, view);
        mPtrListView = (PullToRefreshListView) view.findViewById(R.id.ptr_tweet_refresh);
        ListView listView = mPtrListView.getRefreshableView();

        View footView = View.inflate(AppContext.context, R.layout.footer_tweet_hot, null);
        listView.addFooterView(footView);

        mPtrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mPtrListView.setOnRefreshListener(this);
        mPtrListView.setOnItemClickListener(this);

    }

    @Override
    protected Call<TweetResponse> getCall() {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<TweetResponse> tweetData = retrofitCall.getTweetData("-1", pageIndex);
        return tweetData;
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        switch (refreshView.getCurrentMode()) {

            case PULL_FROM_START:
                isLoadMore = false;
                pageIndex = 0;
                loadData();
                break;

            default:
                break;

        }
    }


}
