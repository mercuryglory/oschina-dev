package org.mercury.oschina.widget.recyclerload;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * created by wang.zhonghao at 2016/11/20
 * descript:带上拉加载更多的recyclerview
 */
public class HaoRecyclerView extends RecyclerView {

    private int downY;
    private int moveY;
    private int upY;

    private Context mContext;

    private OnLoadMoreListener mOnLoadMoreListener;
    //是否可加载更多
    private boolean canloadMore = true;

    private Adapter mAdapter;

    private Adapter mWrapAdapter;

    private boolean isLoadingData = false;
    //加载更多布局
    private LoadingMoreFooter loadingMoreFooter;

    public HaoRecyclerView(Context context) {
        this(context, null);
    }

    public HaoRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HaoRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LoadingMoreFooter footView = new LoadingMoreFooter(mContext);
        addFootView(footView);
        footView.setGone();
    }


    //点击监听
    public void setOnItemClickListener(WrapAdapter.OnItemClickListener onItemClickListener) {
        if (mWrapAdapter != null && mWrapAdapter instanceof WrapAdapter) {
            ((WrapAdapter) mWrapAdapter).setOnItemClickListener(onItemClickListener);
        }
    }


    //长按监听
    public void setOnItemLongClickListener(WrapAdapter.OnItemLongClickListener listener) {
        if (mWrapAdapter != null && mWrapAdapter instanceof WrapAdapter) {
            ((WrapAdapter) mWrapAdapter).setOnItemLongClickListener(listener);
        }
    }

    /**
     * 底部加载更多的布局的初始化
     *
     * @param view
     */
    public void addFootView(LoadingMoreFooter view) {
        loadingMoreFooter = view;
    }

    //设置底部加载中效果
    public void setFootLoadingView(View view) {
        if (loadingMoreFooter != null) {
            loadingMoreFooter.addFootLoadingView(view);
        }
    }

    //设置底部到底了布局
    public void setFootEndView(View view) {
        if (loadingMoreFooter != null) {
            loadingMoreFooter.addFootEndView(view);
        }
    }

    //下拉刷新后初始化底部状态
    public void refreshComplete() {
        if (loadingMoreFooter != null) {
            loadingMoreFooter.setGone();
        }
        isLoadingData = false;
        canloadMore = true;
    }

    public void loadMoreComplete() {
        if (loadingMoreFooter != null) {
            loadingMoreFooter.setGone();
        }
        isLoadingData = false;
    }


    //到底了
    public void loadMoreEnd() {
        if (loadingMoreFooter != null) {
            loadingMoreFooter.setEnd();
        }
    }

    //设置是否可加载更多
    public void setCanloadMore(boolean flag) {
        canloadMore = flag;
    }

    //设置加载更多监听
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mOnLoadMoreListener = listener;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter != null) {
            mAdapter = adapter;
            mWrapAdapter = new WrapAdapter(this, loadingMoreFooter, adapter);
            super.setAdapter(mWrapAdapter);
            mAdapter.registerAdapterDataObserver(mDataObserver);
        }

    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        //不再加载数据时,即isLoadingData=false时,才去判断是否满足加载更多的条件
        if (state == RecyclerView.SCROLL_STATE_IDLE && mOnLoadMoreListener != null &&
                !isLoadingData && canloadMore && isPullUp()) {

            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = last(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
            }

            Log.i("Mercury", "firstVisibleItemPosition: "+((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition());
            Log.i("Mercury", "lastVisibleItemPosition: "+((LinearLayoutManager) layoutManager).findLastVisibleItemPosition());
            Log.i("Mercury", "getChildCount: "+((LinearLayoutManager) layoutManager).getChildCount());
            Log.i("Mercury", "getItemCount: "+((LinearLayoutManager) layoutManager).getItemCount());

            if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 1) {
                if (loadingMoreFooter != null) {
                    loadingMoreFooter.setVisible();
                }
                isLoadingData = true;
                mOnLoadMoreListener.onLoadMore();
            }
        }
    }


    //取到最后的一个节点
    private int last(int[] lastPositions) {
        int last = lastPositions[0];
        for (int value : lastPositions) {
            if (value > last) {
                last = value;
            }
        }
        return last;
    }


    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver
            () {
        @Override
        public void onChanged() {
            mWrapAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                upY = (int) ev.getRawY();
            default:
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    //是否是上拉操作
    public boolean isPullUp() {
        return (downY - upY) >= 0;
    }

}
