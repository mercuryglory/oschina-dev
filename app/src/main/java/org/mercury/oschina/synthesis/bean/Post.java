package org.mercury.oschina.synthesis.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wang.zhonghao on 2017/8/17.
 * 帖子列表 ID 1-问答 2-分享 3-IT杂烩(综合) 4-站务 100-职业生涯 0-所有
 */

public class Post implements Parcelable {


    /**
     * name : 沧海_Sea
     * time : 2017-08-17 18:00:44
     */

    private Object answer;
    /**
     * answer : {"name":"沧海_Sea","time":"2017-08-17 18:00:44"}
     * answerCount : 1
     * author : yansen_zh
     * authorid : 2407820
     * id : 2264197
     * portrait : https://www.oschina.net/img/portrait.gif
     * pubDate : 2017-08-17 17:48:49
     * title : nginx 正则问题
     * viewCount : 7
     */

    private int        answerCount;
    private String author;
    private int    authorid;
    private int    id;
    private String portrait;
    private String pubDate;
    private String title;
    private int    viewCount;

    public Object getAnswer() {
        return answer;
    }

    public void setAnswer(Object answer) {
        this.answer = answer;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
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

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public static class AnswerBean implements Parcelable {
        private String name;
        private String time;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.time);
        }

        public AnswerBean() {
        }

        protected AnswerBean(Parcel in) {
            this.name = in.readString();
            this.time = in.readString();
        }

        public static final Creator<AnswerBean> CREATOR = new Creator<AnswerBean>() {
            @Override
            public AnswerBean createFromParcel(Parcel source) {
                return new AnswerBean(source);
            }

            @Override
            public AnswerBean[] newArray(int size) {
                return new AnswerBean[size];
            }
        };
    }

    public Post() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.answer);
        dest.writeInt(this.answerCount);
        dest.writeString(this.author);
        dest.writeInt(this.authorid);
        dest.writeInt(this.id);
        dest.writeString(this.portrait);
        dest.writeString(this.pubDate);
        dest.writeString(this.title);
        dest.writeInt(this.viewCount);
    }

    protected Post(Parcel in) {
        this.answer = in.readParcelable(Object.class.getClassLoader());
        this.answerCount = in.readInt();
        this.author = in.readString();
        this.authorid = in.readInt();
        this.id = in.readInt();
        this.portrait = in.readString();
        this.pubDate = in.readString();
        this.title = in.readString();
        this.viewCount = in.readInt();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
