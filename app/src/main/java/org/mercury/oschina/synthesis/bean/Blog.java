package org.mercury.oschina.synthesis.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wang.zhonghao on 2017/8/17.
 */

public class Blog implements Parcelable {


    /**
     * author : zzimac
     * authorid : 3362827
     * commentCount : 0
     * id : 1510901
     * pubDate : 2017-08-16 09:53:59.0
     * title : Graylog 使用drools功能过滤不需要的日志
     * type : 1
     */

    private String author;
    private int    authorid;
    private int    commentCount;
    private int    id;
    private String pubDate;
    private String title;
    private int    type;

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
        dest.writeString(this.pubDate);
        dest.writeString(this.title);
        dest.writeInt(this.type);
    }

    public Blog() {
    }

    protected Blog(Parcel in) {
        this.author = in.readString();
        this.authorid = in.readInt();
        this.commentCount = in.readInt();
        this.id = in.readInt();
        this.pubDate = in.readString();
        this.title = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<Blog> CREATOR = new Parcelable.Creator<Blog>() {
        @Override
        public Blog createFromParcel(Parcel source) {
            return new Blog(source);
        }

        @Override
        public Blog[] newArray(int size) {
            return new Blog[size];
        }
    };
}
