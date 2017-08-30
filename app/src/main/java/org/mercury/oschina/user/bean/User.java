package org.mercury.oschina.user.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wang.zhonghao on 2017/8/30.
 */

public class User implements Parcelable {


    /**
     * gender : 2
     * name : 落落酱
     * portrait : https://static.oschina.net/uploads/user/1304/2609174_100.jpg?t=1486332631000
     * expertise : ,WEB开发,桌面软件开发,软件开发管理
     * userid : 2609174
     */

    private int gender;
    private String name;
    private String portrait;
    private String expertise;
    private int    userid;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.gender);
        dest.writeString(this.name);
        dest.writeString(this.portrait);
        dest.writeString(this.expertise);
        dest.writeInt(this.userid);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.gender = in.readInt();
        this.name = in.readString();
        this.portrait = in.readString();
        this.expertise = in.readString();
        this.userid = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
