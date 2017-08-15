package org.mercury.oschina.bean.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mercury on 2017/8/3.
 */

public class Notice implements Parcelable{

    /**
     * replyCount : 0
     * msgCount : 0
     * fansCount : 0
     * referCount : 0
     */

    private int replyCount;
    private int msgCount;
    private int fansCount;
    private int referCount;

    protected Notice(Parcel in) {
        replyCount = in.readInt();
        msgCount = in.readInt();
        fansCount = in.readInt();
        referCount = in.readInt();
    }

    public static final Creator<Notice> CREATOR = new Creator<Notice>() {
        @Override
        public Notice createFromParcel(Parcel in) {
            return new Notice(in);
        }

        @Override
        public Notice[] newArray(int size) {
            return new Notice[size];
        }
    };

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getReferCount() {
        return referCount;
    }

    public void setReferCount(int referCount) {
        this.referCount = referCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(replyCount);
        dest.writeInt(msgCount);
        dest.writeInt(fansCount);
        dest.writeInt(referCount);
    }
}
