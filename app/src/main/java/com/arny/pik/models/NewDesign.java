
package com.arny.pik.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewDesign {

    @SerializedName("imageMap")
    @Expose
    private String imageMap;

    public String getImageMap() {
        return imageMap;
    }

    public void setImageMap(String imageMap) {
        this.imageMap = imageMap;
    }

}
