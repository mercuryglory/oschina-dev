package org.mercury.oschina.user.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wang.zhonghao on 2017/8/19.
 */

public class Message implements Parcelable {

    /**
     * content : 你好啊
     * senderid : 253479
     * sender : 张艺辰
     * friendid : 253469
     * id : 898973
     * pubDate : 2013-10-10 15:55:24.0
     * friendname : test33
     * messageCount : 2
     * portrait : http://static.oschina.org/uploads/user/126/253469_50.jpg?t=1366257509000
     */

    private String content;
    private int    senderid;
    private String sender;
    private int    friendid;
    private int    id;
    private String pubDate;
    private String friendname;
    private int    messageCount;
    private String portrait;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSenderid() {
        return senderid;
    }

    public void setSenderid(int senderid) {
        this.senderid = senderid;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getFriendid() {
        return friendid;
    }

    public void setFriendid(int friendid) {
        this.friendid = friendid;
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

    public String getFriendname() {
        return friendname;
    }

    public void setFriendname(String friendname) {
        this.friendname = friendname;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeInt(this.senderid);
        dest.writeString(this.sender);
        dest.writeInt(this.friendid);
        dest.writeInt(this.id);
        dest.writeString(this.pubDate);
        dest.writeString(this.friendname);
        dest.writeInt(this.messageCount);
        dest.writeString(this.portrait);
    }

    public Message() {
    }

    protected Message(Parcel in) {
        this.content = in.readString();
        this.senderid = in.readInt();
        this.sender = in.readString();
        this.friendid = in.readInt();
        this.id = in.readInt();
        this.pubDate = in.readString();
        this.friendname = in.readString();
        this.messageCount = in.readInt();
        this.portrait = in.readString();
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
