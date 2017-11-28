
package com.arny.pik.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenPlan {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data:" + data;
    }
}
