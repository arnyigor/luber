package com.arny.lubereckiy.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Section implements Parcelable{
    private int ID;
    private String name;
    private String korpusID;
    private int maxFlatsOnFloor;
    private int quantity;
    private int flatsDirection;
    private int readiness;
    private int firstFloorNumber;

    public Section() {
    }

    protected Section(Parcel in) {
        name = in.readString();
        korpusID = in.readString();
        maxFlatsOnFloor = in.readInt();
        quantity = in.readInt();
        flatsDirection = in.readInt();
        readiness = in.readInt();
        firstFloorNumber = in.readInt();
        ID = in.readInt();
    }

    public static final Creator<Section> CREATOR = new Creator<Section>() {
        @Override
        public Section createFromParcel(Parcel in) {
            return new Section(in);
        }

        @Override
        public Section[] newArray(int size) {
            return new Section[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxFlatsOnFloor() {
        return maxFlatsOnFloor;
    }

    public void setMaxFlatsOnFloor(int maxFlatsOnFloor) {
        this.maxFlatsOnFloor = maxFlatsOnFloor;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFlatsDirection() {
        return flatsDirection;
    }

    public void setFlatsDirection(int flatsDirection) {
        this.flatsDirection = flatsDirection;
    }

    public int getReadiness() {
        return readiness;
    }

    public void setReadiness(int readiness) {
        this.readiness = readiness;
    }

    public int getFirstFloorNumber() {
        return firstFloorNumber;
    }

    public void setFirstFloorNumber(int firstFloorNumber) {
        this.firstFloorNumber = firstFloorNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(korpusID);
        dest.writeInt(maxFlatsOnFloor);
        dest.writeInt(quantity);
        dest.writeInt(flatsDirection);
        dest.writeInt(readiness);
        dest.writeInt(firstFloorNumber);
        dest.writeInt(ID);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getKorpusID() {
        return korpusID;
    }

    public void setKorpusID(String korpusID) {
        this.korpusID = korpusID;
    }
}
