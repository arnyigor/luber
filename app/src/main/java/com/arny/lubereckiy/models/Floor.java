package com.arny.lubereckiy.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Floor implements Parcelable{
    private int ID;
    private String korpusID;
    private String sectionName;
    private int floorNumber;
    private String floorPlan;

    public Floor() {
    }

    protected Floor(Parcel in) {
        korpusID = in.readString();
        sectionName = in.readString();
        floorNumber = in.readInt();
        floorPlan = in.readString();
    }

    public static final Creator<Floor> CREATOR = new Creator<Floor>() {
        @Override
        public Floor createFromParcel(Parcel in) {
            return new Floor(in);
        }

        @Override
        public Floor[] newArray(int size) {
            return new Floor[size];
        }
    };

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getFloorPlan() {
        return floorPlan;
    }

    public void setFloorPlan(String floorPlan) {
        this.floorPlan = floorPlan;
    }

    public String getKorpusID() {
        return korpusID;
    }

    public void setKorpusID(String korpusID) {
        this.korpusID = korpusID;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(korpusID);
        dest.writeString(sectionName);
        dest.writeInt(floorNumber);
        dest.writeString(floorPlan);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
