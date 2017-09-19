package com.arny.lubereckiy.models;

import com.arny.arnylib.database.DBProvider;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class KorpusData {
    @Override
    public String toString() {
        return DBProvider.getColumns(this);
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
