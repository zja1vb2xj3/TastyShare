package com.example.administrator.tastyshare;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pdg on 2017-06-29.
 */

public class RegisterParcelable implements Parcelable {

    private String imageName;
    private String tagName;
    private String eateryName;
    private String menuName;
    private String eateryComment;
    private String eateryScore;


    protected RegisterParcelable(Parcel in) {
        imageName = in.readString();
        tagName = in.readString();
        eateryName = in.readString();
        menuName = in.readString();
        eateryComment = in.readString();
        eateryScore = in.readString();
    }

    public static final Creator<RegisterParcelable> CREATOR = new Creator<RegisterParcelable>() {
        @Override
        public RegisterParcelable createFromParcel(Parcel in) {
            return new RegisterParcelable(in);
        }

        @Override
        public RegisterParcelable[] newArray(int size) {
            return new RegisterParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageName);
        dest.writeString(tagName);
        dest.writeString(eateryName);
        dest.writeString(menuName);
        dest.writeString(eateryComment);
        dest.writeString(eateryScore);
    }
}
