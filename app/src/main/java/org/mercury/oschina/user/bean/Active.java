package org.mercury.oschina.user.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wang.zhonghao on 2017/8/21.
 */

public class Active implements Parcelable {


    /**
     * appClient : 3
     * author : wzhseu
     * authorid : 2886481
     * catalog : 3
     * commentCount : 0
     * id : 15057837
     * message : 我是个有代码洁癖的人[得意]
     * objectCatalog : 0
     * objectId : 15057837
     * objectReply : {}
     * objectTitle :
     * objectType : 100
     * portrait : https://www.oschina.net/img/portrait.gif
     * pubDate : 2017-08-21 10:44:10.0
     * tweetImage : https://static.oschina.net/uploads/space/2017/0821/104437_aiZK_2886481_thumb.jpg
     * url :
     */

    private int appClient;
    private String          author;
    private int             authorid;
    private int             catalog;
    private int             commentCount;
    private int             id;
    private String          message;
    private int             objectCatalog;
    private int             objectId;
    private ObjectReplyBean objectReply;
    private String          objectTitle;
    private int             objectType;
    private String          portrait;
    private String          pubDate;
    private String          tweetImage;
    private String          url;

    public int getAppClient() {
        return appClient;
    }

    public void setAppClient(int appClient) {
        this.appClient = appClient;
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

    public int getCatalog() {
        return catalog;
    }

    public void setCatalog(int catalog) {
        this.catalog = catalog;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getObjectCatalog() {
        return objectCatalog;
    }

    public void setObjectCatalog(int objectCatalog) {
        this.objectCatalog = objectCatalog;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public ObjectReplyBean getObjectReply() {
        return objectReply;
    }

    public void setObjectReply(ObjectReplyBean objectReply) {
        this.objectReply = objectReply;
    }

    public String getObjectTitle() {
        return objectTitle;
    }

    public void setObjectTitle(String objectTitle) {
        this.objectTitle = objectTitle;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
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

    public String getTweetImage() {
        return tweetImage;
    }

    public void setTweetImage(String tweetImage) {
        this.tweetImage = tweetImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class ObjectReplyBean implements Parcelable{
        // 	动态对象回复者名称
        private String objectName;
        // 	动态对象回复内容
        private String objectBody;

        protected ObjectReplyBean(Parcel in) {
            objectName = in.readString();
            objectBody = in.readString();
        }

        public static final Creator<ObjectReplyBean> CREATOR = new Creator<ObjectReplyBean>() {
            @Override
            public ObjectReplyBean createFromParcel(Parcel in) {
                return new ObjectReplyBean(in);
            }

            @Override
            public ObjectReplyBean[] newArray(int size) {
                return new ObjectReplyBean[size];
            }
        };

        public String getObjectName() {
            return objectName;
        }

        public void setObjectName(String objectName) {
            this.objectName = objectName;
        }

        public String getObjectBody() {
            return objectBody;
        }

        public void setObjectBody(String objectBody) {
            this.objectBody = objectBody;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.objectName);
            dest.writeString(this.objectBody);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.appClient);
        dest.writeString(this.author);
        dest.writeInt(this.authorid);
        dest.writeInt(this.catalog);
        dest.writeInt(this.commentCount);
        dest.writeInt(this.id);
        dest.writeString(this.message);
        dest.writeInt(this.objectCatalog);
        dest.writeInt(this.objectId);
        dest.writeParcelable(this.objectReply, flags);
        dest.writeString(this.objectTitle);
        dest.writeInt(this.objectType);
        dest.writeString(this.portrait);
        dest.writeString(this.pubDate);
        dest.writeString(this.tweetImage);
        dest.writeString(this.url);
    }

    public Active() {
    }

    protected Active(Parcel in) {
        this.appClient = in.readInt();
        this.author = in.readString();
        this.authorid = in.readInt();
        this.catalog = in.readInt();
        this.commentCount = in.readInt();
        this.id = in.readInt();
        this.message = in.readString();
        this.objectCatalog = in.readInt();
        this.objectId = in.readInt();
        this.objectReply = in.readParcelable(ObjectReplyBean.class.getClassLoader());
        this.objectTitle = in.readString();
        this.objectType = in.readInt();
        this.portrait = in.readString();
        this.pubDate = in.readString();
        this.tweetImage = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Active> CREATOR = new Parcelable.Creator<Active>() {
        @Override
        public Active createFromParcel(Parcel source) {
            return new Active(source);
        }

        @Override
        public Active[] newArray(int size) {
            return new Active[size];
        }
    };
}
