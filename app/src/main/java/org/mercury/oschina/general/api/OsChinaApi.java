package org.mercury.oschina.general.api;

import org.mercury.oschina.explorer.bean.FindUserBean;
import org.mercury.oschina.general.bean.BlogListBean;
import org.mercury.oschina.general.bean.HotListBean;
import org.mercury.oschina.general.bean.NewsListBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by more on 2016-08-15 18:27:38.
 */
public interface OsChinaApi {

    //pageIndex=0&catalog=1&pageSize=20
    @GET("action/apiv2/news")
    Call<NewsListBean> getNewsList(@Query("pageToken") String pageToken);

    @GET("action/api/rock_rock")
    Call<NewsListBean> getShake(@Query("type") int type);

    @GET("action/apiv2/news_list")
    Call<HotListBean> getHotList(@Query("catalog") int catalog,
                                 @Query("pageIndex") int pageIndex,
                                 @Query("pageSize") int pageSize
                                 );



    int CATALOG_BLOG_NORMAL = 1; // 最新
    int CATALOG_BLOG_HEAT = 2; // 最热
    int CATALOG_BLOG_RECOMMEND = 3;//推荐
    @GET("action/apiv2/blog")
    Call<BlogListBean> getBlogList(
            @Query("catalog") int catalog,
            @Query("pageToken") String pageToken);



    @GET("action/apiv2/find_user")
    Call<FindUserBean> getOscUser(
            @Query("name") String name);
}
