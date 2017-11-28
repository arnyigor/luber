package com.arny.pik.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Planing {

    @SerializedName("srcLayout")
    @Expose
    private String srcLayout;

    public String getSrcLayout() {
        return srcLayout;
    }

    public void setSrcLayout(String srcLayout) {
        this.srcLayout = srcLayout;
    }

}