package org.lion.oschina.general.ui;

import org.lion.oschina.general.api.HttpApi;
import org.lion.oschina.general.api.OsChinaApi;

/**
 * Created by more on 2016-08-15 15:03:38.
 */
public class BlogHeatFragment extends BlogFragment {
    @Override
    protected void onLoadData() {
        HttpApi.getNewestBlogList(OsChinaApi.CATALOG_BLOG_HEAT,token,mCallback);

    }
}
