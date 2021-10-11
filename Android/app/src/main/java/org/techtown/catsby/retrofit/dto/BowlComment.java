package org.techtown.catsby.retrofit.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BowlComment implements Serializable, Parcelable {
    private int id;
    private User user;
    private BowlCommunity bowlCommunity;
    private String uid;
    private String content;
    private String createDate;
    private String updateDate;

    protected BowlComment(Parcel in) {
        id = in.readInt();
        content = in.readString();
        user = in.readParcelable(user);
        uid = in.readString();
        createDate = in.readString();
        updateDate = in.readString();
    }

    public static final Creator<BowlComment> CREATOR = new Creator<BowlComment>() {
        @Override
        public BowlComment createFromParcel(Parcel in) {
            return new BowlComment(in);
        }

        @Override
        public BowlComment[] newArray(int size) {
            return new BowlComment[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public BowlCommunity getBowlCommunity() {
        return bowlCommunity;
    }

    public String getContent() {
        return content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(content);
        parcel.writeValue(user);
        parcel.writeString(uid);
        parcel.writeString(createDate);
        parcel.writeString(updateDate);
    }
}
