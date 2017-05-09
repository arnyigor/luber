package com.arny.lubereckiy.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Korpus implements Parcelable {
    private int ID;
    private String korpusID;
    private String title;
    private String status;
    private boolean finishing;
    private int free;
    private int busy;
    private int minprice_1;
    private int minprice_2;
    private int sold;
    private String dateOfMovingIn ;

    public Korpus() {
    }

    protected Korpus(Parcel in) {
        korpusID = in.readString();
        title = in.readString();
        dateOfMovingIn = in.readString();
        status = in.readString();
        finishing = in.readByte() != 0;
        free = in.readInt();
        busy = in.readInt();
        ID = in.readInt();
        minprice_1 = in.readInt();
        minprice_2 = in.readInt();
        sold = in.readInt();
    }

    public static final Creator<Korpus> CREATOR = new Creator<Korpus>() {
        @Override
        public Korpus createFromParcel(Parcel in) {
            return new Korpus(in);
        }

        @Override
        public Korpus[] newArray(int size) {
            return new Korpus[size];
        }
    };

    public String getKorpusID() {
        return korpusID;
    }

    public void setKorpusID(String korpusID) {
        this.korpusID = korpusID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFinishing() {
        return finishing;
    }

    public void setFinishing(boolean finishing) {
        this.finishing = finishing;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getBusy() {
        return busy;
    }

    public void setBusy(int busy) {
        this.busy = busy;
    }

    public int getMinprice_1() {
        return minprice_1;
    }

    public void setMinprice_1(int minprice_1) {
        this.minprice_1 = minprice_1;
    }

    public int getMinprice_2() {
        return minprice_2;
    }

    public void setMinprice_2(int minprice_2) {
        this.minprice_2 = minprice_2;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(korpusID);
        dest.writeString(title);
        dest.writeString(dateOfMovingIn);
        dest.writeString(status);
        dest.writeByte((byte) (finishing ? 1 : 0));
        dest.writeInt(free);
        dest.writeInt(busy);
        dest.writeInt(minprice_1);
        dest.writeInt(minprice_2);
        dest.writeInt(sold);
        dest.writeInt(ID);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDateOfMovingIn() {
        return dateOfMovingIn;
    }

    public void setDateOfMovingIn(String dateOfMovingIn) {
        this.dateOfMovingIn = dateOfMovingIn;
    }

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Korpus)) {
			return false;
		}
		Korpus korpus = (Korpus) obj;
		return title!=null && title.equals(korpus.title)
				&& status!=null && status.equals(korpus.status)
				&& finishing==korpus.finishing
				&& free==korpus.free
				&& busy==korpus.busy
				&& minprice_1==korpus.minprice_1
				&& minprice_2==korpus.minprice_2
				&& sold==korpus.sold
				&& dateOfMovingIn!=null && dateOfMovingIn.equals(korpus.dateOfMovingIn);
	}
}
