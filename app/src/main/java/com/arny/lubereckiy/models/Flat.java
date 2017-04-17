package com.arny.lubereckiy.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Flat implements Parcelable{
    private int ID;
    private int floorNumber;
    private String korpusID;
    private String sectionName;
    private String flatID;
    private int roomQuantity;
    private double wholeAreaBti;
    private int wholePrice;
    private int officePrice;
    private int discount;
    private String status;

    public Flat() {
    }

    protected Flat(Parcel in) {
        floorNumber = in.readInt();
        korpusID = in.readString();
        sectionName = in.readString();
        flatID = in.readString();
        roomQuantity = in.readInt();
        ID = in.readInt();
        wholeAreaBti = in.readDouble();
        wholePrice = in.readInt();
        officePrice = in.readInt();
        discount = in.readInt();
        status = in.readString();
    }

    public static final Creator<Flat> CREATOR = new Creator<Flat>() {
        @Override
        public Flat createFromParcel(Parcel in) {
            return new Flat(in);
        }

        @Override
        public Flat[] newArray(int size) {
            return new Flat[size];
        }
    };

    public String getFlatID() {
        return flatID;
    }

    public void setFlatID(String flatID) {
        this.flatID = flatID;
    }

    public int getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(int roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

    public double getWholeAreaBti() {
        return wholeAreaBti;
    }

    public void setWholeAreaBti(double wholeAreaBti) {
        this.wholeAreaBti = wholeAreaBti;
    }

    public double getWholePrice() {
        return wholePrice;
    }

    public void setWholePrice(int wholePrice) {
        this.wholePrice = wholePrice;
    }

    public double getOfficePrice() {
        return officePrice;
    }

    public void setOfficePrice(int officePrice) {
        this.officePrice = officePrice;
    }

    public int isDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(floorNumber);
        dest.writeString(korpusID);
        dest.writeString(sectionName);
        dest.writeString(flatID);
        dest.writeInt(roomQuantity);
        dest.writeInt(ID);
        dest.writeDouble(wholeAreaBti);
        dest.writeInt(wholePrice);
        dest.writeInt(officePrice);
        dest.writeInt(discount);
        dest.writeString(status);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Flat)) {
            return false;
        }
        Flat flat = (Flat) obj;
        return status!=null && status.equals(flat.status)
                && roomQuantity==flat.roomQuantity
                && wholeAreaBti==flat.wholeAreaBti
                && wholePrice==flat.wholePrice
                && officePrice==flat.officePrice
                && discount==flat.discount;
    }
    
}
