
package com.arny.lubereckiy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KorpusSection {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sectionName")
    @Expose
    private String sectionName;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("maxFlatsOnFloor")
    @Expose
    private String maxFlatsOnFloor;
    @SerializedName("firstFloorNumber")
    @Expose
    private String firstFloorNumber;
    @SerializedName("readiness")
    @Expose
    private String readiness;
    @SerializedName("flatsDirection")
    @Expose
    private String flatsDirection;
    @SerializedName("plan")
    @Expose
    private String plan;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMaxFlatsOnFloor() {
        return maxFlatsOnFloor;
    }

    public void setMaxFlatsOnFloor(String maxFlatsOnFloor) {
        this.maxFlatsOnFloor = maxFlatsOnFloor;
    }

    public String getFirstFloorNumber() {
        return firstFloorNumber;
    }

    public void setFirstFloorNumber(String firstFloorNumber) {
        this.firstFloorNumber = firstFloorNumber;
    }

    public String getReadiness() {
        return readiness;
    }

    public void setReadiness(String readiness) {
        this.readiness = readiness;
    }

    public String getFlatsDirection() {
        return flatsDirection;
    }

    public void setFlatsDirection(String flatsDirection) {
        this.flatsDirection = flatsDirection;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

}
