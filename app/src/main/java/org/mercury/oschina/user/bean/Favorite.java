package org.mercury.oschina.user.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wang.zhonghao on 2017/8/30.
 */

public class Favorite implements Parcelable {


    /**
     * title : helloworld
     * objid : 9688
     * type : 5
     * url : http://oschina.org/code/snippet_253472_9688
     */

    private String title;
    private int    objid;
    private int    type;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getObjid() {
        return objid;
    }

    public void setObjid(int objid) {
        this.objid = objid;
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
        dest.writeString(this.title);
        dest.writeInt(this.objid);
        dest.writeInt(this.type);
        dest.writeString(this.url);
    }

    public Favorite() {
    }

    protected Favorite(Parcel in) {
        this.title = in.readString();
        this.objid = in.readInt();
        this.type = in.readInt();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Favorite> CREATOR = new Parcelable.Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel source) {
            return new Favorite(source);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}
