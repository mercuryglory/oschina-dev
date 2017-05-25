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

import butterknife.ButterKnife;


/**
 * 创建者:    Mercury
 * 创建时间:  2016/8/14
 * 描述:      ${TODO}
 */
public class HotTweetFragment extends NewTweetFragment implements AdapterView.OnItemClickListener,
        PullToRefreshBase.OnRefreshListener {

    String uid = "-1";

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
        public void onRefresh(PullToRefreshBase refreshView) {
            switch (refreshView.getCurrentMode()) {

                case PULL_FROM_START:
                    System.out.println("refresh");
                    isLoadMore = false;
                    pageIndex = 0;
                    loadData();
                    break;

                default:
                    break;

            }
        }


//    @Bind(R.id.ptr_tweet_refresh)
//    PullToRefreshListView mPtrListView;
//
//    private NewTweetAdapter mAdapter;
//    private List<Tweet>     mList;
//
//    int    pageIndex = 0;
//    String uid       = "-1";
//    private StringCallback mCallback;
//    private boolean        isLoadMore;
//
//
//    @Override
//    protected Object loadDataThread() {
//
//        loadData();
//        return mList;
//    }
//
//    private void loadData() {
//
//        if (mCallback == null) {
//            mCallback = new StringCallback() {
//                @Override
//                public void onError(Call call, Exception e, int i) {
//                    mPtrListView.setRefreshing(false);
//                    ToastUtil.showToast("请求失败");
//                }
//
//                @Override
//                public void onResponse(String response, int i) {
//
//                    if (mLoadPager != null) {
//                        ToastUtil.showToast("请求成功");
//                        TweetsList tweetsList = XmlUtils.toBean(TweetsList.class, response);
//                        mList = tweetsList.getList();
//
//                        Utils.runOnUIThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mLoadPager.currentState = mLoadPager.checkData(mList);
//                                mLoadPager.showPage();
//                                if (isLoadMore) {
//                                    loadMore();
//                                } else {
//                                    refresh();
//                                }
//
//                                if (mPtrListView != null) {
//                                    mPtrListView.onRefreshComplete();
//                                }
//
//                            }
//                        });
//                    }
//                }
//            };
//        }
//        System.out.println(pageIndex);
//        HttpApi.getTweetList(pageIndex, uid, mCallback);
//    }
//
//    private void loadMore() {
//        if (mAdapter == null) {
//            mAdapter = new NewTweetAdapter();
//            mAdapter.updateDatas(mList);
//            mPtrListView.setAdapter(mAdapter);
//        } else {
//            mAdapter.addDatas(mList);
//        }
//    }
//
//    private void refresh() {
//
//        if (mAdapter == null) {
//            mAdapter = new NewTweetAdapter();
//            mAdapter.updateDatas(mList);
//            mPtrListView.setAdapter(mAdapter);
//        } else {
//            mAdapter.updateDatas(mList);
//        }
//    }
//
//    @Override
//    protected View createView() {
//        View view = View.inflate(AppContext.context, R.layout.fragment_tweet_refresh, null);
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        //初始化控件
//        ButterKnife.bind(this, view);
//        mPtrListView = (PullToRefreshListView) view.findViewById(R.id.ptr_tweet_refresh);
//        ListView listView = mPtrListView.getRefreshableView();
//
//        View footView = View.inflate(AppContext.context, R.layout.footer_tweet_hot, null);
//        listView.addFooterView(footView);
//
//        mPtrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        mPtrListView.setOnRefreshListener(this);
//        mPtrListView.setOnItemClickListener(this);
//
//    }
//
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }
//
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(AppContext.context, TweetDetailActivity.class);
//        Tweet tweet = mAdapter.getShowItems().get((int) id);
//        intent.putExtra(Constant.TWEET_DETAIL, tweet.getId());
//        startActivity(intent);
//
//    }
//
//
//    @Override
//    public void onRefresh(PullToRefreshBase refreshView) {
//        switch (refreshView.getCurrentMode()) {
//
//            case PULL_FROM_START:
//                System.out.println("refresh");
//                isLoadMore = false;
//                pageIndex = 0;
//                loadData();
//                break;
////
////            case PULL_FROM_END:
////                System.out.println("more");
////                isLoadMore = true;
////                pageIndex++;
////                loadData();
////                break;
//
//            default:
//                break;
//
//        }
//    }
}
