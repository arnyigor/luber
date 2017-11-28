
package com.arny.pik.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Img {
    @SerializedName("main")
    @Expose
    private String main;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

}
