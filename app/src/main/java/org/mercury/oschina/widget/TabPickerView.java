package org.mercury.oschina.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mercury.oschina.R;
import org.mercury.oschina.bean.SubTab;

import java.util.List;

/**
 * 动态栏目View 请通过{@link #setTabPickerManager(TabPickerDataManager)}来设置活动数据和原始数据，数据
 * 对象根据需要实现{@link Object#hashCode()}和{@link Object#equals(Object)}方法，因为非活动数据是通过使用
 * {@link List#contains(Object)}方法从原始数据剔除活动数据实现的。
 * <p>
 * <p>活动动态栏目的添加、删除、移动、选择通过{@link OnTabPickingListener}来实现的，你可以通过方法
 * {@link #setOnTabPickingListener(OnTabPickingListener)}来监听。
 * <p>
 * <p>通过{@link #show(int)}和{@link #hide()}方法来显示隐藏动态栏目界面。
 * <p>
 * <p>通过{@link #onTurnBack()}响应回退事件。
 * <p>
 * <p>Created by thanatosx on 16/10/27.
 */

public class TabPickerView extends FrameLayout {
    private TextView         mViewDone;
    private TextView         mViewOperator;
    private RecyclerView     mRecyclerActive;
    private RecyclerView     mRecyclerInactive;
    private LinearLayout     mLayoutWrapper;
    private RelativeLayout   mLayoutTop;
    private LinearLayout     mViewWrapper;
    private NestedScrollView mViewScroller;
    private ItemTouchHelper  mItemTouchHelper;

    private TabAdapter<TabAdapter.ViewHolder> mActiveAdapter;
    private TabAdapter<TabAdapter.ViewHolder> mInactiveAdapter;

//    private TabPickerDataManager mTabManager;
    private OnTabPickingListener mTabPickingListener;

    private Action1<ViewPropertyAnimator> mOnShowAnimator;
    private Action1<ViewPropertyAnimator> mOnHideAnimator;

    private int mSelectedIndex = 0;

    public TabPickerView(@NonNull Context context) {
        this(context, null);
    }

    public TabPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabPickerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWidgets();
    }

    private void initWidgets() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_tab_picker, this, false);
        mRecyclerActive = (RecyclerView) view.findViewById(R.id.view_recycler_active);
        mRecyclerInactive = (RecyclerView) view.findViewById(R.id.view_recycler_inactive);
        mViewScroller = (NestedScrollView) view.findViewById(R.id.view_scroller);
        mLayoutTop = (RelativeLayout) view.findViewById(R.id.layout_top);
        mViewWrapper = (LinearLayout) view.findViewById(R.id.view_wrapper);
        mViewDone = (TextView) view.findViewById(R.id.tv_done);
        mViewOperator = (TextView) view.findViewById(R.id.tv_operator);
        mLayoutWrapper = (LinearLayout) view.findViewById(R.id.layout_wrapper);
        mViewDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewDone.getText().toString().equals("排序删除")) {
                    // TODO: 2017/9/6
                } else {

                }
            }
        });

        addView(view);
    }

    /**
     * The Tab Picking Listener Interface
     */
    public interface OnTabPickingListener {
        /**
         * 单击选择某个tab
         *
         * @param position select a tab
         */
        void onSelected(int position);

        /**
         * 删除某个tab
         *
         * @param position the moved tab's position
         * @param tab      the moved tab
         */
        void onRemove(int position, SubTab tab);

        /**
         * 添加某个tab
         *
         * @param tab the inserted tab
         */
        void onInsert(SubTab tab);

        /**
         * 交换tab
         *
         * @param op      the mover's position
         * @param np      the swapper's position
         */
        void onMove(int op, int np);

        /**
         * 重新持久化活动的tabs
         *
         * @param activeTabs the actived tabs
         */
        void onRestore(List<SubTab> activeTabs);
    }

    public interface Action1<T>{
        void call(T t);
    }

    public void setOnShowAnimator(Action1<ViewPropertyAnimator> l) {
        this.mOnShowAnimator = l;
    }

    public void setOnHideAnimator(Action1<ViewPropertyAnimator> l) {
        this.mOnHideAnimator = l;
    }

    public void show(int selectedIndex) {
        int tempIndex = mSelectedIndex;
        mSelectedIndex = selectedIndex;
        // TODO: 2017/9/6
    }


    /**
     * Class TabAdapter
     *
     * @param <VH>
     */
    private abstract class TabAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView
            .Adapter {

        private View.OnClickListener mClickDeleteListener;
        private View.OnClickListener mClickTabItemListener;
        private View.OnTouchListener mTouchTabItemListener;

        private Action1<Integer> mDeleteItemAction;
        private Action1<Integer> mSelectItemAction;
        Action1<ViewHolder> mBindViewObserver;

        private boolean isEditMode = false;
        List<SubTab> items;

        TabAdapter(List<SubTab> items) {
            this.items = items;
        }

        SubTab removeItem(int position) {
            SubTab subTab = items.get(position);
            notifyItemRemoved(position);
            return subTab;
        }

        void addItem(SubTab subTab) {
            items.add(subTab);
            notifyItemInserted(items.size() - 1);
        }

        void addItem(SubTab subTab, int position) {
            items.add(position, subTab);
            notifyItemInserted(position);
        }

        SubTab getItem(int position) {
            if (position < 0 || position >= items.size()) {
                return null;
            }
            return items.get(position);
        }

        /**
         * Tab View Holder
         */
        class ViewHolder extends RecyclerView.ViewHolder {

            TextView  mViewTab;
            TextView  mViewBubble;
            ImageView mViewDel;

            public ViewHolder(View itemView) {
                super(itemView);
                mViewTab = (TextView) itemView.findViewById(R.id.tv_content);
                mViewBubble = (TextView) itemView.findViewById(R.id.tv_bubble);
                mViewDel = (ImageView) itemView.findViewById(R.id.iv_delete);
            }
        }

    }


}
