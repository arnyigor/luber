
package com.arny.lubereckiy.models;

import com.arny.arnylib.utils.Utility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sticker {

	@Override
	public String toString() {
		return Utility.getFields(this);
	}

	@SerializedName("text")
    @Expose
    private String text;
    @SerializedName("color")
    @Expose
    private String color;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
