package com.arny.pik.data.models

import com.arny.pik.utils.Utility
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Status {

    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null

    override fun toString(): String {
        return Utility.getFields(this)
    }
}
