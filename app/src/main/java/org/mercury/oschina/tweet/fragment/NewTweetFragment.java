package org.mercury.oschina.tweet.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.mercury.oschina.Constant;
import org.mercury.oschina.R;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.tweet.activity.TweetDetailActivity;
import org.mercury.oschina.tweet.adapter.NewTweetAdapter;
import org.mercury.oschina.tweet.bean.Tweet;
import org.mercury.oschina.tweet.bean.TweetResponse;

import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      最新动弹
 */
public class NewTweetFragment extends BaseFragment implements AdapterView.OnItemClickListener,
        PullToRefreshBase.OnRefreshListener {

    @Bind(R.id.ptr_tweet_refresh)
    PullToRefreshListView mPtrListView;

    public NewTweetAdapter mAdapter;
    public List<Tweet>     mData;

    int pageIndex = 1;
    public boolean isLoadMore;

    @Override
    protected void initData() {
        mPtrListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPtrListView.setOnRefreshListener(this);
        mPtrListView.setOnItemClickListener(this);

        loadData();
    }


    protected Call<TweetResponse> getCall() {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Call<TweetResponse> tweetData = retrofitCall.getTweetList("0", pageIndex);
        return tweetData;
    }

    public void loadData() {

        Call<TweetResponse> tweetData = getCall();
        tweetData.enqueue(new Callback<TweetResponse>() {
            @Override
            public void onResponse(Call<TweetResponse> call, Response<TweetResponse> response) {
                TweetResponse bean = response.body();
                mData = bean.getTweetlist();
                if (isLoadMore) {
                    loadMore();
                } else {
                    refresh();
                }

                mPtrListView.onRefreshComplete();
            }

            @Override
            public void onFailure(Call<TweetResponse> call, Throwable t) {
                mPtrListView.onRefreshComplete();

            }
        });

    }


    public void loadMore() {
        if (mAdapter == null) {
            mAdapter = new NewTweetAdapter();
            mAdapter.updateDatas(mData);
            mPtrListView.setAdapter(mAdapter);
        } else {
            mAdapter.addDatas(mData);
        }
    }

    public void refresh() {
        if (mAdapter == null) {
            mAdapter = new NewTweetAdapter();
            mAdapter.updateDatas(mData);
            mPtrListView.setAdapter(mAdapter);
        } else {
            mAdapter.updateDatas(mData);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), TweetDetailActivity.class);
        Tweet tweet = mAdapter.getShowItems().get((int) id);
        intent.putExtra(Constant.TWEET_DETAIL, tweet.getId());
        startActivity(intent);

    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        switch (refreshView.getCurrentMode()) {

            case PULL_FROM_START:
                isLoadMore = false;
                pageIndex = 1;
                loadData();
                break;

            case PULL_FROM_END:
                isLoadMore = true;
                pageIndex++;
                loadData();
                break;

            default:
                break;

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tweet_refresh;
    }
}
