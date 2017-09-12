
package com.arny.lubereckiy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Korpus {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("coords")
    @Expose
    private String coords;
    @SerializedName("coordsOuter")
    @Expose
    private String coordsOuter;
    @SerializedName("dateOfMovingIn")
    @Expose
    private String dateOfMovingIn;
    @SerializedName("minprice_s")
    @Expose
    private String minpriceS;
    @SerializedName("minprice_1")
    @Expose
    private String minprice1;
    @SerializedName("minprice_2")
    @Expose
    private String minprice2;
    @SerializedName("minprice_3")
    @Expose
    private String minprice3;
    @SerializedName("minprice_4")
    @Expose
    private String minprice4;
    @SerializedName("minprice_5")
    @Expose
    private String minprice5;
    @SerializedName("free")
    @Expose
    private String free;
    @SerializedName("busy")
    @Expose
    private String busy;
    @SerializedName("sold")
    @Expose
    private String sold;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("is_online")
    @Expose
    private Boolean isOnline;
    @SerializedName("finishing")
    @Expose
    private String finishing;

    @Override
    public String toString() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public String getCoordsOuter() {
        return coordsOuter;
    }

    public void setCoordsOuter(String coordsOuter) {
        this.coordsOuter = coordsOuter;
    }

    public String getDateOfMovingIn() {
        return dateOfMovingIn;
    }

    public void setDateOfMovingIn(String dateOfMovingIn) {
        this.dateOfMovingIn = dateOfMovingIn;
    }

    public String getMinpriceS() {
        return minpriceS;
    }

    public void setMinpriceS(String minpriceS) {
        this.minpriceS = minpriceS;
    }

    public String getMinprice1() {
        return minprice1;
    }

    public void setMinprice1(String minprice1) {
        this.minprice1 = minprice1;
    }

    public String getMinprice2() {
        return minprice2;
    }

    public void setMinprice2(String minprice2) {
        this.minprice2 = minprice2;
    }

    public String getMinprice3() {
        return minprice3;
    }

    public void setMinprice3(String minprice3) {
        this.minprice3 = minprice3;
    }

    public String getMinprice4() {
        return minprice4;
    }

    public void setMinprice4(String minprice4) {
        this.minprice4 = minprice4;
    }

    public String getMinprice5() {
        return minprice5;
    }

    public void setMinprice5(String minprice5) {
        this.minprice5 = minprice5;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getBusy() {
        return busy;
    }

    public void setBusy(String busy) {
        this.busy = busy;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public String getFinishing() {
        return finishing;
    }

    public void setFinishing(String finishing) {
        this.finishing = finishing;
    }

}
