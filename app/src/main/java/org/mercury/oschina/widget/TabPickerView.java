package org.mercury.oschina.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by wang.zhonghao on 2017/9/5.
 */

public class TabPickerView extends FrameLayout {
    public TabPickerView(@NonNull Context context) {
        super(context);
    }

    public TabPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabPickerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
