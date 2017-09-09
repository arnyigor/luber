
package com.arny.lubereckiy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flat {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("roomQuantity")
    @Expose
    private String roomQuantity;
    @SerializedName("stageNumber1")
    @Expose
    private String stageNumber1;
    @SerializedName("wholeAreaBti")
    @Expose
    private String wholeAreaBti;
    @SerializedName("wholePrice")
    @Expose
    private String wholePrice;
    @SerializedName("reserve_cost")
    @Expose
    private String reserveCost;
    @SerializedName("end_cost")
    @Expose
    private String endCost;
    @SerializedName("officePrice")
    @Expose
    private String officePrice;
    @SerializedName("planId")
    @Expose
    private String planId;
    @SerializedName("finishing")
    @Expose
    private Boolean finishing;
    @SerializedName("discount")
    @Expose
    private Boolean discount;
    @SerializedName("angle")
    @Expose
    private String angle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(String roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

    public String getStageNumber1() {
        return stageNumber1;
    }

    public void setStageNumber1(String stageNumber1) {
        this.stageNumber1 = stageNumber1;
    }

    public String getWholeAreaBti() {
        return wholeAreaBti;
    }

    public void setWholeAreaBti(String wholeAreaBti) {
        this.wholeAreaBti = wholeAreaBti;
    }

    public String getWholePrice() {
        return wholePrice;
    }

    public void setWholePrice(String wholePrice) {
        this.wholePrice = wholePrice;
    }

    public String getReserveCost() {
        return reserveCost;
    }

    public void setReserveCost(String reserveCost) {
        this.reserveCost = reserveCost;
    }

    public String getEndCost() {
        return endCost;
    }

    public void setEndCost(String endCost) {
        this.endCost = endCost;
    }

    public String getOfficePrice() {
        return officePrice;
    }

    public void setOfficePrice(String officePrice) {
        this.officePrice = officePrice;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Boolean getFinishing() {
        return finishing;
    }

    public void setFinishing(Boolean finishing) {
        this.finishing = finishing;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

}
