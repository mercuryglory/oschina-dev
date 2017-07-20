package org.mercury.oschina.synthesis.ui.fragment;

import org.mercury.oschina.synthesis.api.HttpApi;
import org.mercury.oschina.synthesis.api.OsChinaApi;

/**
 * Created by more on 2016-08-15 15:03:38.
 */
public class BlogHeatFragment extends BlogFragment {
    @Override
    protected void onLoadData() {
        HttpApi.getNewestBlogList(OsChinaApi.CATALOG_BLOG_HEAT,token,mCallback);

    }
}
