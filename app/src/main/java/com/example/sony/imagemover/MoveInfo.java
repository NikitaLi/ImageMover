package com.example.sony.imagemover;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Information about one of moving operations
 */
class MoveInfo implements Parcelable {

    String mMoveInfo;

    MoveInfo(int movesCount, int firstFragmentId, int lastFragmentId) {
        mMoveInfo = Integer.toString(movesCount) + ", " + Integer.toString(firstFragmentId) + ", " + Integer.toString(lastFragmentId);
    }

    private MoveInfo(Parcel in) {
        mMoveInfo = in.readString();
    }

    public static final Creator<MoveInfo> CREATOR = new Creator<MoveInfo>() {
        @Override
        public MoveInfo createFromParcel(Parcel in) {
            return new MoveInfo(in);
        }

        @Override
        public MoveInfo[] newArray(int size) {
            return new MoveInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMoveInfo);
    }
}
