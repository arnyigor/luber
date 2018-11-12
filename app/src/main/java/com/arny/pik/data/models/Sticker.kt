package com.arny.pik.data.models

import com.arny.pik.utils.Utility
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Sticker {

    @SerializedName("text")
    @Expose
    var text: String? = null
    @SerializedName("color")
    @Expose
    var color: String? = null

    override fun toString(): String {
        return Utility.getFields(this)
    }

}
