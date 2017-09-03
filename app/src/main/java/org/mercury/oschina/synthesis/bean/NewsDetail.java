package org.mercury.oschina.synthesis.bean;

import android.os.Parcel;

import org.mercury.oschina.bean.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mercury on 2017/9/3.
 */

public class NewsDetail extends BaseBean {


    /**
     * author : zhenruyan
     * id : 88344
     * authorid : 998789
     * title : AvocadoDB v0.0.1 发布，多模型 NoSQL 数据库
     * body : <style type='text/css'>pre {white-space:pre-wrap;word-wrap:break-word;
     * }</style><p><img alt="" src="https://static.oschina
     * .net/uploads/space/2017/0903/193428_EOio_998789.jpg"></p>
     <h3>AvocadoDB</h3>
     <p>v0.1</p>
     <p>avocadodb是具有灵活的数据模型<br>原生支持ｒｅｓｔｆｕｌ<br>文档、图表和k/v多结构的开源数据库。<br>使用Aql构建高性能应用程序<br>方便的sql
     查询语言或JavaScript扩展。</p>
     <h3>AvocadoDB的主要特点</h3>
     <ul>
     <li><p><strong>多模型</strong>: 文档,图形，k/v &nbsp;多种数据模型由你选择</p></li>
     <li><p><strong>AQL</strong>:灵活方便的AQL查询</p><p><strong>restful-api</strong>&nbsp;
     :方便各种前后端直接调用</p><p><strong>中文化的web管理界面</strong>:不在迷茫！开箱即用！</p></li>
     </ul>
     <h3>V0.0.1版本</h3>
     <ul>
     <li><p>汉化了主要界面</p></li>
     <li><p>汉化了ＡＱＬ语言文档</p></li>
     </ul>
     <p><img alt="" src="https://static.oschina
     .net/uploads/space/2017/0903/193759_Kvwz_998789.png"></p>
     <p>下面介绍以下高级操作:</p>
     <ul>
     <li><p><strong>FOR</strong>:遍历数组的所有元素。</p></li>
     <li><p><strong>RETURN</strong>:生成查询的结果。</p></li>
     <li><p><strong>FILTER</strong>:将结果限制为与任意逻辑条件匹配的元素。</p></li>
     <li><p><strong>SORT</strong>:强制排序已生成的中间结果的数组。</p></li>
     <li><p><strong>LIMIT</strong>:将结果中的元素数减少到至多指定的数字, 可以选择跳过元素 (分页)。</p></li>
     <li><p><strong>LET</strong>:将任意值赋给变量。</p></li>
     <li><p><strong>COLLECT</strong>:按一个或多个组条件对数组进行分组。也可以计数和聚合。</p></li>
     <li><p><strong>REMOVE</strong>:从集合中移除文档。</p></li>
     <li><p><strong>UPDATE</strong>:部分更新集合中的文档。</p></li>
     <li><p><strong>REPLACE</strong>:完全替换集合中的文档。</p></li>
     <li><p><strong>INSERT</strong>:将新文档插入到集合中。</p></li>
     <li><p><strong>UPSERT</strong>:更新/替换现有文档, 或在不存在的情况下创建它。</p></li>
     <li><p><strong>WITH</strong>:指定查询中使用的集合 (仅在查询开始时)。</p></li>
     </ul>
     <p>相关地址：</p>
     <p><a href="http://https//github.com/avocadodb/avocadodb/blob/master/Documentation/AQL.md" target="_blank" rel="nofollow">github</a></p>
     <p><a href="http://freeidea.win/" target="_blank" rel="nofollow">我的博客</a></p>
     * pubDate : 2017-09-03 19:37:39
     * favorite : 0
     * url : https://www.oschina.net/news/88344/avocadodb-0-0-1
     * relativies : [{"title":"codefont v0.0.1 发布，Windows 一键安装所有字体","url":"https://www.oschina
     * .net/news/88252/codefont-0-0-1"},{"title":"MapBox-gl-js v0.0.1 dev 和 0.0.0 dev 发布",
     * "url":"https://www.oschina.net/news/87730/mapbox-gl-js-v-0-0-1-dev"},{"title":"Scylla
     * 1.7.4 发布，高吞吐低延迟的 NoSQL 数据库","url":"https://www.oschina.net/news/87286/scylla-1-7-4"},
     * {"title":" RethinkDB 2.3.6 发布，NoSQL 分布式文档数据库","url":"https://www.oschina
     * .net/news/86904/rethinkdb-2-3-6"},{"title":"iBoxDB 2.11/2.6 发布，嵌入式 NoSQL 数据库",
     * "url":"https://www.oschina.net/news/85580/iboxdb-2-11-and-2-6"},{"title":"NoSQL 没毛病，为什么
     * MySQL 还是\u201c王\u201d？","url":"https://www.oschina
     * .net/news/84809/nosql-no-problem-why-mysql-is-still-king"},{"title":"ArangoDB 3.1.17
     * 发布，高性能 NoSQL 数据库","url":"https://www.oschina.net/news/83539/arangodb-3-1-17"},
     * {"title":"Apache Gora 0.7 发布，NoSQL 的 ORM 框架","url":"https://www.oschina
     * .net/news/83173/apache-gora-0-7-released"},{"title":"NoSQL 持久层框架 Hibernate OGM 5.1 正式发布",
     * "url":"https://www.oschina.net/news/82475/hibernate-ogm-5-1-final"},{"title":"每日一博 | NoSQL
     * 详细介绍及相关疑问总结","url":"https://my.oschina.net/lzhaoqiang/blog/844710"}]
     * commentCount : 0
     */

    private String author;
    private int                  id;
    private int                  authorid;
    private String               title;
    private String               body;
    private String               pubDate;
    private int                  favorite;
    private String               url;
    private int                  commentCount;
    private List<RelativiesBean> relativies;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public List<RelativiesBean> getRelativies() {
        return relativies;
    }

    public void setRelativies(List<RelativiesBean> relativies) {
        this.relativies = relativies;
    }

    public static class RelativiesBean {
        /**
         * title : codefont v0.0.1 发布，Windows 一键安装所有字体
         * url : https://www.oschina.net/news/88252/codefont-0-0-1
         */

        private String title;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.author);
        dest.writeInt(this.id);
        dest.writeInt(this.authorid);
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeString(this.pubDate);
        dest.writeInt(this.favorite);
        dest.writeString(this.url);
        dest.writeInt(this.commentCount);
        dest.writeList(this.relativies);
    }

    public NewsDetail() {
    }

    protected NewsDetail(Parcel in) {
        super(in);
        this.author = in.readString();
        this.id = in.readInt();
        this.authorid = in.readInt();
        this.title = in.readString();
        this.body = in.readString();
        this.pubDate = in.readString();
        this.favorite = in.readInt();
        this.url = in.readString();
        this.commentCount = in.readInt();
        this.relativies = new ArrayList<RelativiesBean>();
        in.readList(this.relativies, RelativiesBean.class.getClassLoader());
    }

    public static final Creator<NewsDetail> CREATOR = new Creator<NewsDetail>() {
        @Override
        public NewsDetail createFromParcel(Parcel source) {
            return new NewsDetail(source);
        }

        @Override
        public NewsDetail[] newArray(int size) {
            return new NewsDetail[size];
        }
    };
}
