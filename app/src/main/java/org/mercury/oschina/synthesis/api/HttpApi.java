package org.mercury.oschina.synthesis.api;

import org.mercury.oschina.explorer.bean.FindUserBean;
import org.mercury.oschina.synthesis.bean.BlogResponse;
import org.mercury.oschina.synthesis.bean.HotListBean;
import org.mercury.oschina.synthesis.bean.NewsResponse;
import org.mercury.oschina.synthesis.utils.RetrofitUtil;

import retrofit2.Callback;

/**
 * Created by more on 2016-08-16 19:15:44.
 */
public class HttpApi {

    public static void getNewsList(String token, Callback<NewsResponse> callback) {
        RetrofitUtil.createHttpApiInstance().getNewsList(token).enqueue(callback);

    }


    public static void getHotList(int catalog, int pageIndex, int pageSize, Callback<HotListBean> callback) {
        RetrofitUtil.createHttpApiInstance().getHotList(catalog, pageIndex, pageSize).enqueue(callback);
    }

    public static void getNewestBlogList(int catalog, String pageToken, Callback<BlogResponse> callback) {
        RetrofitUtil.createHttpApiInstance().getBlogList(catalog, pageToken).enqueue(callback);
    }

    public static void getOscUser(String name, Callback<FindUserBean> callback) {
        RetrofitUtil.createHttpApiInstance().getOscUser(name).enqueue(callback);
    }


}
