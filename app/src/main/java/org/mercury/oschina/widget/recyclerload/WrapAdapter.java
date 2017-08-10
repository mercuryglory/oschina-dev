package org.mercury.oschina.widget.recyclerload;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * created by Mercury at 2016/11/20
 * descript:带脚布局（加载更多）的RecyclerView包装适配器,需要将真正处理数据的适配器当作参数传入
 */
public class WrapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View
        .OnClickListener, View.OnLongClickListener {

    private RecyclerView.Adapter adapter;

    private HaoRecyclerView haoRecyclerView;

    private LoadingMoreFooter loadingMoreFooter;

    private static final int DEFAULT = 0;
    private static final int FOOTER  = -1;

    public WrapAdapter(HaoRecyclerView haoRecyclerView, LoadingMoreFooter loadingMoreFooter,
                       RecyclerView.Adapter adapter) {
        this.haoRecyclerView = haoRecyclerView;
        this.loadingMoreFooter = loadingMoreFooter;
        this.adapter = adapter;
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

    /**
     * 当前布局是否为Footer
     *
     * @param position
     * @return
     */
    public boolean isFooter(int position) {
        return position == getItemCount() - 1;
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
        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnLongClickListener(this);
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
            return 1 + adapter.getItemCount();
        } else {
            return 1;
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

    @Override
    public void onClick(View view) {
        int pos = haoRecyclerView.getChildAdapterPosition(view);
        if (onItemClickListener != null && !isFooter(pos)) {
            onItemClickListener.onItemClick(null, view, pos, 0);

        }
    }


    @Override
    public boolean onLongClick(View view) {
        int pos = haoRecyclerView.getChildAdapterPosition(view);
        if (onItemLongClickListener != null && !isFooter(pos)) {
            onItemLongClickListener.onItemLongClick(null, view, pos, 0);

        }
        return true;
    }

    //点击
    AdapterView.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //长按
    AdapterView.OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener
                                                   onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    private class SimpleViewHolder extends RecyclerView.ViewHolder {
        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }
}