package org.lion.oschina.general.api;

import org.lion.oschina.explorer.bean.FindUserBean;
import org.lion.oschina.general.bean.BlogListBean;
import org.lion.oschina.general.bean.HotListBean;
import org.lion.oschina.general.bean.NewsListBean;
import org.lion.oschina.general.utils.RetrofitUtil;

import retrofit2.Callback;

/**
 * Created by more on 2016-08-16 19:15:44.
 */
public class HttpApi {

    public static void getNewsList(String token, Callback<NewsListBean> callback) {
        RetrofitUtil.createHttpApiInstance().getNewsList(token).enqueue(callback);

    }


    public static void getHotList(int catalog, int pageIndex, int pageSize, Callback<HotListBean> callback) {
        RetrofitUtil.createHttpApiInstance().getHotList(catalog, pageIndex, pageSize).enqueue(callback);
    }

    public static void getNewestBlogList(int catalog, String pageToken, Callback<BlogListBean> callback) {
        RetrofitUtil.createHttpApiInstance().getBlogList(catalog, pageToken).enqueue(callback);
    }

    public static void getOscUser(String name, Callback<FindUserBean> callback) {
        RetrofitUtil.createHttpApiInstance().getOscUser(name).enqueue(callback);
    }


}
