
package com.arny.lubereckiy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hint {

    @SerializedName("top")
    @Expose
    private String top;
    @SerializedName("left")
    @Expose
    private String left;

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

}
