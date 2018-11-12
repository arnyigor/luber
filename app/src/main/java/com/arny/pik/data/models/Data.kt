package com.arny.pik.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {
    @SerializedName("img")
    @Expose
    var img: String? = null
    @SerializedName("sets_of_pathes")
    @Expose
    var korpuses: List<Korpus>? = null

    override fun toString(): String {
        return "Корпуса:" + korpuses!!
    }
}
