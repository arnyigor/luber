
package com.arny.lubereckiy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Img {
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("200")
    @Expose
    private String _200;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String get200() {
        return _200;
    }

    public void set200(String _200) {
        this._200 = _200;
    }


}
