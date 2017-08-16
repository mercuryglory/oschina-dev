package org.mercury.oschina.widget.recyclerload;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * created by Mercury at 2016/11/20
 * descript:带脚布局（加载更多）的RecyclerView包装适配器,需要将真正处理数据的适配器当作参数传入
 */
public class WrapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView.Adapter adapter;

    private HaoRecyclerView haoRecyclerView;

    private LoadingMoreFooter loadingMoreFooter;

    private static final int DEFAULT = 0;
    private static final int FOOTER  = -1;

    private OnClickListener mOnClickListener;
    private OnLongClickListener mOnLongClickListener;


    public WrapAdapter(HaoRecyclerView haoRecyclerView, LoadingMoreFooter loadingMoreFooter,
                       RecyclerView.Adapter adapter) {
        this.haoRecyclerView = haoRecyclerView;
        this.loadingMoreFooter = loadingMoreFooter;
        this.adapter = adapter;
        initListener();
    }

    private void initListener() {
        mOnClickListener=new OnClickListener() {
            @Override
            public void onClick(int position, long itemId) {
                if (onItemClickListener != null && !isFooter(position)) {
                    onItemClickListener.onItemClick(position, itemId);
                }
            }
        };

        mOnLongClickListener=new OnLongClickListener() {
            @Override
            public boolean onLongClick(int position, long itemId) {
                if (onItemLongClickListener != null && !isFooter(position)) {
                    onItemLongClickListener.onItemLongClick(position, itemId);
                    return true;
                }
                return false;
            }
        };
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (getItemViewType(position) == RecyclerView.INVALID_TYPE ||
                            getItemViewType(position) == RecyclerView.INVALID_TYPE - 1)
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && isFooter(holder.getLayoutPosition())) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams)
                    lp;
            p.setFullSpan(true);
        }
    }

    public static abstract class OnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            onClick(holder.getAdapterPosition(),holder.getItemId());
        }

        public abstract void onClick(int position, long itemId);
    }

    public static abstract class OnLongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            return onLongClick(holder.getAdapterPosition(), holder.getItemId());
        }

        public abstract boolean onLongClick(int position, long itemId);
    }


    /**
     * 当前布局是否为Footer
     *
     * @param position
     * @return
     */
    public boolean isFooter(int position) {
        if(adapter.getItemCount()>=20)
            return position == getItemCount() - 1;
        return position == getItemCount();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER) {
            return new SimpleViewHolder(loadingMoreFooter);
        }
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(holder);
        holder.itemView.setOnClickListener(mOnClickListener);
        holder.itemView.setOnLongClickListener(mOnLongClickListener);
        if (adapter != null) {
            int count = adapter.getItemCount();
            if (position < count) {
                adapter.onBindViewHolder(holder, position);
                return;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (adapter != null) {
            //如果单次数据大于等于20条,才会出现loading的尾布局
            if (adapter.getItemCount() >= 20)
                return adapter.getItemCount() + 1;
            return adapter.getItemCount();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooter(position)) {
            return FOOTER;
        }
        if (adapter != null) {
            int count = adapter.getItemCount();
            if (position < count) {
                return adapter.getItemViewType(position);
            }
        }
        return DEFAULT;
    }

    @Override
    public long getItemId(int position) {
        if (adapter != null && position >= 0) {
            int adapterCount = adapter.getItemCount();
            if (position < adapterCount) {
                return adapter.getItemId(position);
            }
        }
        return -1;
    }

    //点击
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position,long itemId);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //长按
    OnItemLongClickListener onItemLongClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(int position,long itemId);
    }


    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    private class SimpleViewHolder extends RecyclerView.ViewHolder {
        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }
}