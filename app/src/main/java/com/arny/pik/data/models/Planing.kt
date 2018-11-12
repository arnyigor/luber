package com.arny.pik.data.models

import com.arny.pik.utils.Utility
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Planing {
    @SerializedName("srcLayout")
    @Expose
    var srcLayout: String? = null

    override fun toString(): String {
        return Utility.getFields(this)
    }
}