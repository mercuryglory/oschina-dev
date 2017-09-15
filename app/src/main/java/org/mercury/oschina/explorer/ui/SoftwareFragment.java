package org.mercury.oschina.explorer.ui;

import org.mercury.oschina.R;
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
                new PageInfo(array[0], SoftwareListFragment.class, null),
                new PageInfo(array[1], SoftwareListFragment.class, null),
                new PageInfo(array[2], SoftwareListFragment.class, null),
                new PageInfo(array[3], SoftwareListFragment.class, null),
                new PageInfo(array[4], SoftwareListFragment.class, null)
        };
        return infos;
    }

}
