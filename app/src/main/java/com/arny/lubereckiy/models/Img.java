
package com.arny.lubereckiy.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Img {

    @SerializedName("gradify")
    @Expose
    private List<String> gradify = null;
    @SerializedName("retina")
    @Expose
    private String retina;
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("600")
    @Expose
    private String _600;
    @SerializedName("200")
    @Expose
    private String _200;
    @SerializedName("100")
    @Expose
    private String _100;

    public List<String> getGradify() {
        return gradify;
    }

    public void setGradify(List<String> gradify) {
        this.gradify = gradify;
    }

    public String getRetina() {
        return retina;
    }

    public void setRetina(String retina) {
        this.retina = retina;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String get600() {
        return _600;
    }

    public void set600(String _600) {
        this._600 = _600;
    }

    public String get200() {
        return _200;
    }

    public void set200(String _200) {
        this._200 = _200;
    }

    public String get100() {
        return _100;
    }

    public void set100(String _100) {
        this._100 = _100;
    }

}
