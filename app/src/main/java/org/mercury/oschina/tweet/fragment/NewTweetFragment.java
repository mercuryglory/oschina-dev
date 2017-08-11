package org.mercury.oschina.tweet.fragment;

import android.support.v4.widget.SwipeRefreshLayout;

import org.mercury.oschina.base.BaseRecyclerAdapter;
import org.mercury.oschina.base.BaseRecyclerViewFragment;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.tweet.adapter.NewTweetAdapter;
import org.mercury.oschina.tweet.bean.TweetResponse;
import org.mercury.oschina.widget.recyclerload.OnLoadMoreListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      最新动弹
 */
public class NewTweetFragment extends BaseRecyclerViewFragment<TweetResponse> implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    public NewTweetAdapter mAdapter;

    int pageIndex = 1;
    public boolean isLoadMore;

//    @Bind(R.id.rv)
//    HaoRecyclerView    rv;
//    @Bind(R.id.swr)
//    SwipeRefreshLayout swr;

//    @Override
//    protected void initData() {
//        swr.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R
//                .color.swiperefresh_color3, R.color.swiperefresh_color4);
//        swr.setOnRefreshListener(this);
//        rv.setOnLoadMoreListener(this);
//        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration
//                .VERTICAL));
//
//        mAdapter = new NewTweetAdapter(getActivity());
//        rv.setAdapter(mAdapter);
//        requestData();
//
//        rv.setOnItemClickListener(new WrapAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, long itemId) {
//                showToast(position + "");
//            }
//        });
//    }


//    protected Call<TweetResponse> getCall() {
//        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
//        Call<TweetResponse> tweetData = retrofitCall.getTweetList("0", pageIndex);
//        return tweetData;
//    }

//    public void requestData() {
//
//        Call<TweetResponse> tweetData = getCall();
//        tweetData.enqueue(new Callback<TweetResponse>() {
//            @Override
//            public void onResponse(Call<TweetResponse> call, Response<TweetResponse> response) {
//                TweetResponse bean = response.body();
//                if (isLoadMore) {
//                    loadMore(bean.getTweetlist());
//                } else {
//                    refresh(bean.getTweetlist());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<TweetResponse> call, Throwable t) {
//
//            }
//        });
//
//    }

    @Override
    protected void failure(Call<TweetResponse> call, Throwable t) {

    }

    @Override
    protected void response(Call<TweetResponse> call, Response<TweetResponse> response) {
        TweetResponse bean = response.body();
        if (isLoadMore) {
            loadMore(bean.getTweetlist());
        } else {
            refresh(bean.getTweetlist());
        }
    }


    @Override
    protected Call<TweetResponse> getCall(HttpApi retrofitCall) {
        Call<TweetResponse> tweetData = retrofitCall.getTweetList("0", pageIndex);
        return tweetData;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        NewTweetAdapter adapter = new NewTweetAdapter(getActivity());
        mAdapter = adapter;
        return adapter;
    }

    public void loadMore(List list) {
        mAdapter.addAll(list);
        mRecyclerView.loadMoreComplete();
    }

    public void refresh(List list) {
        if (mAdapter == null) {
            mAdapter.addAll(list);
        } else {
            mAdapter.setData(list);
        }
        mRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete();
    }

//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_tweet_refresh;
//    }


    @Override
    public void onRefresh() {
        isLoadMore = false;
        mRecyclerView.setCanloadMore(false);
        pageIndex = 1;
        requestData();

    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        pageIndex++;
        requestData();

    }
}
