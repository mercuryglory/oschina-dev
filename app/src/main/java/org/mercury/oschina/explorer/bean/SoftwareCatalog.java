package org.mercury.oschina.explorer.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mercury on 2017/9/19.
 */

public class SoftwareCatalog implements Parcelable {

    /**
     * name : 跨平台
     * tag : 35
     */

    private String name;
    private int tag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.tag);
    }

    public SoftwareCatalog() {
    }

    protected SoftwareCatalog(Parcel in) {
        this.name = in.readString();
        this.tag = in.readInt();
    }

    public static final Parcelable.Creator<SoftwareCatalog> CREATOR = new Parcelable
            .Creator<SoftwareCatalog>() {
        @Override
        public SoftwareCatalog createFromParcel(Parcel source) {
            return new SoftwareCatalog(source);
        }

        @Override
        public SoftwareCatalog[] newArray(int size) {
            return new SoftwareCatalog[size];
        }
    };
}
