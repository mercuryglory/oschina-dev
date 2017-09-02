package org.mercury.oschina.synthesis.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wang.zhonghao on 2017/9/2.
 */

public class News implements Parcelable {


    /**
     * author : 王练
     * authorid : 2896879
     * commentCount : 0
     * id : 88314
     * object : 0
     * pubDate : 2017-09-02 08:14:42
     * title : 码云推荐 | 严格遵循雪花算法的分布式 ID 生成器 
     * type : 0
     * url : https://gitee.com/darkranger/id-generator
     */

    private String author;
    private int    authorid;
    private int    commentCount;
    private int    id;
    private int    object;
    private String pubDate;
    private String title;
    private int    type;
    private String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObject() {
        return object;
    }

    public void setObject(int object) {
        this.object = object;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeInt(this.authorid);
        dest.writeInt(this.commentCount);
        dest.writeInt(this.id);
        dest.writeInt(this.object);
        dest.writeString(this.pubDate);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeString(this.url);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.author = in.readString();
        this.authorid = in.readInt();
        this.commentCount = in.readInt();
        this.id = in.readInt();
        this.object = in.readInt();
        this.pubDate = in.readString();
        this.title = in.readString();
        this.type = in.readInt();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
