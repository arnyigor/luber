
package com.arny.lubereckiy.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("sets_of_pathes")
    @Expose
    private List<Korpus> korpuses = null;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Korpus> getKorpuses() {
        return korpuses;
    }

    public void setKorpuses(List<Korpus> korpuses) {
        this.korpuses = korpuses;
    }

}
