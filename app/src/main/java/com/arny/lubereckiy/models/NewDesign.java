
package com.arny.lubereckiy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;

public class NewDesign  extends RealmObject {

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
