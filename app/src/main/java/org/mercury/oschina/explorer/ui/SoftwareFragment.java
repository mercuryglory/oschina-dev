package org.mercury.oschina.explorer.ui;

import android.os.Bundle;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseFragment;
import org.mercury.oschina.base.BaseViewPagerFragment;
import org.mercury.oschina.bean.PageInfo;

/**
 * Created by Mercury on 2017/9/15.
 * 开源软件
 */

public class SoftwareFragment extends BaseViewPagerFragment {


    @Override
    public PageInfo[] initPageInfos() {
        String[] array = getResources().getStringArray(R.array.software_tag);
        PageInfo[] infos = new PageInfo[]{
                new PageInfo(array[0], SoftwareCatalogFragment.class, null),
                new PageInfo(array[1], SoftwareListFragment.class, getBundle(SoftwareListFragment.CATALOG_RECOMMEND)),
                new PageInfo(array[2], SoftwareListFragment.class, getBundle(SoftwareListFragment.CATALOG_TIME)),
                new PageInfo(array[3], SoftwareListFragment.class, getBundle(SoftwareListFragment.CATALOG_VIEW)),
                new PageInfo(array[4], SoftwareListFragment.class, getBundle(SoftwareListFragment.CATALOG_CN))
        };
        return infos;
    }

    private Bundle getBundle(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(SoftwareListFragment.CATALOG_TYPE, type);
        return bundle;
    }

    @Override
    public boolean onBackPressed() {
        ViewPagerAdapter adapter = (ViewPagerAdapter) baseViewpager.getAdapter();
        BaseFragment fragment = (BaseFragment) adapter.getItem(baseViewpager.getCurrentItem());
        if (fragment instanceof SoftwareCatalogFragment) {
            return fragment.onBackPressed();
        }
        return super.onBackPressed();
    }
}
