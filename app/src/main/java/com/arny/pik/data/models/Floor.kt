package com.arny.pik.data.models

import com.arny.pik.utils.Utility
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Floor {
    var num: Int = 0
    @SerializedName("plan")
    @Expose
    var plan: String? = null
    @SerializedName("paths")
    @Expose
    var paths: String? = null
    @SerializedName("flats")
    @Expose
    var flats: List<Flat>? = null

    override fun toString(): String {
        return Utility.getFields(this)
    }
}