package org.lion.oschina.general.ui;

import com.orhanobut.logger.Logger;

import org.lion.oschina.general.adapter.BasicAdapter;
import org.lion.oschina.general.adapter.BlogAdapter;
import org.lion.oschina.general.api.HttpApi;
import org.lion.oschina.general.api.OsChinaApi;
import org.lion.oschina.general.bean.BlogListBean;
import org.lion.oschina.general.utils.RetrofitUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by more on 2016-08-15 15:03:38.
 */
public class BlogNewestFragment extends BlogFragment {
    @Override
    protected void onLoadData() {
        HttpApi.getNewestBlogList(OsChinaApi.CATALOG_BLOG_NORMAL,token,mCallback);
    }
}
