package org.mercury.oschina.http;

import org.mercury.oschina.bean.AccessToken;
import org.mercury.oschina.bean.ResultBean;
import org.mercury.oschina.explorer.bean.SoftwareCatalogResponse;
import org.mercury.oschina.explorer.bean.SoftwareDetail;
import org.mercury.oschina.explorer.bean.SoftwareResponse;
import org.mercury.oschina.synthesis.bean.BlogResponse;
import org.mercury.oschina.synthesis.bean.NewsDetail;
import org.mercury.oschina.synthesis.bean.NewsResponse;
import org.mercury.oschina.synthesis.bean.PostResponse;
import org.mercury.oschina.tweet.bean.CommentResponse;
import org.mercury.oschina.tweet.bean.Tweet;
import org.mercury.oschina.tweet.bean.TweetResponse;
import org.mercury.oschina.tweet.bean.User;
import org.mercury.oschina.user.bean.ActiveResponse;
import org.mercury.oschina.user.bean.FavoriteResponse;
import org.mercury.oschina.user.bean.MessageResponse;
import org.mercury.oschina.user.bean.ProjectResponse;
import org.mercury.oschina.user.bean.UserResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Mercury on 2017/7/30.
 */

public interface HttpApi {

    @GET("token")
    Call<AccessToken> getAccessToken(@QueryMap Map<String, String> params);

    @GET("tweet_list")
    Call<TweetResponse> getTweetList(@Query("user") long user, @Query("page") int page);

    @GET("tweet_detail")
    Call<Tweet> getTweetDetail(@Query("id") int id);

    @GET("comment_list")
    Call<CommentResponse> getCommentList(@Query("id") int id, @Query("catalog") int catalog, @Query
            ("page") int page);

    @GET("blog_list")
    Call<BlogResponse> getBlogNewList(@Query("page") int page);

    @GET("blog_recommend_list")
    Call<BlogResponse> getBlogHotList(@Query("page") int page);

    @GET("news_list")
    Call<NewsResponse> getNewsList(@Query("catalog") int catalog, @Query("page") int page);

    @GET("post_list")
    Call<PostResponse> getPostList(@Query("catalog") int catalog, @Query("page") int page);

    @GET("my_information")
    Call<User> getMyInfo();

    @GET("message_list")
    Call<MessageResponse> getPrivateMsg(@Query("page") int page);

    @GET("user_blog_list")
    Call<ProjectResponse> getUserBlogList(@Query("authoruid") int authoruid, @Query("page") int
            page);


    /**
     *
     * @param catalog 类别ID [0、1所有动态,2提到我的,3评论,4我自己 ]
     * @param userId
     * @param page
     * @return
     */
    @GET("active_list")
    Call<ActiveResponse> getActiveList(@Query("catalog") int catalog, @Query("user") int userId,
                                       @Query("page") int page);

    @GET("user_information")
    Call<User> getOtherUserInfo(@Query("friend") long userId);

    @GET("favorite_list")
    Call<FavoriteResponse> getFavoriteList(@Query("type") int type, @Query("page") int page);

    /**
     *
     * @param relation 0-粉丝|1-关注的人(default=0)
     * @param page
     * @return
     */
    @GET("friends_list")
    Call<UserResponse> getUserList(@Query("relation") int relation, @Query("page") int page);

    @GET("news_detail")
    Call<NewsDetail> getNewsDetail(@Query("id") long id);

    /**
     * @param userId   对方用户id
     * @param relation 0-取消关注，1-加关注
     * @return
     */
    @POST("update_user_relation")
    Call<ResultBean> addRelation(@Query("friend") long userId, @Query("relation") int relation);

    /**
     *推荐|time-最新|view-热门|cn-国产 软件列表
     * @param type
     * @param page
     * @return
     */
    @GET("project_list")
    Call<SoftwareResponse> getSoftwareList(@Query("type") String type, @Query("page") int page);

    /**
     * 获取软件分类列表（2级）
     * @param tag
     * @return
     */
    @GET("project_catalog_list")
    Call<SoftwareCatalogResponse> getSoftwareTypes(@Query("tag") int tag);

    /**
     * tag下的软件列表
     * @param tag
     * @return
     */
    @GET("project_tag_list")
    Call<SoftwareResponse> getSoftTagList(@Query("tag") int tag);

    /**
     * 软件详情
     *
     * @param ident 软件特征名称
     * @param userId 用户名 传已登录的本机用户名会显示该软件是否被收藏
     * @return
     */
    @GET("project_detail")
    Call<SoftwareDetail> getSoftware(@Query("ident") String ident, @Query("user") long userId);

    @GET("favorite_add")
    Call<ResultBean> addFavorite(@Query("id") long id, @Query("type") int type);

    @GET("favorite_remove")
    Call<ResultBean> cancleFavorite(@Query("id") long id, @Query("type") int type);



}
