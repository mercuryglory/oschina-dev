package org.mercury.oschina.tweet.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.mercury.oschina.bean.base.BaseBean;

import java.util.List;

/**
 * 登录用户实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */

public class User extends BaseBean implements Parcelable {

    /**
     * city : 深圳
     * expertise : ["网站运营/站长"]
     * gender : 1
     * ident : javayou
     * joinTime : 2008-08-31 11:48:43
     * lastLoginTime : 2017-08-15 11:23:39
     * name : 红薯
     * platforms : ["Java EE","Java SE","Android","HTML/CSS"]
     * portrait : https://static.oschina.net/uploads/user/0/12_50.jpg?t=1421200584000
     * province : 广东
     * relation : 3
     * uid : 12
     */

    private String city;
    private int          gender;
    private String       ident;
    private String       joinTime;
    private String       lastLoginTime;
    private String       name;
    private String       portrait;
    private String       province;
    private int          relation;
    private int          uid;
    private List<String> expertise;
    private List<String> platforms;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public List<String> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<String> expertise) {
        this.expertise = expertise;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeInt(this.gender);
        dest.writeString(this.ident);
        dest.writeString(this.joinTime);
        dest.writeString(this.lastLoginTime);
        dest.writeString(this.name);
        dest.writeString(this.portrait);
        dest.writeString(this.province);
        dest.writeInt(this.relation);
        dest.writeInt(this.uid);
        dest.writeStringList(this.expertise);
        dest.writeStringList(this.platforms);
    }

    protected User(Parcel in) {
        this.city = in.readString();
        this.gender = in.readInt();
        this.ident = in.readString();
        this.joinTime = in.readString();
        this.lastLoginTime = in.readString();
        this.name = in.readString();
        this.portrait = in.readString();
        this.province = in.readString();
        this.relation = in.readInt();
        this.uid = in.readInt();
        this.expertise = in.createStringArrayList();
        this.platforms = in.createStringArrayList();
    }

    public User() {

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
