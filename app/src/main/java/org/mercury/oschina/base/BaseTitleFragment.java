package org.mercury.oschina.base;

import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewStub;

import org.mercury.oschina.R;
import org.mercury.oschina.widget.TitleBar;

/**
 * Created by Mercury on 2017/7/20.
 */

public abstract class BaseTitleFragment extends BaseFragment {

    TitleBar mTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_title;
    }

    @Override
    protected void onBindViewBefore(View root) {
        super.onBindViewBefore(root);
        ViewStub stub = (ViewStub) root.findViewById(R.id.layout_content);
        stub.setLayoutResource(getContentLayoutId());
        stub.inflate();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mTitleBar = (TitleBar) root.findViewById(R.id.merge_title);
        mTitleBar.setTitle(getTitleRes());
        mTitleBar.setIcon(getIconRes());
        mTitleBar.setIconOnClickListener(getIconClickListener());

    }

    protected abstract @StringRes int getTitleRes();

    protected @DrawableRes int getIconRes() {
        return R.mipmap.btn_search_normal;
    }

    protected View.OnClickListener getIconClickListener() {
        return null;
    }

    protected abstract @LayoutRes int getContentLayoutId();
}
