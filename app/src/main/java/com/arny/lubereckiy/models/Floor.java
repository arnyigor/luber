package com.arny.lubereckiy.models;
import java.util.List;

import com.arny.arnylib.utils.Utility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Floor {

    private int num;
    @SerializedName("plan")
    @Expose
    private String plan;
    @SerializedName("paths")
    @Expose
    private String paths;
    @SerializedName("flats")
    @Expose
    private List<Flat> flats = null;

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }

    public List<Flat> getFlats() {
        return flats;
    }

    public void setFlats(List<Flat> flats) {
        this.flats = flats;
    }

    @Override
    public String toString() {
        return Utility.getFields(this);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}