package com.arny.lubereckiy.models;

import com.arny.arnylib.utils.Utility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class KorpusData {
    @Override
    public String toString() {
        return Utility.getColumns(this);
    }

    @SerializedName("sections")
    @Expose
    private List<KorpusSection> sections;

    public List<KorpusSection> getSections() {
        return sections;
    }

    public void setSections(List<KorpusSection> sections) {
        this.sections = sections;
    }
}
