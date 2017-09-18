package org.mercury.oschina.explorer.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mercury on 2017/9/18.
 */

public class Project implements Parcelable {


    /**
     * name : EverVim
     * description : EverVim: 一个面向所有开发者的 Vim 发行...
     * url : https://www.oschina.net/p/evervim
     */

    private String name;
    private String description;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.url);
    }

    public Project() {
    }

    protected Project(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Project> CREATOR = new Parcelable.Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel source) {
            return new Project(source);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
}
