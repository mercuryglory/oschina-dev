package org.mercury.oschina.explorer.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseFragment;
import org.mercury.oschina.widget.EmptyLayout;
import org.mercury.oschina.widget.ScrollLayout;

import butterknife.Bind;

/**
 * Created by Mercury on 2017/9/15.
 * 软件分类列表，一级二级三级列表
 */

public class SoftwareCatalogFragment extends BaseFragment {

    @Bind(R.id.lv_catalog)
    ListView     lvCatalog;
    @Bind(R.id.lv_tag)
    ListView     lvTag;
    @Bind(R.id.lv_software)
    ListView     lvSoftware;
    @Bind(R.id.scrolllayout)
    ScrollLayout scrolllayout;
    @Bind(R.id.error_layout)
    EmptyLayout  errorLayout;

    public static final int SCREEN_CATALOG = 0;
    public static final int SCREEN_TAG = 1;
    public static final int SCREEN_SOFTWARE = 2;

    private static int currentScreen = SCREEN_CATALOG;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_software_catalog;
    }

    private AdapterView.OnItemClickListener mCatalogListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //加载二级分类
            currentScreen = SCREEN_CATALOG;
            scrolllayout.scrollToScreen(SCREEN_CATALOG);
        }
    };

    @Override
    protected void initWidget(View root) {
        lvCatalog.setOnItemClickListener(mCatalogListener);

    }
}
