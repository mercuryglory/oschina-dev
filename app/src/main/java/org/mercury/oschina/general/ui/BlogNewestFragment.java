package org.mercury.oschina.general.ui;

import org.mercury.oschina.general.api.HttpApi;
import org.mercury.oschina.general.api.OsChinaApi;

/**
 * Created by more on 2016-08-15 15:03:38.
 */
public class BlogNewestFragment extends BlogFragment {
    @Override
    protected void onLoadData() {
        HttpApi.getNewestBlogList(OsChinaApi.CATALOG_BLOG_NORMAL,token,mCallback);
    }
}
