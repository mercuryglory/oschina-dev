package org.mercury.oschina.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wang.zhonghao on 2017/9/27.
 */

public class BottomDialog extends BottomSheetDialog {

    private BottomSheetBehavior mBehavior;

    public BottomDialog(@NonNull Context context) {
        super(context);
    }

    public BottomDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initView(view);
    }

    @Override
    public void show() {
        super.show();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void initView(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) parent
                .getLayoutParams();
        mBehavior = (BottomSheetBehavior) layoutParams.getBehavior();
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }
}
