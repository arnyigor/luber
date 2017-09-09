
package com.arny.lubereckiy.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Korpusflats {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("titleOfCorps")
    @Expose
    private String titleOfCorps;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("korpusSections")
    @Expose
    private List<KorpusSection> korpusSections = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleOfCorps() {
        return titleOfCorps;
    }

    public void setTitleOfCorps(String titleOfCorps) {
        this.titleOfCorps = titleOfCorps;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<KorpusSection> getKorpusSections() {
        return korpusSections;
    }

    public void setKorpusSections(List<KorpusSection> korpusSections) {
        this.korpusSections = korpusSections;
    }

}
