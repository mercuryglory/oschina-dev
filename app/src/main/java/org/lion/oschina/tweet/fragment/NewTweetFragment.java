package org.lion.oschina.tweet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.lion.oschina.R;
import org.lion.oschina.application.MyApplication;
import org.lion.oschina.tweet.activity.TweetDetailActivity;
import org.lion.oschina.tweet.adapter.NewTweetAdapter;
import org.lion.oschina.tweet.bean.Tweet;
import org.lion.oschina.tweet.bean.TweetsList;
import org.lion.oschina.tweet.net.HttpApi;
import org.lion.oschina.tweet.util.Constant;
import org.lion.oschina.tweet.util.ToastUtil;
import org.lion.oschina.tweet.util.Utils;
import org.lion.oschina.tweet.util.XmlUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      ${TODO}
 */
public class NewTweetFragment extends BaseFragment implements AdapterView.OnItemClickListener,PullToRefreshBase.OnRefreshListener {


    @Bind(R.id.ptr_tweet_refresh)
    PullToRefreshListView mPtrListView;

    public NewTweetAdapter mAdapter;
    public List<Tweet>     mList;

    int    pageIndex = 0;
    String uid       = "0";
    public StringCallback mCallback;
    public boolean        isLoadMore;


    @Override
    protected Object loadDataThread() {
        loadData();
        return mList;
    }

    public void loadData() {

        if (mCallback == null) {
            mCallback = new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int i) {
                    ToastUtil.showToast("请求失败");
                    if (mPtrListView != null) {

                        mPtrListView.onRefreshComplete();
                    }
                }

                @Override
                public void onResponse(String response, int i) {

                    if (mLoadPager != null) {
                        ToastUtil.showToast("请求成功");
                        TweetsList tweetsList = XmlUtils.toBean(TweetsList.class, response);
                        mList = tweetsList.getList();

                        Utils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                mLoadPager.currentState = mLoadPager.checkData(mList);
                                mLoadPager.showPage();
                                if (isLoadMore) {
                                    loadMore();
                                } else {
                                    refresh();
                                }

                                if (mPtrListView != null) {
                                    mPtrListView.onRefreshComplete();
                                }

                            }
                        });
                    }
                }
            };
        }
        System.out.println(pageIndex);
        HttpApi.getTweetList(pageIndex, uid, mCallback);
    }


    public void loadMore() {
        if (mAdapter == null) {
            mAdapter = new NewTweetAdapter();
            mAdapter.updateDatas(mList);
            mPtrListView.setAdapter(mAdapter);
        } else {
            mAdapter.addDatas(mList);
        }
    }

    public void refresh() {

        if (mAdapter == null) {
            mAdapter = new NewTweetAdapter();
            mAdapter.updateDatas(mList);
            mPtrListView.setAdapter(mAdapter);
        } else {
            mAdapter.updateDatas(mList);
        }
    }

    @Override
    protected View createView() {
        View view = View.inflate(MyApplication.context, R.layout.fragment_tweet_refresh, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化控件
        ButterKnife.bind(this, view);
        mPtrListView = (PullToRefreshListView) view.findViewById(R.id.ptr_tweet_refresh);
        mPtrListView.setMode(PullToRefreshBase.Mode.BOTH);

        mPtrListView.setOnRefreshListener(this);
        mPtrListView.setOnItemClickListener(this);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MyApplication.context, TweetDetailActivity.class);
        Tweet tweet = mAdapter.getShowItems().get((int) id);
        intent.putExtra(Constant.TWEET_DETAIL, tweet.getId());
        startActivity(intent);

    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        switch (refreshView.getCurrentMode()) {

            case PULL_FROM_START:
                System.out.println("refresh");
                isLoadMore = false;
                pageIndex = 0;
                loadData();
                break;

            case PULL_FROM_END:
                System.out.println("more");
                isLoadMore = true;
                pageIndex++;
                loadData();
                break;

            default:
                break;

        }
    }
}
