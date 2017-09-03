package org.mercury.oschina.bean.base;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mercury on 2017/8/3.
 */

public class BaseBean implements Parcelable {

    private Notice notice;

    public BaseBean() {

    }

    protected BaseBean(Parcel in) {
        notice = in.readParcelable(Notice.class.getClassLoader());
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(notice, flags);
    }
}
