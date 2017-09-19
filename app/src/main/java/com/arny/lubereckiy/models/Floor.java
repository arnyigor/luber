package com.arny.lubereckiy.models;
import java.util.List;

import com.arny.arnylib.database.DBProvider;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Floor {

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
        return DBProvider.getColumns(this);
    }
}