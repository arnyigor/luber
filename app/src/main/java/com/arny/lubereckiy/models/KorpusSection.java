
package com.arny.lubereckiy.models;

import com.arny.arnylib.utils.Utility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

public class KorpusSection {

    @Override
    public String toString() {
        return Utility.getColumns(this);
    }

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
    private int maxFlatsOnFloor;
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
    @SerializedName("floors")
    @Expose
    private LinkedHashMap<Integer,Floor> floors;

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

    public int getMaxFlatsOnFloor() {
        return maxFlatsOnFloor;
    }

    public void setMaxFlatsOnFloor(int maxFlatsOnFloor) {
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

    public LinkedHashMap<Integer, Floor> getFloors() {
        return floors;
    }

    public void setFloors(LinkedHashMap<Integer, Floor> floors) {
        this.floors = floors;
    }
}
